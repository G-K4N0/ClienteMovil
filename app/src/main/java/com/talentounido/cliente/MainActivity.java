package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import fragmentos.FragmentLaboratorios;

public class MainActivity extends AppCompatActivity implements FragmentLaboratorios.OnFragmentInteractionListener {
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
            Intent home = new Intent(MainActivity.this, Home.class);
            startActivity(home);
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}