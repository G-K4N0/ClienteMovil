package fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.talentounido.cliente.R;

import java.util.ArrayList;

import adaptadores.AdapterAviso;
import modelo.Aviso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvisoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvisoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ArrayList<Aviso> listaAviso;
    private ListView lstVista;
    private AdapterAviso adapterAviso;
    public AvisoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvisoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvisoFragment newInstance(String param1, String param2) {
        AvisoFragment fragment = new AvisoFragment();
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
        View view = inflater.inflate(R.layout.fragment_aviso, container, false);
        lstVista = view.findViewById(R.id.lstAvisos);

        listaAviso = new ArrayList<>();
        listaAviso.add(new Aviso(1,"Primer Aviso", "Esto es un test de la parte de aviso, es una prueba para verificar el ajuste automático del texto"));
        listaAviso.add(new Aviso(2,"Segundo Aviso", "Esto es un test de la parte de aviso, es una prueba para verificar el ajuste automático del texto"));

        adapterAviso = new AdapterAviso(getActivity(), listaAviso);
        lstVista.setAdapter(adapterAviso);

        return view;
    }
}