package com.example.listviewconautocomplete;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterAutoComplete extends ArrayAdapter<Novela> {

    ArrayList<Novela> listadoNovelasCompletas;

    public AdapterAutoComplete(@NonNull Context context, @NonNull ArrayList<Novela> objects) {
        super(context, 0, objects);
        listadoNovelasCompletas = new ArrayList<>(objects);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (getItem(position).getNombre().toLowerCase().equals("reverend insanity")) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getItemLayout(int position) {

        switch (getItemViewType(position)) {

            case 0:
                return R.layout.row;

            case 1:
                return R.layout.row2;


            default:
                return 0;

        }

    }

    private Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filtroResultados = new FilterResults();
            ArrayList<Novela> sugerencias = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                sugerencias.addAll(listadoNovelasCompletas);
            } else {
                for (Novela novelas: listadoNovelasCompletas) {
                    if(novelas.getNombre().toLowerCase().contains(constraint.toString().toLowerCase().trim())) {
                        sugerencias.add(novelas);
                    }
                }
            }

            filtroResultados.values = sugerencias;
            filtroResultados.count = sugerencias.size();
            return filtroResultados;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            clear();
            addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Novela) resultValue).getNombre();
        }

    };

    @NonNull
    @Override
    public Filter getFilter() {
        return filtro;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        TextView nombre;
        ImageView imagen;
        ViewHolder holder;

        if(row == null) {
            row = LayoutInflater.from(getContext()).inflate(getItemLayout(position), parent, false);
            nombre = row.findViewById(R.id.nombre);
            imagen = row.findViewById(R.id.logo);
            holder = new ViewHolder(nombre, imagen);
            row.setTag(holder);
        } else {
           holder = (ViewHolder) row.getTag();
        }

        Novela novela = getItem(position);
        holder.getNombre().setText(novela.getNombre());
        holder.getImagen().setImageResource(novela.getImagen());

        return row;

    }


    class ViewHolder {

        private TextView nombre;
        private ImageView imagen;

        public ViewHolder(TextView nombre, ImageView imagen) {
            this.nombre = nombre;
            this.imagen = imagen;
        }

        public TextView getNombre() {
            return nombre;
        }

        public ImageView getImagen() {
            return imagen;
        }
    }

}
