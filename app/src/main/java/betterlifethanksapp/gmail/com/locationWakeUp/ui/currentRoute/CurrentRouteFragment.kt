package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.Toast

import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.DistanceSuccess
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import java.math.RoundingMode
import java.text.DecimalFormat

class CurrentRouteFragment : Fragment(),DistanceSuccess {
    override fun locationFaliure() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
        Toast.makeText(context,"Włącz lokalizację i internet",Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() =
            CurrentRouteFragment()
    }

    private lateinit var viewModel: CurrentRouteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CurrentRouteViewModel::class.java)
        // TODO: Use the ViewModel
        getDistenceInfo()
        return inflater.inflate(R.layout.current_route_fragment, container, false)
    }


    fun getDistenceInfo()
    {
        val ldh = LocationDataHelper(activity!!,"Zlota 44,Warsaw",distanceSuccess = this)
        ldh.getDistanceInfo()
    }

    override fun displayToast(locationdistance:Float)
    {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
       Toast.makeText(context,"Odległość w linii prostej to: ${df.format(locationdistance)} km ?",Toast.LENGTH_LONG).show()
    }



}
