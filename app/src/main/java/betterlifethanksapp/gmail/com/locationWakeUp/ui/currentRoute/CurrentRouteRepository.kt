package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper

class CurrentRouteRepository(private val ldh:LocationDataHelper) {

    fun getDistenceInfo()
    {
        ldh.getDistanceInfo()
    }
}