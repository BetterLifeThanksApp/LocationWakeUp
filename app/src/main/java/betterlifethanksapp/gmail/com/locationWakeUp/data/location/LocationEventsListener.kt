package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location

interface LocationEventsListener {

    interface OnFinishedAllOperations
    {
        fun successLocation(distance:Float)
        fun faliedLocation()
    }

    fun myLocationSuccess(location: Location)
    fun myLocaionFaliure()
}