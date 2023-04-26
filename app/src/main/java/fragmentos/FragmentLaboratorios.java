package fragmentos;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.talentounido.cliente.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import PeticionesVolley.Peticiones;
import adaptadores.AdapterRecyclerLabs;
import modelo.Lab;

public class FragmentLaboratorios extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerLabs;
    ArrayList<Lab> arrayLabs;
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
        recyclerLabs = vista.findViewById(R.id.idRecycler);
        recyclerLabs.setLayoutManager(new GridLayoutManager(getContext(), 2));

        llenarLista();

        return vista;
    }

    public void llenarLista(){
        String token = Peticiones.getToken(getActivity());
        Peticiones.getLabs(token,response->{
            arrayLabs = new ArrayList<>();
            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject labDataJson = response.getJSONObject(i);
                    arrayLabs.add(new Lab(labDataJson.getInt("id"),labDataJson.getString("name"), labDataJson.getBoolean("ocupado")));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            AdapterRecyclerLabs adapter = new AdapterRecyclerLabs(arrayLabs);
            recyclerLabs.setAdapter(adapter);
            adapter.setOnClickListener(this::pasarData);
        },error -> {
            Toast.makeText(getContext(), "No se puede establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
        });
    }

    private void pasarData(View view){
        int position = recyclerLabs.getChildAdapterPosition(view);
        Lab labSeleccionado = arrayLabs.get(position);

        DetallesLab detallesLab = new DetallesLab();
        Bundle bundle = new Bundle();
        bundle.putString("nombreLab", labSeleccionado.getName());
        bundle.putBoolean("statusLab", labSeleccionado.getStatus());
        detallesLab.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, detallesLab).addToBackStack(null).commit();
    }
    public void onButtonPressed(Uri uri){
        if (mListener != null){
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(Uri uri);
    }
}