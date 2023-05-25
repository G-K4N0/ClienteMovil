package com.talentounido.cliente.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.talentounido.cliente.R;

import java.util.ArrayList;

import com.talentounido.cliente.modelo.Horario;

public class AdapterHorarios extends BaseAdapter {
    private final ArrayList<Horario> horarioList;
    private final Context context;

    public AdapterHorarios (Context context, ArrayList<Horario> horarioList){
        this.context = context;
        this.horarioList = horarioList;
    }
    @Override
    public int getCount() {
        return this.horarioList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.horarioList.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_labs_list, viewGroup, false);
            holder = new ViewHolder();

            holder.txtInicio = view.findViewById(R.id.tv_horario_inicio);
            holder.txtFin = view.findViewById(R.id.tv_horario_fin);
            holder.txtLab = view.findViewById(R.id.tv_horario_lab);
            holder.txtMateria = view.findViewById(R.id.tv_horario_materia);
            holder.txtGrupo = view.findViewById(R.id.tv_horario_grupo);
            holder.txtCarrera = view.findViewById(R.id.tv_horario_carrera);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Horario horario = horarioList.get(position);

        holder.txtInicio.setText(horario.getInicia());
        holder.txtFin.setText(horario.getFinaliza());
        holder.txtLab.setText(horario.getLaboratorio());
        holder.txtMateria.setText(horario.getMateria());
        holder.txtGrupo.setText(horario.getGrupo());
        holder.txtCarrera.setText(horario.getCarrera());

        return view;
    }

    static class ViewHolder {
        private TextView txtInicio;
        private TextView txtFin;
        private TextView txtLab;
        private TextView txtMateria;
        private TextView txtGrupo;
        private TextView txtCarrera;
    }
}
