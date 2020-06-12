package motoservice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class request_DriverActivity extends AppCompatActivity {
    private EditText telefono;
    private Button agregar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__driver);
        telefono = (EditText)findViewById(R.id.Celular);
        agregar = (Button)findViewById(R.id.Enviar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Servicio("http://192.168.0.102/projects/MotoService/controller/insert_request.php");
            }

        });
    }

    private void Servicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Conexion exitosa",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametos = new HashMap<String,String>();
                parametos.put("phone_user",telefono.getText().toString());
                parametos.put("name_user",user.getEmail().toString());
                parametos.put("licence_driver","AAAA-1");
                parametos.put("coordinates_user","88888,44444");
                return parametos;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
