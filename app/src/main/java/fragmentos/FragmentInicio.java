package fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.talentounido.cliente.R;

import java.util.ArrayList;

import adaptadores.AdapterLabsInicio;
import modelo.HorarioActual;

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

        horarioActuals = new ArrayList<>();
        horarioActuals.add(new HorarioActual(1, "08:00", "10:00", "Laboratorio 1", "POO", "Docente 1", true));

        adapterLabs = new AdapterLabsInicio(horarioActuals, getActivity());
        listView.setAdapter((ListAdapter) adapterLabs);
        return view;
    }
}