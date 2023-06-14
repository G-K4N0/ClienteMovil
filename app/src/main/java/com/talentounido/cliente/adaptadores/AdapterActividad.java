package com.talentounido.cliente.adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.talentounido.cliente.modelo.Actividad;
import com.talentounido.cliente.modelo.Materia;

import java.util.ArrayList;
import java.util.List;

public class AdapterActividad extends ArrayAdapter<Actividad> {

    private List<Actividad> actividades;
    private ArrayList<Actividad> originalActividades;
    public AdapterActividad(Context context, List<Actividad> actividades) {
        super(context, android.R.layout.simple_list_item_1, actividades);
        this.actividades =  actividades;
        this.originalActividades = new ArrayList<>(actividades);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);

        Actividad actividad = getItem(position);

        if (actividad != null){
            textView.setText(actividad.getActividad());
        }
        return textView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<Actividad> suggestion = new ArrayList<>();

                if (charSequence != null){
                    for (Actividad actividad : actividades){
                        if (actividad.getActividad().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            suggestion.add(actividad);
                        }
                    }
                    results.values = suggestion;
                    results.count = suggestion.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                clear();
                if (filterResults != null && filterResults.count > 0) {
                    addAll((List<Actividad>) filterResults.values);
                } else {
                    addAll(originalActividades);
                }
                notifyDataSetChanged();
            }
        };
    }
}
