package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.adaptadores.AdapterActividad;
import com.talentounido.cliente.adaptadores.AdapterGrupo;
import com.talentounido.cliente.adaptadores.AdapterMateria;
import com.talentounido.cliente.modelo.Actividad;
import com.talentounido.cliente.modelo.Grupo;
import com.talentounido.cliente.modelo.Materia;
import com.talentounido.cliente.modelo.SinRegisterContent;
import com.talentounido.cliente.modelo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SinHorario extends AppCompatActivity {

    private ImageView qrImage;
    private AutoCompleteTextView txtGrupo, txtMateria, txtInicia, txtFinaliza, txtDia, txtActividad;
    private int idGrupo, idMateria, idActividad;
    private String inicia, finaliza, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_horario);

        txtGrupo = findViewById(R.id.txtOpciones_grupo_sin_horario);
        txtMateria = findViewById(R.id.txtOpciones_Materia_sin_horario);
        txtInicia = findViewById(R.id.txtOpciones_Inicia_sin_horario);
        txtFinaliza = findViewById(R.id.txtOpciones_Finaliza_sin_horario);
        txtDia = findViewById(R.id.txtOpciones_Dia_sin_horario);
        txtActividad = findViewById(R.id.txtOpciones_actividad_sin_horario);

        qrImage = findViewById(R.id.qr_image_scann_sin_horario);
        Glide.with(this).load(R.raw.scanner).into(qrImage);

        getMaterias();
        getGrupos();
        getActividades();
        eventAutoComplete();
        createRegister();
        eventQrImage(qrImage);
    }

    private void eventQrImage(ImageView qrButton) {
        qrButton.setOnClickListener(view -> {
            if (idGrupo != 0 && idMateria != 0 && idActividad != 0 && !inicia.isEmpty() && !finaliza.isEmpty() && !dia.isEmpty()) {
                IntentIntegrator integrator = new IntentIntegrator(SinHorario.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt(getString(R.string.enfocar_camara));
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            } else {
                Toast.makeText(getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eventAutoComplete() {
        txtGrupo.setOnItemClickListener((parent, view, position, id) -> {
            Grupo grupo = (Grupo) parent.getItemAtPosition(position);

            if (grupo != null) {
                String selectedItem = grupo.getName();
                txtGrupo.setText(selectedItem);
                idGrupo = grupo.getId();
            }
        });

        txtMateria.setOnItemClickListener((parent, view, position, id) -> {
            Materia materia = (Materia) parent.getItemAtPosition(position);
            if (materia != null) {
                String selectedItem = materia.getMateria();
                txtMateria.setText(selectedItem);
                idMateria = materia.getId();
            }
        });

        txtInicia.setOnItemClickListener((adapterView, view, i, l) -> inicia = (String) adapterView.getItemAtPosition(i));

        txtFinaliza.setOnItemClickListener((adapterView, view, i, l) -> finaliza = (String) adapterView.getItemAtPosition(i));

        txtDia.setOnItemClickListener((adapterView, view, i, l) -> dia = (String) adapterView.getItemAtPosition(i));

        txtActividad.setOnItemClickListener((adapterView, view, position, id) -> {
            Actividad actividad = (Actividad) adapterView.getItemAtPosition(position);

            if (actividad != null) {
                String selectedItem = actividad.getActividad();
                txtActividad.setText(selectedItem);
                idActividad = actividad.getId();
            }
        });
    }

    private void getGrupos() {
        String token = Peticiones.getPreference(this, "token");

        ArrayList<Grupo> listaGrupos = new ArrayList<>();

        Peticiones.getGrupos(token, response -> {
            if (response.length() > 0) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject grupo = response.getJSONObject(i);

                        listaGrupos.add(new Grupo(grupo.getInt("id"), grupo.getString("name")));
                    }

                    AdapterGrupo adapter = new AdapterGrupo(getApplicationContext(), listaGrupos);
                    txtGrupo.setAdapter(adapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {

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
                    txtActividad.setAdapter(adapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            Toast.makeText(this, "-->" + error, Toast.LENGTH_LONG).show();
        });
    }

    private void getMaterias() {
        String token = Peticiones.getPreference(this, "token");

        ArrayList<Materia> listaMaterias = new ArrayList<>();

        Peticiones.getMaterias(token, response -> {

            if (response.length() > 0) {

                try {
                    JSONArray arrayMaterias = response.getJSONArray("topic");

                    for (int i = 0; i < arrayMaterias.length(); i++) {
                        JSONObject materia = arrayMaterias.getJSONObject(i);

                        listaMaterias.add(new Materia(materia.getInt("id"), materia.getString("name")));
                    }
                    AdapterMateria adapter = new AdapterMateria(getApplicationContext(), listaMaterias);
                    txtMateria.setAdapter(adapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            Toast.makeText(this, "Error -->" + error, Toast.LENGTH_SHORT).show();
        });
    }

    private void createRegister() {

        qrImage.setOnClickListener(view -> {

        });
    }

    private int getIdUser(String token) throws JSONException {

        int id;

        DecodedJWT jwt = JWT.decode(token);
        String payload = jwt.getPayload();
        JSONObject payloadJson = new JSONObject(new String(Base64.decode(payload, Base64.URL_SAFE)));
        id = payloadJson.getInt("id");

        return id;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            } else {

                String token = Peticiones.getPreference(this, "token");
                String laboratorio = result.getContents();
                try {
                    SinRegisterContent content = new SinRegisterContent(idGrupo, idMateria, inicia, finaliza, dia, idActividad, getIdUser(token), laboratorio);

                    Peticiones.postCreateRegisterWithoutSchedule(token, content, response -> {
                        Toast.makeText(this, "Response -->" + response, Toast.LENGTH_SHORT).show();
                    }, error -> {
                        Toast.makeText(this, "Error ->" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}