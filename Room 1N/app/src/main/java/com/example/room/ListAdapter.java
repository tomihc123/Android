/*package com.example.room;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NovelaConComentarios> novelas = new ArrayList<>();
    private final OnItemClickListener listener;

    public ListAdapter(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(novelas.get(position).getNovela().getNombre().toLowerCase().equals("reverend insanity")) {
            return 0;
        } else {
            return 1;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if(viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
            return new ViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ViewHolder2(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Novela novela = novelas.get(position).getNovela();
        if(getItemViewType(position) == 0) {
            ((ViewHolder)holder).nombre.setText(novela.getNombre());
            ((ViewHolder)holder).imagen.setImageResource(novela.getImagen());
            ((ViewHolder)holder).descripcion.setText(novela.getDescripcion());
            ((ViewHolder)holder).autor.setText(novela.getAutor());
        } else {
            ((ViewHolder2)holder).nombre.setText(novela.getNombre());
            ((ViewHolder2)holder).imagen.setImageResource(novela.getImagen());
        }
    }


    public void setNovelas(List<NovelaConComentarios> novelas) {
        this.novelas = novelas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.novelas.size();
    }

    public Novela getItemAtPosition(int position) {
        return novelas.get(position).getNovela();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre, descripcion, autor, leerMas, editar;
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            imagen = itemView.findViewById(R.id.logo);
            descripcion = itemView.findViewById(R.id.descripcion);
            autor = itemView.findViewById(R.id.autor);
            leerMas = itemView.findViewById(R.id.leerMas);
            editar = itemView.findViewById(R.id.editar);

            leerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()), leerMas.getId());
                }
            });

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()), editar.getId());
                }
            });

        }

    }


     class ViewHolder2 extends RecyclerView.ViewHolder {

       private TextView nombre;
       private ImageView imagen;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            imagen = itemView.findViewById(R.id.logo);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NovelaConComentarios novela, int id);
    }

} */



package com.example.room;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<NovelaConComentarios> novelas = new ArrayList<>();
    private final OnItemClickListener listener;

    public ListAdapter(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Novela novela = novelas.get(position).getNovela();
        holder.nombre.setText(novela.getNombre());
        holder.imagen.setImageResource(novela.getImagen());
        holder.descripcion.setText(novela.getDescripcion());
        holder.autor.setText(novela.getAutor());
    }

    public void setNovelas(List<NovelaConComentarios> novelas) {
        this.novelas = novelas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.novelas.size();
    }

    public Novela getItemAtPosition(int position) {
        return novelas.get(position).getNovela();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre, descripcion, autor, leerMas, editar;
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            imagen = itemView.findViewById(R.id.logo);
            descripcion = itemView.findViewById(R.id.descripcion);
            autor = itemView.findViewById(R.id.autor);
            leerMas = itemView.findViewById(R.id.leerMas);
            editar = itemView.findViewById(R.id.editar);

            leerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()), leerMas.getId());
                }
            });

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()), editar.getId());
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(NovelaConComentarios novela, int id);
    }

}