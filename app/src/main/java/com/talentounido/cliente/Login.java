package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
private Peticiones peticion;
private EditText txtUsuario,txtPwd;
private  String usuario,pwd;
private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = findViewById(R.id.txtUser);
        txtPwd = findViewById(R.id.txtPwd);
        btnLogin = findViewById(R.id.btnLogin);
        peticion = new Peticiones(this);
        iniciarSesion();
    }
    private void iniciarSesion()
    {
        btnLogin.setOnClickListener(view -> {
            usuario = txtUsuario.getText().toString();
            pwd = txtPwd.getText().toString();
            try {
                JSONObject response = new JSONObject();
                        response = peticion.login(usuario,pwd);
                if (response.has("token")){
                    SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("token",response.getString("token"));
                    editor.apply();
                    Toast.makeText(this, "->" + response.getString("token"), Toast.LENGTH_SHORT).show();
                    Intent inicio = new Intent(Login.this, Home.class);
                    startActivity(inicio);
                }
                if (response.has("error")){
                    Toast.makeText(this, "->" + response, Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                System.out.println(e.toString());
            }
        });
    }
}