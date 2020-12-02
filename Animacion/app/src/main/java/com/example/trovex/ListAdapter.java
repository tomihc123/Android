package com.example.trovex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElement_Obras> obras;
    private LayoutInflater inflater;
    private Context context;

    public ListAdapter(List<ListElement_Obras> obras, Context context) {

        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.obras = obras;

    }

    @Override
    public int getItemCount() { return this.obras.size(); }

    public void filterList(ArrayList<ListElement_Obras> filtro) {
        this.obras = filtro;
        notifyDataSetChanged();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(parent.getContext()).inflate(R.layout.list_element_obras, parent,false);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {

          holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
          holder.bindData(obras.get(position));

    }


    public void setObras(List<ListElement_Obras> obras) { this.obras = obras; }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icono_obra;
        TextView calle, cliente, status;
        CardView cv;


        ViewHolder(View obraView) {

            super(obraView);

            icono_obra = obraView.findViewById(R.id.ImgObra);
            calle = obraView.findViewById(R.id.calle_Obra);
            cliente = obraView.findViewById(R.id.cliente);
            status = obraView.findViewById(R.id.pagado);
            cv = obraView.findViewById(R.id.cardview_obra);

        }

        void bindData(final ListElement_Obras obra) {

            calle.setText(obra.getCalle());
            cliente.setText(obra.getCliente());
            status.setText(obra.getStatus());

        }


    }




}
