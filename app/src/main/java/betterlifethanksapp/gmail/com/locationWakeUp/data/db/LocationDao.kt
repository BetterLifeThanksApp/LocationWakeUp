package betterlifethanksapp.gmail.com.locationWakeUp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocationDao {

    @Query("SELECT * from location_table WHERE locationName=:locationN LIMIT 1")
    fun getLocation(locationN:String):Location

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(location:Location)

    @Delete
    suspend fun deleteLocation(location:Location)

    @Query("SELECT * FROM location_table")
    fun getAllLocations(): LiveData<List<Location>>
}