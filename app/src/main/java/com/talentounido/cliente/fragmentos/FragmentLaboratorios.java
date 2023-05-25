package com.talentounido.cliente.fragmentos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.talentounido.cliente.R;
import com.talentounido.cliente.RegistroHorario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.adaptadores.AdapterHorarios;
import com.talentounido.cliente.modelo.Horario;

public class FragmentLaboratorios extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private AdapterHorarios adapterHorarios;
    private ArrayList<Horario> horarios;
    private ListView listView;

    public FragmentLaboratorios() {
        // Required empty public constructor
    }

    public static FragmentLaboratorios newInstance(String param1, String param2) {
        FragmentLaboratorios fragment = new FragmentLaboratorios();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_laboratorios, container, false);
        listView = vista.findViewById(R.id.lst_horarios);

        try {
            llenarLista();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return vista;
    }

    public void llenarLista() throws JSONException {
        String token = Peticiones.getToken(getActivity());

        if (token == null) {
            Toast.makeText(getContext(), "No se puede obtener el token", Toast.LENGTH_SHORT).show();
        } else {
            Peticiones.getHorariosByDocente(token, getIdUser(token), response -> {
                horarios = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject horarioDataJson = response.getJSONObject(i);
                        int id = horarioDataJson.getInt("id");
                        String inicia = horarioDataJson.getString("inicia");
                        String finaliza = horarioDataJson.getString("finaliza");
                        String dia = horarioDataJson.getString("dia");
                        String usuario = horarioDataJson.getString("Usuario");
                        String materia = horarioDataJson.getString("Materia");
                        String lab = horarioDataJson.getString("Laboratorio");
                        String grupo = horarioDataJson.getString("Grupo");
                        String carrera = horarioDataJson.getString("Carrera");
                        horarios.add(new Horario(id, inicia, finaliza, dia, usuario, materia, lab, grupo, carrera));
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Error al procesar la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                adapterHorarios = new AdapterHorarios(getActivity(), horarios);
                listView.setAdapter((ListAdapter) adapterHorarios);

                listView.setOnItemClickListener((adapterView, view, position, l) -> {

                    try {
                        Horario horario = horarios.get(position);

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("horario", horario);

                        Intent registroDeUso = new Intent(getActivity(), RegistroHorario.class);
                        registroDeUso.putExtras(bundle);
                        startActivity(registroDeUso);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error al abrir  la sección de registro", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
            }, error -> {
                Toast.makeText(getContext(), "No se puede establecer conexión con el servidor", Toast.LENGTH_SHORT).show();
            });
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private int getIdUser(String token) throws JSONException {
        int id;
        DecodedJWT jwt = JWT.decode(token);
        String payload = jwt.getPayload();
        JSONObject payloadJson = new JSONObject(new String(Base64.decode(payload, Base64.URL_SAFE)));
        id = payloadJson.getInt("id");
        return id;
    }
}