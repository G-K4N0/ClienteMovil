package com.talentounido.cliente.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.talentounido.cliente.R;

import org.json.JSONException;
import org.json.JSONObject;

import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.modelo.Reporte;

public class FragmentReportes extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText txtLaboratorios,txtProblema, txtDescripcion;
    private Button btnReportar;
    private String token;

    public FragmentReportes() {
    }

    public static FragmentReportes newInstance(String param1, String param2) {
        FragmentReportes fragment = new FragmentReportes();
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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reportes, container, false);
        btnReportar = view.findViewById(R.id.btnReportar);
        txtLaboratorios = view.findViewById(R.id.txtOpciones_lab);
        txtProblema = view.findViewById(R.id.txtReporte_problema);
        txtDescripcion = view.findViewById(R.id.txtReporte_descripcion);
        click();
        return view;
    }

    private void click(){
        btnReportar.setOnClickListener(view -> {
            int lab = Integer.parseInt(txtLaboratorios.getText().toString().split(" ")[1]);
            String titulo = txtProblema.getText().toString();
            String problema = txtDescripcion.getText().toString();
            String token = Peticiones.getPreference(getActivity(),"token");
            Toast.makeText(getActivity(), "-> " + lab, Toast.LENGTH_SHORT).show();
            try {
                Reporte reporte = new Reporte(lab,titulo, problema, getIdUser(token));
                postReporte(reporte);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void postReporte (Reporte reporte) throws JSONException {
        token = Peticiones.getPreference(getActivity(),getString(R.string.token));

        Peticiones.postReporte(token, reporte, response -> {
            try {
                String message = response.getString("message");
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Toast.makeText(getActivity(), "Reporte fallido, intentalo mas tarde" + error.getMessage(), Toast.LENGTH_SHORT).show());
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