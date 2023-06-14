package com.talentounido.cliente.adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.talentounido.cliente.modelo.Materia;

import java.util.ArrayList;
import java.util.List;

public class AdapterMateria extends ArrayAdapter<Materia> {

    private List<Materia> materias;
    private ArrayList<Materia> originalMaterias;

    public AdapterMateria(Context context, List<Materia> materias) {
        super(context, android.R.layout.simple_list_item_1, materias);
        this.materias =  materias;
        this.originalMaterias = new ArrayList<>(materias);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        Materia materia = getItem(position);
        if (materia != null) {
            textView.setText(materia.getMateria());
        }
        return textView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Materia> suggestions = new ArrayList<>();
                if (constraint != null) {
                    for (Materia materia : materias) {
                        if (materia.getMateria().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(materia);
                        }
                    }
                    results.values = suggestions;
                    results.count = suggestions.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                if (results != null && results.count > 0) {
                    addAll((List<Materia>) results.values);
                } else {
                    addAll(originalMaterias);
                }
                notifyDataSetChanged();
            }

        };
    }
}

