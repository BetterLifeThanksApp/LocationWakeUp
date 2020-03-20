package betterlifethanksapp.gmail.com.locationWakeUp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Location::class],version = 1,exportSchema = false)
abstract class LocationRoomDatabase:RoomDatabase() {

    abstract fun locationDao():LocationDao

    companion object{

        @Volatile
        private var INSTANCE: LocationRoomDatabase?=null

        fun getDatabase(context: Context): LocationRoomDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    LocationRoomDatabase::class.java,
                    "location_database"
                    ).build()
                INSTANCE = instance
                return instance

            }
        }
    }
}