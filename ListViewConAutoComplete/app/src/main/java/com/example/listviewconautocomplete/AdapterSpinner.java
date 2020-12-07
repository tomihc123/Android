package com.example.listviewconautocomplete;

import android.content.Context;
import android.media.Image;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterSpinner extends ArrayAdapter<Novela> {

    public AdapterSpinner(@NonNull Context context, @NonNull List<Novela> objects) {
        super(context,0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);

    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TextView nombre;
        ImageView imagen;
        ViewHolder holder;
        if(row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            nombre = row.findViewById(R.id.nombre);
            imagen = row.findViewById(R.id.logo);
            holder = new ViewHolder(nombre, imagen);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Novela novela = getItem(position);
        holder.getTexto().setText(novela.getNombre());
        holder.getImagen().setImageResource(novela.getImagen());
        return row;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);

    }

    class ViewHolder {

        TextView texto;
        ImageView imagen;

        public ViewHolder(TextView texto, ImageView imagen) {
            this.texto = texto;
            this.imagen = imagen;
        }

        public TextView getTexto() {
            return texto;
        }

        public ImageView getImagen() {
            return imagen;
        }
    }

}
