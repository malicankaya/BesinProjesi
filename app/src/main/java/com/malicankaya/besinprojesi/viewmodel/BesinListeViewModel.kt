package com.malicankaya.besinprojesi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malicankaya.besinprojesi.model.Besin
import com.malicankaya.besinprojesi.roomdb.BesinDatabase
import com.malicankaya.besinprojesi.service.BesinApiServis
import com.malicankaya.besinprojesi.util.OzelSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BesinListeViewModel(application: Application) : AndroidViewModel(application){

    private var besinListesi = MutableLiveData<List<Besin>>()
    private var yukleniyorMu = MutableLiveData<Boolean>()
    private var hataMesaji = MutableLiveData<Boolean>()

    private var besinApiServis = BesinApiServis()

    private var ozelSharedPreferences = OzelSharedPreferences()

    private fun besinleriInternettenAl(){

        yukleniyorMu.value = true

        viewModelScope.launch(Dispatchers.IO){
            val besinler = besinApiServis.getData()
            withContext(Dispatchers.Main){
                yukleniyorMu.value = false
                //rooma kaydet
                roomaKaydet(besinler)
                Toast.makeText(getApplication(),"Besinleri internetten aldık.",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun besinListesiniGuncelle(besinListesi: List<Besin>){
        this.besinListesi.value = besinListesi
        yukleniyorMu.value = false
        hataMesaji.value = false
    }

    private fun roomaKaydet(besinListesi: List<Besin>){
        viewModelScope.launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidList = dao.insertAllBesin(*besinListesi.toTypedArray())

            for (i in uuidList.indices){
                besinListesi[i].uuid = uuidList[i].toInt()
            }

            besinListesiniGuncelle(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }


}