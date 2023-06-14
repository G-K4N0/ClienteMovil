package com.talentounido.cliente.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.talentounido.cliente.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.adaptadores.AdapterLabsInicio;
import com.talentounido.cliente.funciones.Tiempo;
import com.talentounido.cliente.modelo.HorarioActual;

public class FragmentInicio extends Fragment {

    private Adapter adapterLabs;
    private ArrayList<HorarioActual> horarioActuals;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public FragmentInicio() {

    }

    public static FragmentInicio newInstance(String param1, String param2) {
        FragmentInicio fragment = new FragmentInicio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        ListView listView = view.findViewById(R.id.lst_inicio_labs);

        adapterLabs = new AdapterLabsInicio(new ArrayList<>(), getActivity());
        listView.setAdapter((ListAdapter) adapterLabs);
        horarioActuals = new ArrayList<>();

        getHorarioActuals(listView);


        final Handler handler = new Handler(Looper.myLooper());
        final int delay =  30 * 60 * 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                actualizarDatos(listView);

                handler.postDelayed(this, delay);
            }
        }, delay);

        return view;
    }


    private void actualizarDatos(ListView listView) {
        synchronized (adapterLabs) {
            horarioActuals.clear();
            adapterLabs.notifyAll();
        }
        getHorarioActuals(listView);
    }

    private void getHorarioActuals(ListView listView) {

        if (!isAdded()) {
            return;
        }
        String token = Peticiones.getPreference(getActivity(), getString(R.string.token));
        horarioActuals = new ArrayList<>();
        Peticiones.getHorariosByDias(token, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject dataJson = response.getJSONObject(i);
                    String inicio = dataJson.getString("inicia");
                    String finaliza = dataJson.getString("finaliza");
                    String laboratorio = dataJson.getString("laboratorio");
                    String carrera = dataJson.getString("carrera");
                    String materia = dataJson.getString("materia");
                    String grupo = dataJson.getString("grupo");
                    String docente = dataJson.getString("docente");
                    String image = dataJson.getString("image");
                    int ocupado = dataJson.getInt("ocupado");
                    boolean isOcupado = ocupado >= 1;

                    if (hoursFilter(inicio, finaliza)) {
                        horarioActuals.add(new HorarioActual(inicio, finaliza, laboratorio, materia, carrera, grupo, docente, image, isOcupado));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            if (horarioActuals.isEmpty()) {
                Toast.makeText(getActivity(), "No hay horarios asignados en este momento", Toast.LENGTH_SHORT).show();
            } else {
                adapterLabs = new AdapterLabsInicio(horarioActuals, getActivity());
                listView.setAdapter((ListAdapter) adapterLabs);
            }
        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private boolean hoursFilter(String initHourRange, String finalHourRange) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return false;
        }
        return Tiempo.inInterval(initHourRange, finalHourRange);
    }
}
