package fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.talentounido.cliente.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import PeticionesVolley.Peticiones;
import modelo.Horario;


public class DetallesLab extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView txtDetailName;
    private TextView txtStatus;
    private ListView listLabs;
    private ArrayList<Horario> horarios;
    public DetallesLab() {
        // Required empty public constructor
    }

    public static DetallesLab newInstance(String param1, String param2) {
        DetallesLab fragment = new DetallesLab();
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
        View vista = inflater.inflate(R.layout.fragment_detalles_lab, container, false);
        txtDetailName = vista.findViewById(R.id.txt_labs_detail_name);
        txtStatus = vista.findViewById(R.id.txt_labs_status_detail);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String nombreLab = bundle.getString("nombreLab");
            boolean statusLab = bundle.getBoolean("statusLab");

            txtDetailName.setText(nombreLab);
            if (!statusLab)
                txtStatus.setText(R.string.ocupado);
            txtStatus.setText(R.string.libre);

            try {
                labsByDocente();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return vista;
    }

    private void labsByDocente() throws JSONException {
        String token = Peticiones.getToken(getActivity());
        Peticiones.getHorarios(token,getIdUser(token),response -> {
            Toast.makeText(getActivity(), "-> " + response, Toast.LENGTH_SHORT).show();
        }, error -> {
            Toast.makeText(getActivity(), "error -> "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
    private int getIdUser(String token) throws JSONException {
        int id = 0;
            DecodedJWT jwt = JWT.decode(token);
            String payload = jwt.getPayload();
            JSONObject payloadJson = new JSONObject(new String(Base64.decode(payload, Base64.URL_SAFE)));
             id = payloadJson.getInt("id");
        Toast.makeText(getActivity(), "id -> " + id, Toast.LENGTH_SHORT).show();
        return id;
    }
}