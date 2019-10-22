package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location

interface LocationEventsListener {
    fun getCurrentLocation(location: Location?)
}