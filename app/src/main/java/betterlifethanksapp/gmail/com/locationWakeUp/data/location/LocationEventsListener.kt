package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location

interface LocationEventsListener {

    interface OnFinishedLocationSingleOperations
    {
        fun successSingleLocation(distance:Float)
        fun failedSingleLocation()
        interface OnFinishedSingleLocationVm{
            fun successSingleLocation(distance:Float)
            fun failedSingleLocation()
        }
    }

    fun mySingleLocationSuccess(location: Location)
    fun mySingleLocationFailure()
}