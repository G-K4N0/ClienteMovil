package com.talentounido.cliente.fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.adaptadores.AdapterLabsInicio;
import com.talentounido.cliente.modelo.HorarioActual;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentInicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInicio extends Fragment {

    private ListView listView;
    private Adapter adapterLabs;
    private ArrayList<HorarioActual> horarioActuals;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        listView = view.findViewById(R.id.lst_inicio_labs);

        getHorarioActuals(listView);
        String fecha = obtenerFechaActual();
        Toast.makeText(getActivity(), fecha, Toast.LENGTH_SHORT).show();
        return view;
    }

    private void getHorarioActuals(ListView listView){
        String token = Peticiones.getToken(getActivity());
        horarioActuals = new ArrayList<>();
        Peticiones.getHorariosByDias(token, response -> {
            for (int i = 0; i < response.length(); i++){
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
                    boolean isOcupado;
                    isOcupado = ocupado >= 1;

                    horarioActuals.add(new HorarioActual(inicio, finaliza,laboratorio,materia,carrera,grupo,docente, image, isOcupado));
                }catch(JSONException e){
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            adapterLabs = new AdapterLabsInicio(horarioActuals, getActivity());
            listView.setAdapter((ListAdapter) adapterLabs);
        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show());
    }
    private String obtenerFechaActual() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayOfWeek = dateFormat.format(calendar.getTime());

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        @SuppressLint("DefaultLocale") String dateTime = String.format("%s, %02d/%02d/%04d %02d:%02d:%02d", dayOfWeek, day, month, year, hour, minute, second);

        return dateTime;
    }
}