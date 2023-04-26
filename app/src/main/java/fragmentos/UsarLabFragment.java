package fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.talentounido.cliente.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsarLabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsarLabFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView txtUsarMateria, txtUsarCarrera, txtUsarSemestre, txtUsarModalidad, txtUsarFase;
    private EditText txtUsarActividad;
    private ImageView qrImage;
    public UsarLabFragment() {
        // Required empty public constructor
    }
    public static UsarLabFragment newInstance(String param1, String param2) {
        UsarLabFragment fragment = new UsarLabFragment();
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
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_usar_lab, container, false);
        txtUsarMateria = vista.findViewById(R.id.txtUsarMateria);
        txtUsarCarrera = vista.findViewById(R.id.txtUsarCarrera);
        txtUsarSemestre = vista.findViewById(R.id.txtUsarSemestre);
        txtUsarModalidad = vista.findViewById(R.id.txtUsarModalidad);
        txtUsarFase = vista.findViewById(R.id.txtFase);
        qrImage = vista.findViewById(R.id.qr_image_scann);
        Glide.with(requireActivity()).load(R.raw.scanner).into(qrImage);
        return vista;
    }
}