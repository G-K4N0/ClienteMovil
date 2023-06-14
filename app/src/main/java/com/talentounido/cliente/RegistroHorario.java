package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.adaptadores.AdapterActividad;
import com.talentounido.cliente.fragmentos.FragmentLaboratorios;
import com.talentounido.cliente.modelo.Actividad;
import com.talentounido.cliente.modelo.Horario;
import com.talentounido.cliente.modelo.RegisterContent;

public class RegistroHorario extends AppCompatActivity {
    private ImageView qrImage;
    private int idHorario = 0;
    private Button btnDesOcupar;
    private EditText txtAlumnos;
    private AutoCompleteTextView txtUsarActividad;
    private Horario horario;
    private int idRegister, idActividad;

    private TextView txtUsarMateria, txtUsarCarrera, txtUsarSemestre, txtTitulo;
    private TextInputLayout txtLayoutAlumnos;

    private String intervaloHorario, actividadItem, dia;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_horario);

        initComponetsUI();
        eventAutoclompleteTxt();
        if (!containsIdRegister()) {
            getDataFromLabFragment();
        } else {
            uiOcupado();
        }
    }

    private void getDataFromLabFragment() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("horario")) {
            horario = bundle.getParcelable("horario");
        }

        if (horario != null) {
            txtUsarMateria.setText(horario.getMateria());
            txtUsarCarrera.setText(horario.getCarrera());
            txtUsarSemestre.setText(String.format("%s%s%s", horario.getInicia(), getResources().getString(R.string.guion), horario.getFinaliza()));
            idHorario = horario.getId();
            txtTitulo.setText(horario.getLaboratorio());
            intervaloHorario = String.format("%s%s%s", horario.getInicia(), getResources().getString(R.string.guion), horario.getFinaliza());
            dia = horario.getDia();
        }

        qrImage.setOnClickListener(view -> {
            IntentIntegrator integrator = new IntentIntegrator(RegistroHorario.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt(getString(R.string.enfocar_camara));
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });
    }

    private boolean containsIdRegister() {
        boolean gotRegister;
        String register = Peticiones.getPreference(this, "idRegister");

        if (!register.contains("error")) {
            gotRegister = true;
        } else {
            gotRegister = false;
        }
        return gotRegister;
    }

    private void initComponetsUI() {
        txtUsarMateria = findViewById(R.id.txtUsarMateria);
        txtUsarCarrera = findViewById(R.id.txtUsarCarrera);
        txtUsarSemestre = findViewById(R.id.txtUsarSemestre);

        qrImage = findViewById(R.id.qr_image_scann);
        Glide.with(this).load(R.raw.scanner).into(qrImage);

        txtTitulo = findViewById(R.id.tvLaboratorio_titulo);
        txtUsarActividad = findViewById(R.id.txtOpciones_actividad);
        getActividades();
    }

    private void uiOcupado() {
        Intent ocupado = new Intent(this, CompletarRegistro.class);
        startActivity(ocupado);
    }

    private void eventAutoclompleteTxt() {
        txtUsarActividad.setOnItemClickListener((adapterView, view, position, id) -> {
            Actividad actividad = (Actividad) adapterView.getItemAtPosition(position);

            if (actividad != null) {
                String selectedItem = actividad.getActividad();
                txtUsarActividad.setText(selectedItem);
                idActividad = actividad.getId();
                actividadItem = selectedItem;
            }
        });
    }

    private void getActividades() {
        String token = Peticiones.getPreference(this, "token");
        ArrayList<Actividad> listActividad = new ArrayList<>();

        Peticiones.getActividades(token, response -> {
            if (response.length() > 0) {
                try {
                    JSONArray arrayActividades = response.getJSONArray("actividades");

                    for (int i = 0; i < arrayActividades.length(); i++) {
                        JSONObject actividad = arrayActividades.getJSONObject(i);

                        listActividad.add(new Actividad(actividad.getInt("id"), actividad.getString("name")));
                    }
                    AdapterActividad adapter = new AdapterActividad(getApplicationContext(), listActividad);
                    txtUsarActividad.setAdapter(adapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            Toast.makeText(this, "-->" + error, Toast.LENGTH_LONG).show();
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_SHORT).show();
            } else {
                String laboratorio = result.getContents();
                RegisterContent content = new RegisterContent(idHorario, idActividad, laboratorio);

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
        String token = Peticiones.getPreference(this, getString(R.string.token));

        Peticiones.postCreateRegister(token, content, response -> {
            if (response.has("message")) {
                try {
                    String mensaje = response.getString("message");

                    if (mensaje.contains("Registro creado exitosamente")) {
                        idRegister = response.getInt("id");
                        Peticiones.setPreference(this, "idRegister", String.valueOf(idRegister));
                        Peticiones.setPreference(this, "labname", content.getLabName());
                        Peticiones.setPreference(this, "intervalHora", intervaloHorario);
                        Peticiones.setPreference(this, "actividad", actividadItem);
                        Peticiones.setPreference(this, "dia", dia);
                        uiOcupado();
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

}