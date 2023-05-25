package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.util.Objects;

import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.modelo.Horario;
import com.talentounido.cliente.modelo.RegisterContent;

public class RegistroHorario extends AppCompatActivity {
    private ImageView qrImage;
    private int idHorario = 0;
    private TextView txtUsarMateria, txtUsarCarrera, txtUsarSemestre, txtUsarModalidad, txtUsarFase;
    private Button btnDesOcupar;
    private EditText txtUsarActividad, txtAlumnos;
    private Horario horario;
    private RegisterContent content;
    private int idRegister;

    private TextInputLayout txtLayoutActividad, txtLayoutAlumnos;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_horario);

        txtUsarActividad = findViewById(R.id.txtOpciones_actividad);

        txtLayoutActividad = findViewById(R.id.txtOpciones_actividad_registro);
        txtLayoutAlumnos = findViewById(R.id.txtContainer_numero_alumnos);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("horario")) {
            horario = bundle.getParcelable("horario");
        }
        txtAlumnos = findViewById(R.id.txtAlumnos);
        txtUsarMateria = findViewById(R.id.txtUsarMateria);
        txtUsarCarrera = findViewById(R.id.txtUsarCarrera);
        txtUsarSemestre = findViewById(R.id.txtUsarSemestre);
        txtUsarModalidad = findViewById(R.id.txtUsarModalidad);
        txtUsarFase = findViewById(R.id.txtFase);
        btnDesOcupar = findViewById(R.id.btnDesocupar);

        txtLayoutAlumnos.setVisibility(View.INVISIBLE);
        txtAlumnos.setVisibility(View.INVISIBLE);
        btnDesOcupar.setVisibility(View.INVISIBLE);

        qrImage = findViewById(R.id.qr_image_scann);
        Glide.with(this).load(R.raw.scanner).into(qrImage);

        if (horario != null) {
            txtUsarMateria.setText(horario.getMateria());
            txtUsarCarrera.setText(horario.getCarrera());
            txtUsarSemestre.setText(horario.getInicia() + " - " + horario.getFinaliza());
            idHorario = horario.getId();
        }

        qrImage.setOnClickListener(view -> {
            IntentIntegrator integrator = new IntentIntegrator(RegistroHorario.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt("Enfoca la camara al QR");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_SHORT).show();
            } else {

                String actividad = txtUsarActividad.getText().toString();
                String laboratorio = result.getContents();
                content = new RegisterContent(idHorario, actividad, laboratorio);

                try {
                    registrar(content);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void registrar(RegisterContent content) throws JSONException {
        String token = Peticiones.getToken(this);

        Peticiones.postCreateRegister(token, content, response -> {
            if (response.has("message")) {
                try {
                    String mensaje = response.getString("message");
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    if (mensaje.contains("Registro creado exitosamente")) {
                        uiOcupado();
                        idRegister = response.getInt("id");
                    } else {
                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            if (Objects.requireNonNull(error.getMessage()).contains("ConnectException")) {
                Toast.makeText(this, "El servidor no responde, intentelo mas tarde", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uiOcupado() {
        qrImage.setVisibility(View.INVISIBLE);
        txtLayoutActividad.setVisibility(View.INVISIBLE);
        txtUsarActividad.setVisibility(View.INVISIBLE);

        txtLayoutAlumnos.setVisibility(View.VISIBLE);
        txtAlumnos.setVisibility(View.VISIBLE);
        btnDesOcupar.setVisibility(View.VISIBLE);

        btnDesOcupar.setOnClickListener(v ->{
            int alumnos = Integer.parseInt(txtAlumnos.getText().toString());

            String token = Peticiones.getToken(this);

            try {
                Peticiones.postAlumnosAtendidos(token,idRegister,alumnos, response -> Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show(), error -> {

                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}