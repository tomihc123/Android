package com.example.listwithframentsandwiewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {


    private final MutableLiveData<ArrayList<Novela>> novelas = new MutableLiveData<>();
    private final MutableLiveData<String> visualizacion = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSmall = new MutableLiveData<>();


    public void setVisualizacion(String visualizacion) {
            this.visualizacion.setValue(visualizacion);
    }

    public MutableLiveData<Boolean> getIsSmall() { return isSmall; }

    public MutableLiveData<ArrayList<Novela>> getNovelas() {
        return novelas;
    }

    public MutableLiveData<String> getVisualizacion() {
        return visualizacion;
    }


    public void addNovela(Novela novela) {

        ArrayList<Novela> listsAux = new ArrayList<>();

        if(this.getNovelas().getValue() != null) {

            listsAux = this.getNovelas().getValue();

        }

        listsAux.add(novela);
        this.novelas.setValue(listsAux);
    }

    public MainViewModel() {
        if(this.novelas.getValue() == null) {
            loadData();
        }
    }

    private void loadData() {

        ArrayList<Novela> novelas = new ArrayList<>();
        novelas.add(new Novela("Reverend Insanity", R.drawable.fang, "La mejor"));
        this.getNovelas().setValue(novelas);
    }

}
