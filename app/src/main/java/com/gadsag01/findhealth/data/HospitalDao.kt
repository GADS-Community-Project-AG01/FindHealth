package com.gadsag01.findhealth.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gadsag01.findhealth.model.Hospital
import kotlinx.coroutines.flow.Flow

@Dao
interface HospitalDao {

    @Insert
    fun insertHospitals(hospital: List<Hospital>)

    @Query("SELECT * FROM Hospital")
    fun getAllHospitals(): Flow<List<Hospital>>

    @Delete
    fun deleteAllHospitals() {

    }
}