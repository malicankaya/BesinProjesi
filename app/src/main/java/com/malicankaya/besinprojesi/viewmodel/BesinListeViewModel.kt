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

    var besinListesi = MutableLiveData<List<Besin>>()
    var yukleniyorMu = MutableLiveData<Boolean>()
    var hataMesaji = MutableLiveData<Boolean>()

    private var besinApiServis = BesinApiServis()

    private var ozelSharedPreferences = OzelSharedPreferences(getApplication())

    private val guncellenmeZamani = 1 * 60 * 1000 * 1000 * 1000L

    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()

        if(kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellenmeZamani) {
            besinleriRoomdanAl()
        }else{
            besinleriInternettenAl()
        }
    }

    fun refrestDataFromInternet(){
        besinleriInternettenAl()
    }

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

    private fun besinleriRoomdanAl() {
        yukleniyorMu.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val dao = BesinDatabase(getApplication()).besinDao()
            val besinListesi= dao.getAllBesin()
            withContext(Dispatchers.Main){
                besinListesiniGuncelle(besinListesi)
                yukleniyorMu.value = false
                Toast.makeText(getApplication(),"Besinleri roomdan aldık",Toast.LENGTH_LONG).show()
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
        }
        besinListesiniGuncelle(besinListesi)
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }


}