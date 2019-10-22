package betterlifethanksapp.gmail.com.locationWakeUp.ui.CurrentRoute

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper

class CurrentRouteFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentRouteFragment()
    }

    private lateinit var viewModel: CurrentRouteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_route_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentRouteViewModel::class.java)
        // TODO: Use the ViewModel
        val ldh = LocationDataHelper(activity!!.applicationContext,"Zlota 44,Warsaw")
        ldh.getDistanceInfo()
    }

}
