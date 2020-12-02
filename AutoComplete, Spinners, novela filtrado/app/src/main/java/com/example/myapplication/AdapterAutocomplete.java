package com.example.myapplication;

import android.content.Context;
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

public class AdapterAutocomplete extends ArrayAdapter<Novelas> {

    private ArrayList<Novelas> fullNovelas;

    public AdapterAutocomplete(@NonNull Context context, @NonNull ArrayList<Novelas> objects) {
        super(context, 0, objects);
        fullNovelas = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        TextView text;
        ImageView img;
        ViewHolder holder;

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




    private Filter novelasFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Novelas> suggestions = new ArrayList<>();

            if (constraint == null ||constraint.length() == 0) {
                suggestions.addAll(fullNovelas);
            } else {
                String introducido = constraint.toString().toLowerCase().trim();

                for(Novelas novela: fullNovelas) {
                    if(novela.getNombre().toLowerCase().contains(introducido)) {

                        suggestions.add(novela);

                    }
                }
            }

            filterResults.values = suggestions;
            filterResults.count =  suggestions.size();
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            clear();
            addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Novelas) resultValue).getNombre();
        }
    };

    @NonNull
    @Override
    public Filter getFilter() {
            return novelasFiltro;
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
