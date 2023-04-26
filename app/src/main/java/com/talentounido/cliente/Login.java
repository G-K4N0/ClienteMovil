package com.talentounido.cliente;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.json.JSONException;
import org.json.JSONObject;

import PeticionesVolley.Peticiones;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
private EditText txtUsuario,txtPwd;
private  String usuario,pwd;
private Button btnLogin;
    private Handler handler = new Handler();
private CircularProgressIndicator indicator;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = findViewById(R.id.txtUser);
        txtPwd = findViewById(R.id.txtPwd);


        btnLogin = findViewById(R.id.btnLogin);
        iniciarSesion();

        indicator = findViewById(R.id.materialIndicator);
        indicator.setIndeterminate(false);
        indicator.setVisibility(View.INVISIBLE);
        setProgress();
    }

    private void setProgress(){
        final int[] progress = {0};
        handler.post(new Runnable() {
            @Override
            public void run() {
                progress[0] += 10;
                indicator.setProgress(progress[0], true);
                if (progress[0] == 100){
                    handler.removeCallbacks(this);
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void iniciarSesion()
    {
        btnLogin.setOnClickListener(view -> {
            Peticiones.init(getApplicationContext());
            usuario = txtUsuario.getText().toString();
            pwd = txtPwd.getText().toString();
            if (!usuario.isEmpty() && !pwd.isEmpty()){
                indicator.setVisibility(View.VISIBLE);
                indicator.setIndeterminate(true);
                indicator.setIndicatorDirection(CircularProgressIndicator.INDICATOR_DIRECTION_COUNTERCLOCKWISE);
                indicator.animate();
                indicator.setActivated(true);
                indicator.invalidate();
                Peticiones.login(usuario,pwd, response -> {
                    try {
                        String token = response.getString("token");
                        DecodedJWT jwt = JWT.decode(token);
                        String payload = jwt.getPayload();
                        JSONObject payloadJson =  new JSONObject(new String(Base64.decode(payload,Base64.URL_SAFE)));

                        String rol = payloadJson.getString("privilegio");

                        if (getString(R.string.docente).equals(rol)){
                            Peticiones.setToken(this, token);
                            Intent home = new Intent(this, Home.class);
                            indicator.setVisibility(View.INVISIBLE);
                            startActivity(home);
                        } else {
                            Toast.makeText(this, "No tienes credenciales válidas", Toast.LENGTH_SHORT).show();
                            indicator.setVisibility(View.INVISIBLE);
                        }

                    }catch(JSONException e){
                        Toast.makeText(this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        indicator.setVisibility(View.INVISIBLE);
                    }
                },error ->{
                    Toast.makeText(this, "No es posible conectar con el servicio", Toast.LENGTH_SHORT).show();
                    indicator.setVisibility(View.INVISIBLE);
                });
            }else{
                Toast.makeText(this, "No dejes campos vacios", Toast.LENGTH_SHORT).show();
            }
        });
    }
}