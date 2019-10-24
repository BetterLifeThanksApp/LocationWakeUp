package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location

interface LocationEventsListener {
    fun myLocationSuccess(location: Location)
}