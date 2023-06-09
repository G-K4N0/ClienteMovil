package com.talentounido.cliente.fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bumptech.glide.Glide;
import com.talentounido.cliente.Login;
import com.talentounido.cliente.R;

import org.json.JSONException;
import org.json.JSONObject;

import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.modelo.User;

public class FragmentMas extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button btnLogout;

    public FragmentMas() {
        // Required empty public constructor
    }

    public static FragmentMas newInstance(String param1, String param2) {
        FragmentMas fragment = new FragmentMas();
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
        View vista = inflater.inflate(R.layout.fragment_mas, container, false);
        ImageView profileImage = vista.findViewById(R.id.imgProfile);
        TextView txtNameProfile = vista.findViewById(R.id.txtPerfil_usuario);

        btnLogout = vista.findViewById(R.id.btnLogout);
        String name, image;

        String token = Peticiones.getPreference(getActivity(),getString(R.string.token));
        if (token != null){
            try {
                name = getIdUser(token).getNickname();
                image = getIdUser(token).getImageUrl();

                txtNameProfile.setText(name);
                JSONObject urlImage = new JSONObject(image);
                String urlEdit = urlImage.getString("url").replace("w_1000","w_500,h_500");
                Uri uriImage = Uri.parse(urlEdit);

                Glide.with(requireActivity())
                        .load(uriImage)
                        .circleCrop()
                        .into(profileImage);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(getActivity(), "Sesión expirado", Toast.LENGTH_SHORT).show();
        }

        logout();
        return vista;
    }
    private void logout() {

        btnLogout.setOnClickListener( v -> {
            Peticiones.setPreference(getActivity(), "token",null);

            Intent intent = new Intent(getActivity(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
    private User getIdUser(String token) throws JSONException {

        int id;
        String name, image;

        DecodedJWT jwt = JWT.decode(token);
        String payload = jwt.getPayload();
        JSONObject payloadJson = new JSONObject(new String(Base64.decode(payload, Base64.URL_SAFE)));
        id = payloadJson.getInt("id");
        name = payloadJson.getString("name");
        image = payloadJson.getString("image");

        return new User(id, name, "docente", image);
    }
}