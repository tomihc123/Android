package com.example.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NovelaViewModel extends AndroidViewModel {

    private NovelaRepository novelaRepository;

        public NovelaViewModel(Application application) {

            super(application);
            novelaRepository = new NovelaRepository(application);

        }

        public LiveData<List<Novela>> obtenerNovelas() {

            return novelaRepository.obtenerNovelas();

         }

         public void insertar(Novela novela) {
            novelaRepository.insertar(novela);
         }

         public void actualizar(Novela novela) {
            novelaRepository.actualizar(novela);
         }

         public void eliminar(Novela novela) {
            novelaRepository.eliminar(novela);
         }

}
