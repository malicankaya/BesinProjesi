package com.malicankaya.besinprojesi.service

import com.malicankaya.besinprojesi.model.Besin
import retrofit2.http.GET

interface BesinAPI {

    //BASEURL   ->  https://raw.githubusercontent.com/
    //ENDPOINT  ->  atilsamancioglu/BTK20-JSONVeriSeti/9b55086ad4096dbee7bc2b506d4787fbb805b0ca/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/9b55086ad4096dbee7bc2b506d4787fbb805b0ca/besinler.json")
    suspend fun getBesin(): List<Besin>
}