package betterlifethanksapp.gmail.com.locationWakeUp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class Location(@PrimaryKey @ColumnInfo(name="locationName") val location:String)