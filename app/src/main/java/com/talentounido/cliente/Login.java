package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ServerError;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.json.JSONException;
import org.json.JSONObject;

import com.talentounido.cliente.PeticionesVolley.Peticiones;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private EditText txtUsuario, txtPwd;
    private String usuario, pwd;
    private Button btnLogin;
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
        indicator.setIndeterminate(true);
        indicator.setVisibility(View.INVISIBLE);
    }

    private void clear()
    {
        txtPwd.setText("");
        txtUsuario.setText("");
    }
    private void iniciarSesion() {
        btnLogin.setOnClickListener(view -> {
            Peticiones.init(getApplicationContext());
            usuario = txtUsuario.getText().toString();
            pwd = txtPwd.getText().toString();
            if (!usuario.isEmpty() && !pwd.isEmpty()) {
                indicator.setIndeterminate(true);
                indicator.setVisibility(View.VISIBLE);
                clear();
                Peticiones.login(usuario, pwd, response -> {
                    try {

                            String token = response.getString("token");
                            DecodedJWT jwt = JWT.decode(token);
                            String payload = jwt.getPayload();
                            JSONObject payloadJson = new JSONObject(new String(Base64.decode(payload, Base64.URL_SAFE)));
                            String rol = payloadJson.getString("privilegio");
                            JSONObject priv = new JSONObject(rol);

                            if (getString(R.string.docente).equals(priv.getString("name"))) {
                                Peticiones.setPreference(this, "token", token);
                                Intent home = new Intent(this, Home.class);
                                indicator.setVisibility(View.INVISIBLE);
                                startActivity(home);
                            } else {
                                Toast.makeText(this, "No tienes credenciales válidas", Toast.LENGTH_SHORT).show();
                                indicator.setVisibility(View.INVISIBLE);
                            }

                    } catch (JSONException e) {
                        Toast.makeText(this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        indicator.setVisibility(View.INVISIBLE);
                    }
                }, error -> {
                    if (error instanceof AuthFailureError) {
                        Toast.makeText(this, "Usuario y/o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(this, "Error del servidor. Inténtalo de nuevo más tarde.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error de red. Verifica tu conexión a internet.", Toast.LENGTH_SHORT).show();
                    }
                    indicator.setVisibility(View.INVISIBLE);
                });
            } else {
                Toast.makeText(this, "No dejes campos vacios", Toast.LENGTH_SHORT).show();
            }
        });
    }
}