package com.talentounido.cliente.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.talentounido.cliente.R;

import java.util.ArrayList;

import com.talentounido.cliente.modelo.Horario;

public class AdapterListLabs extends BaseAdapter{

    private ArrayList<Horario> horarios;
    private final int layout;
    private final Context context;

    public AdapterListLabs(Context context, int layout, ArrayList<Horario> horarios){
        this.context = context;
        this.layout = layout;
        this.horarios = horarios;
    }
    @Override
    public int getCount() {return this.horarios.size();}

    @Override
    public Object getItem(int position) {return this.horarios.get(position);}

    @Override
    public long getItemId(int id) {return id;}

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

      ViewHolder holder;
      if(convertView == null)
      {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(R.layout.item_labs_details,null);

        holder = new ViewHolder();
        holder.txtMateria = convertView.findViewById(R.id.txt_lab_detail_materia);
        holder.txtCarrera = convertView.findViewById(R.id.txt_lab_detail_carrera);
        holder.txtEntradaSalida = convertView.findViewById(R.id.txt_lab_detail_entrada_salida);
        holder.txtDia = convertView.findViewById(R.id.txt_lab_detail_dia);
          holder.txtGrupo = convertView.findViewById(R.id.txt_lab_detail_grupo);
        convertView.setTag(holder);
      }else{
        holder= (ViewHolder) convertView.getTag();
      }

        String materia = horarios.get(position).getMateria();
        String carrera = horarios.get(position).getCarrera();
        String entrada_salida = horarios.get(position).getInicia()+" - " + horarios.get(position).getFinaliza();
        String dia = horarios.get(position).getDia();
        String grupo = horarios.get(position).getGrupo();

        holder.txtMateria.setText(materia);
        holder.txtCarrera.setText(carrera);
        holder.txtEntradaSalida.setText(entrada_salida);
        holder.txtDia.setText(dia);
        holder.txtGrupo.setText(grupo);

        return convertView;
    }
    static class ViewHolder{
      private TextView txtMateria;
        private TextView txtCarrera;

        private TextView txtEntradaSalida;
        private TextView txtDia;
        private TextView txtGrupo;

    }
}
