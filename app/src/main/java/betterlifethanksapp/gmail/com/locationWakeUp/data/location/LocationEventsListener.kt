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
        fun successMultipleLocation(distance:Float)
        fun failedMultipleLocation()

    }

    fun mySingleLocationSuccess(location: Location)
    fun mySingleLocationFailure()

    fun multipleLocationSuccess(location: Location)
    fun multipleLocationFailure()
}