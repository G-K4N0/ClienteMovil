package com.talentounido.cliente.adaptadores;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.talentounido.cliente.modelo.Grupo;
import com.talentounido.cliente.modelo.Materia;

import java.util.ArrayList;
import java.util.List;
public class AdapterGrupo extends ArrayAdapter<Grupo> {
    private final List<Grupo> grupos;
    private final ArrayList<Grupo> originalGrupos;
    public AdapterGrupo(Context context, List<Grupo> grupos) {
        super(context, android.R.layout.simple_list_item_1, grupos);
        this.grupos = grupos;
        this.originalGrupos = new ArrayList<>(grupos);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        Grupo grupo = getItem(position);
        if (grupo != null) {
            textView.setText(grupo.getName());
        }
        return textView;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Grupo> suggestions = new ArrayList<>();
                if (constraint != null) {
                    for (Grupo grupo : grupos) {
                        if (grupo.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(grupo);
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
                    addAll((List<Grupo>) results.values);
                } else {
                    addAll(originalGrupos);
                }
                notifyDataSetChanged();
            }
        };
    }
}