package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.talentounido.cliente.PeticionesVolley.Peticiones;

import org.json.JSONException;

public class CompletarRegistro extends AppCompatActivity {

    private Button btnCompletar;
    private TextView txtTitulo, txtHorario, txtActividad, txtDia;
    private EditText txtAlumnos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completar_registro);
        initUI();
        setTextToTextViews();
        completarRegistroClick();
    }

    private void initUI(){
        btnCompletar = findViewById(R.id.btnCompletar);
        txtAlumnos = findViewById(R.id.txtAlumnos_completar);
        txtTitulo = findViewById(R.id.txtTitulo_completar);
        txtHorario = findViewById(R.id.txtHorario_completar);
        txtActividad = findViewById(R.id.txtActividad_Completar);
        txtDia = findViewById(R.id.txtDia_completar);
    }

    private void setTextToTextViews(){
        String labName = Peticiones.getPreference(this, "labname");
        String hora = Peticiones.getPreference(this, "intervalHora");
        String actividad = Peticiones.getPreference(this,"actividad");
        String dia = Peticiones.getPreference(this,"dia");

        txtTitulo.setText(labName);
        txtHorario.setText(hora);
        txtActividad.setText(actividad);
        txtDia.setText(dia);
    }

    private void completarRegistroClick(){
        btnCompletar.setOnClickListener(v ->{

            String token = Peticiones.getPreference(this, getString(R.string.token));
            int idRegister = Integer.parseInt(Peticiones.getPreference(this, "idRegister"));
            int totalAlumnos = Integer.parseInt(txtAlumnos.getText().toString());
            try {
                Peticiones.postAlumnosAtendidos(token,idRegister,totalAlumnos, response -> {
                    if(response.has("message")){
                        Peticiones.setPreference(this, "idRegister", null);
                        txtAlumnos.setText("");
                        returnToFragment();
                    }
                }, error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void returnToFragment(){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
}