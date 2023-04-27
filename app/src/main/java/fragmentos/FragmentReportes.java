package fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.talentounido.cliente.R;

public class FragmentReportes extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private EditText txtLaboratorios,txtProblema, txtDescripcion;
    private Button btnReportar;

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
            String lab = txtLaboratorios.getText().toString();
            String problema = txtProblema.getText().toString();
            String descripcion = txtDescripcion.getText().toString();
            Toast.makeText(getActivity(), lab + problema + descripcion, Toast.LENGTH_SHORT).show();
        });
    }
}