package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button btnIniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBtnIniciar();
    }
    public void setBtnIniciar(){
        btnIniciar = findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(v -> {
            Intent Login = new Intent(MainActivity.this, Login.class);
            startActivity(Login);
        });
    }
}