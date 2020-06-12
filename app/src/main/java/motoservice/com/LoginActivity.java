package motoservice.com;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private EditText email_user,password_user;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private  String email,password;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        ///Iniciamos el callback y lo asignamos al botón

        email_user = (EditText)findViewById(R.id.email_user);
        password_user = (EditText)findViewById(R.id.password_user);
        login = (Button)findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             email = email_user.getText().toString();
             password = password_user.getText().toString();
                if(!email.isEmpty()&&!password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(getApplicationContext(),"Debe completar los datos.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //ingreso exitoso y obtenemos el token para pasarlo como parameto a la funcion
                handleFacebookAccessToken(loginResult.getAccessToken());
                goMenu();
            }

            @Override
            public void onCancel() {
                //proceso cancelado
                 Toast.makeText(getApplicationContext(),R.string.login_facebook_cancel,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                //error de conexión
             Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            //se ejecuta cuando se detecta algún cambio en la autenticación desde el onSuccess
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    goMenu();
                }else{

                }

            }
        };
    }

    public void goCreateAccount(View view){
        Intent intent = new Intent(this,createAccountActivity.class);
        startActivity(intent);
    }
    //manipulador del token
    private void handleFacebookAccessToken( AccessToken accessToken) {
        //Creamos una credencial en base al token que nos llega
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            // se ejecuta cuando se completó el proceso
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Fallo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMenu(){
        Intent intent = new Intent(this , MenuActivity.class);
        //intent.putExtra("nombre",firs_name);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void loginUser(){
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                 Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                 startActivity(intent);
                 finish();
             }else{
                 Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecto.",Toast.LENGTH_SHORT).show();
             }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    //El oyente empieza a escuchar
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }
    //El oyente deja escuchar
    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

}
