package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import kotlinx.android.synthetic.main.activity_main.*

class LocationDataHelper(val context: Context)

                        //later user context.applicationContext()
{
    /*
    override fun myLocaionFaliure() {
        distanceSuccess.locationFaliure()
    }
    */



    fun getDistanceInfo(text:String){




        //1.Check location permission and check is turn on/off
        //2.Notify fragment if turn off or if you don't let permisson yet.
        //3.Check internet permission and check is turn on/off
        //4.Notify fragment
        //repo.checkLocation
        //repo.checkInternet
        //repo.checkDistance



        //val locationListener: LocationListener = CurrentSingleLocationListener(activity,this)

      //  locationDataOperations.getMyLocation(locationListener) //get your current location


        //val distance = locationDataOperations.getDistance(myLocation,destination)

    }
/*
    //TODO create interface and run this method if location is correct
    override fun myLocationSuccess(myLocation:Location)
    {
        val destination = locationDataOperations.getDestinationLocation(address)
        val distance = locationDataOperations.getDistance(myLocation,destination)
        distanceSuccess.displayToast(distance)

    }
    */





}