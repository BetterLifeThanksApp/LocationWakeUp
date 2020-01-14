package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.DistanceSuccess
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import kotlinx.android.synthetic.main.current_route_fragment.*
import kotlinx.android.synthetic.main.current_route_fragment.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class CurrentRouteFragment : Fragment(),DistanceSuccess{

    private fun onButtonClicked(){
        Toast.makeText(context,"KLIK",Toast.LENGTH_SHORT).show()
    }

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
        val v = inflater.inflate(R.layout.current_route_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(CurrentRouteViewModel::class.java)
        // TODO: Use the ViewModel
        v.button.setOnClickListener{viewModel.onButtonClicked() }

        viewModel.dialogInterfaceText.observe(viewLifecycleOwner, Observer { text->
            text?.let {
                val dialogBuilder = AlertDialog.Builder(v.context)
                    .setCancelable(false)
                    .setPositiveButton("Tak",DialogInterface.OnClickListener{
                        dialog,id -> Toast.makeText(context,"YEA",Toast.LENGTH_SHORT).show()
                    })
                    .setNegativeButton("NIE",DialogInterface.OnClickListener {
                            dialog, which -> Toast.makeText(context,"NEIN",Toast.LENGTH_SHORT).show()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle("Cz ustawić budzik")
                alert.show()

            }
        })


        getDistenceInfo()
        return v
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
