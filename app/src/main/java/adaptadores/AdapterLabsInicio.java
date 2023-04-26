package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.talentounido.cliente.R;

import java.util.ArrayList;

import modelo.HorarioActual;

public class AdapterLabsInicio extends BaseAdapter {

    private ArrayList<HorarioActual> listaHorarios;
    private final Context context;

    public AdapterLabsInicio(ArrayList<HorarioActual> horarioActualArrayList, Context context) {
        this.listaHorarios = horarioActualArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.listaHorarios.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaHorarios.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_inicio_labs, viewGroup, false);
            holder = new ViewHolder();
            holder.txtInicio = view.findViewById(R.id.tv_tiempo_inicio);
            holder.txtFin = view.findViewById(R.id.tv_tiempo_fin);
            holder.txtNombreLab = view.findViewById(R.id.tv_nombre_lab);
            holder.txtMateria = view.findViewById(R.id.tv_nombre_materia);
            holder.txtDocente = view.findViewById(R.id.tv_nombre_docente);
            holder.layoutIsOcupado = view.findViewById(R.id.layoutIsOcupado);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        HorarioActual horarioActual = listaHorarios.get(i);

        holder.txtInicio.setText(horarioActual.getInicio());
        holder.txtFin.setText(horarioActual.getFin());
        holder.txtNombreLab.setText(horarioActual.getLabNombre());
        holder.txtMateria.setText(horarioActual.getMateria());
        holder.txtDocente.setText(horarioActual.getDocente());

        if (horarioActual.isOcupado()) {
            holder.layoutIsOcupado.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.layoutIsOcupado.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        view.setOnClickListener(v -> Toast.makeText(context, "Has hecho clic en el elemento " + i, Toast.LENGTH_SHORT).show());
        return view;
    }

    static class ViewHolder{
        private TextView txtInicio;
        private TextView txtFin;
        private TextView txtNombreLab;
        private TextView txtMateria;
        private TextView txtDocente;
        private LinearLayout layoutIsOcupado;
    }
}
