package com.talentounido.cliente.fragmentos;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.talentounido.cliente.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.adaptadores.AdapterAviso;
import com.talentounido.cliente.modelo.Aviso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAviso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAviso extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ArrayList<Aviso> listaAviso;
    private ListView lstVista;
    private AdapterAviso adapterAviso;
    public FragmentAviso() {
        // Required empty public constructor
    }

    public static FragmentAviso newInstance(String param1, String param2) {
        FragmentAviso fragment = new FragmentAviso();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mParam1 = args.getString(ARG_PARAM1);
            mParam2 = args.getString(ARG_PARAM2);
        } else {
            mParam1 = "";
            mParam2 = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aviso, container, false);
        lstVista = view.findViewById(R.id.lstAvisos);

        new  GetAvisosTask().execute();
        return view;
    }
    @SuppressLint("StaticFieldLeak")
    private class GetAvisosTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            getAvisos();
            return null;
        }
    }
    private void getAvisos(){
        String token = Peticiones.getToken(getActivity());

        Peticiones.getAvisos(token,response -> {
            listaAviso = new ArrayList<>();

            for (int i = 0 ; i < response.length(); i++){
                try {
                    JSONObject avisoJson = response.getJSONObject(i);
                    String titulo = avisoJson.getString("titulo");
                    String cuerpo = avisoJson.getString("detalles");

                    listaAviso.add(new Aviso(i,titulo,cuerpo));
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Error al procesar la respuesta del " +
                            "servidor.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            adapterAviso = new AdapterAviso(getActivity(), listaAviso);
            lstVista.setAdapter(adapterAviso);

        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show());
    }

}