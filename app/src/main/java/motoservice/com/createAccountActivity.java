package motoservice.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class createAccountActivity extends AppCompatActivity {
    private EditText et_usernane,et_email,et_pass1;
    private Button btn_register;
    private String name_user,email_user,password_user;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference; //realtime
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //llamando a los recursos desde mi archivo java
        showToolbar(getResources().getString(R.string.text_button_account),true);
        et_usernane = (EditText)findViewById(R.id.username_register);
        et_email = (EditText)findViewById(R.id.email_register);
        et_pass1 = (EditText)findViewById(R.id.password_register1);
        btn_register = (Button)findViewById(R.id.btn_register);
        //cuando presiona el boton
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_user = et_usernane.getText().toString();
                email_user = et_email.getText().toString();
                password_user = et_pass1.getText().toString();
                if(!name_user.isEmpty() && !email_user.isEmpty() && !password_user.isEmpty()){
                    if(password_user.length() >= 6){
                        registerUser();
                    }else{
                        Toast.makeText(getApplicationContext(),"La contraseña debe tener al menos 6 caracteres.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Completa todos los datos.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void registerUser(){
        firebaseAuth.createUserWithEmailAndPassword(email_user,password_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //necesitamos un mapa de valores
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",name_user);
                    map.put("email",email_user);
                    map.put("password",password_user);

                    String id = firebaseAuth.getCurrentUser().getUid();

                    databaseReference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                             // if(task.isSuccessful()){
                                  Toast.makeText(getApplicationContext(),"Registro realizado de manera correcta.",Toast.LENGTH_SHORT).show();
                                  Intent intent = new Intent(createAccountActivity.this,LoginActivity.class);
                                  startActivity(intent);
                                  finish();
                              /*}else{
                                  if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                      Toast.makeText(getApplicationContext(),"El email: "+email_user+" ya existe.",Toast.LENGTH_SHORT).show();
                                  }else{
                                      Toast.makeText(getApplicationContext(),"Error al crear usuario.",Toast.LENGTH_SHORT).show();
                                  }
                              }*/
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Error al registrar, intente con otro email.",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


}
