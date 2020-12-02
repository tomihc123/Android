package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterSpinner extends ArrayAdapter<Novelas> {

    public AdapterSpinner(Context context, ArrayList<Novelas> novelas) {
        super(context, 0, novelas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View row = convertView;
        TextView text;
        ImageView img;

        if(row == null) {

            row = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            text = row.findViewById(R.id.equipo);
            img = row.findViewById(R.id.logo);
            holder = new ViewHolder(text, img);
            row.setTag(holder);

        } else {

            holder = (ViewHolder) row.getTag();
        }

        Novelas novelas = getItem(position);

        holder.getText().setText(novelas.getNombre());
        holder.getImg().setImageResource(novelas.getImagen());

        return row;

    }



    class ViewHolder {

        TextView text;
        ImageView img;

        public ViewHolder(TextView text, ImageView img) {
            this.text = text;
            this.img = img;
        }

        public TextView getText() {
            return text;
        }

        public ImageView getImg() {
            return img;
        }
    }

}


