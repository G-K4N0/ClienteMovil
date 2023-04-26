package adaptadores;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talentounido.cliente.R;

import java.util.ArrayList;

import modelo.Lab;

public class AdapterRecyclerLabs  extends RecyclerView.Adapter<AdapterRecyclerLabs.LabsViewHolder> implements View.OnClickListener {
    ArrayList<Lab> listLabs;
    private View.OnClickListener listener;
    public AdapterRecyclerLabs(ArrayList<Lab> listLabs) {
        this.listLabs = listLabs;
    }

    @NonNull
    @Override
    public AdapterRecyclerLabs.LabsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_labs_list, null, false);
        view.setOnClickListener(this);
        return new LabsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerLabs.LabsViewHolder holder, int position) {
        holder.txtNumber.setText(String.valueOf(listLabs.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return listLabs.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }
    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public class LabsViewHolder extends RecyclerView.ViewHolder{

        TextView txtName,txtNumber;
        @SuppressLint("CutPasteId")
        public LabsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumber = itemView.findViewById(R.id.txtNumberLab);
        }
    }
}
