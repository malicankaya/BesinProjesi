package com.malicankaya.besinprojesi.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.malicankaya.besinprojesi.model.Besin

@Dao
interface BesinDAO {

    @Insert
    suspend fun insertAllBesin(vararg besin : Besin) : List<Long>
    @Query("SELECT * FROM besin")
    suspend fun getAllBesin() : List<Besin>
    @Query("DELETE FROM besin")
    suspend fun deleteAllBesin()
    @Query("SELECT * FROM BESIN WHERE uuid = :id")
    suspend fun getBesin(id : Int) : Besin
}