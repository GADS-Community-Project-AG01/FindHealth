package com.gadsag01.findhealth.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gadsag01.findhealth.model.Hospital
import kotlinx.coroutines.flow.Flow

@Dao
interface HospitalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitals(hospital: List<Hospital>)

    @Update
    suspend fun updateHospital(hospital: Hospital)

    @Query("SELECT * FROM Hospital")
    fun getAllHospitals(): Flow<List<Hospital>>

    @Query("SELECT * FROM Hospital")
    suspend fun getAllHospitalsSEQ(): List<Hospital>

    @Delete
    suspend fun deleteAllHospitals(vararg hospital: Hospital)
}