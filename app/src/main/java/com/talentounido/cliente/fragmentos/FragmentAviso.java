package com.talentounido.cliente.fragmentos;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.talentounido.cliente.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.talentounido.cliente.PeticionesVolley.Peticiones;
import com.talentounido.cliente.adaptadores.AdapterAviso;
import com.talentounido.cliente.modelo.Aviso;

public class FragmentAviso extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String CHANNEL_ID = "centro";

    private Context context;
    private ArrayList<Aviso> listaAviso;
    private ListView lstVista;
    private AdapterAviso adapterAviso;

    public FragmentAviso() {
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
        String mParam1;
        String mParam2;
        if (args != null) {
            mParam1 = args.getString(ARG_PARAM1);
            mParam2 = args.getString(ARG_PARAM2);
        } else {
            mParam1 = "";
            mParam2 = "";
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aviso, container, false);
        lstVista = view.findViewById(R.id.lstAvisos);

        asyncTask();

        final Handler handler = new Handler(Looper.myLooper());
        final int delay = 5000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                actualizarAvisos();
                handler.postDelayed(this, delay);
            }
        }, delay);

        return view;
    }

    private void asyncTask() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(this::getAvisos);
    }

    private void getAvisos() {
        String token = Peticiones.getPreference(getActivity(), getString(R.string.token));

        Peticiones.getAvisos(token, response -> {
            listaAviso = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject avisoJson = response.getJSONObject(i);
                    String titulo = avisoJson.getString("titulo");
                    String cuerpo = avisoJson.getString("detalles");

                    listaAviso.add(new Aviso(i, titulo, cuerpo));
                } catch (JSONException e) {
                    showToast("Error al procesar la respuesta del servidor.");
                    e.printStackTrace();
                }
            }
            adapterAviso = new AdapterAviso(getActivity(), listaAviso);
            lstVista.setAdapter(adapterAviso);

        }, error -> showToast(error.getMessage()));
    }

    private void actualizarAvisos() {
        if (!isAdded()) {
            return;
        }

        String token = Peticiones.getPreference(getActivity(), getString(R.string.token));

        Peticiones.getAvisos(token, response -> {
            ArrayList<Aviso> nuevosAvisos = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject avisoJson = response.getJSONObject(i);
                    String titulo = avisoJson.getString("titulo");
                    String cuerpo = avisoJson.getString("detalles");

                    nuevosAvisos.add(new Aviso(i, titulo, cuerpo));
                } catch (JSONException e) {
                    showToast("Error al procesar la respuesta del servidor.");
                    e.printStackTrace();
                }
            }

            if (!nuevosAvisos.isEmpty()) {
                listaAviso.clear();
                listaAviso.addAll(nuevosAvisos);
                adapterAviso.notifyDataSetChanged();

                mostrarNotificacionNuevosAvisos();
            }
        },error -> showToast(error.getMessage()));
    }

    private void mostrarNotificacionNuevosAvisos() {

        int notificationId = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_aviso_foreground)
                .setContentTitle("Nuevo Aviso")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(notificationId, builder.build());
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
