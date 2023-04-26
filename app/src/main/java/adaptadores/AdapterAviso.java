package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.talentounido.cliente.R;

import java.util.ArrayList;

import modelo.Aviso;

public class AdapterAviso extends BaseAdapter {
    private ArrayList<Aviso> listaAviso;
    private final Context context;

    public AdapterAviso (Context context, ArrayList<Aviso> listaAviso){
        this.context= context;
        this.listaAviso = listaAviso;
    }
    @Override
    public int getCount() {
        return this.listaAviso.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaAviso.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_aviso,viewGroup,false);
            holder = new ViewHolder();
            holder.txtTitulo = view.findViewById(R.id.txtCard_title);
            holder.txtAviso = view.findViewById(R.id.txtCard_body);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Aviso aviso = listaAviso.get(i);
        holder.txtTitulo.setText(aviso.getTitulo());
        holder.txtAviso.setText(aviso.getCuerpo());

        return view;
    }

    static class  ViewHolder {
        private TextView txtTitulo;
        private TextView txtAviso;
    }
}
