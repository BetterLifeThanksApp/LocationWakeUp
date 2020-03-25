package betterlifethanksapp.gmail.com.locationWakeUp.ui.routes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationListAdapter
import betterlifethanksapp.gmail.com.locationWakeUp.sharedViewModel.DestinationLocationViewModel
import kotlinx.android.synthetic.main.routes_fragment.*

class RoutesFragment : Fragment() {

    companion object {
        fun newInstance() = RoutesFragment()
    }

    private lateinit var viewModel: RoutesViewModel
    private val sharedModel: DestinationLocationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(RoutesViewModel::class.java)
        sharedModel.destinationLocation.observe(viewLifecycleOwner,Observer<String>{
                destinationLocation->
            tvCurrentDestinationInfo.text = destinationLocation
        })

        val rootView = inflater.inflate(R.layout.routes_fragment, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.rvLocations)
        val adapter = LocationListAdapter(context!!.applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.allLocation.observe(viewLifecycleOwner, Observer { locations->
            locations?.let { adapter.setLocations(it) }
        })

        return rootView
    }


}
