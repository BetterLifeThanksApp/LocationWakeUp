package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.Manifest
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
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.DistanceSuccess
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import betterlifethanksapp.gmail.com.locationWakeUp.ui.MainActivity
import kotlinx.android.synthetic.main.current_route_fragment.*
import kotlinx.android.synthetic.main.current_route_fragment.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class CurrentRouteFragment : Fragment(){

    private fun onButtonClicked(){
        Toast.makeText(context,"KLIK",Toast.LENGTH_SHORT).show()
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
        viewModel.dialogInterfaceText.observe(viewLifecycleOwner, Observer { text->
            text?.let {
                val dialogBuilder = AlertDialog.Builder(v.context)
                    .setCancelable(false)
                    .setPositiveButton("Tak",DialogInterface.OnClickListener{
                            dialog,id -> viewModel.setAlarmClockWithLocation()
                    })
                    .setNegativeButton("NIE",DialogInterface.OnClickListener {
                            dialog, which -> Toast.makeText(context,"NEIN",Toast.LENGTH_SHORT).show()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle("Did you agree?")
                alert.setMessage(text)
                alert.show()

            }
        })
        viewModel.toastMessage.observe(viewLifecycleOwner, Observer { text->
            text?.let{
                Toast.makeText(context,text,Toast.LENGTH_LONG).show()
            }
        })


        viewModel.buttonEnabled.observe(viewLifecycleOwner, Observer { state->
            button.isClickable = state
        })

        viewModel.permissionGranted.observe(viewLifecycleOwner, Observer { state ->
            if(!state)
            {
                Log.i("Permission","invoke makeRequest in Fragment")
                viewModel.makeRequest(this)
                //makeRequest()
            }
            Log.i("Permission","end in Fragment")
        })

        viewModel.isProgressBarVisible.observe(viewLifecycleOwner, Observer { state->
            if(state)
            {
                progressBar.visibility=View.VISIBLE
            }
            else
            {
                progressBar.visibility = View.GONE
            }
        })

        v.button.setOnClickListener{viewModel.onButtonClicked(etWhere.text.toString()) }


        //TODO Maybe add alert to inform that app use location,internet and notification,so make sure that your notification sound,location and internet is on ;)
        //Set observer internet and location checked.
        //if something wrong,display appropriate message.
        //getDistenceInfo()
        return v
    }
/*
    private fun makeRequest() {
        requestPermissions()
    }
    */



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("Permission","onRequstPermissionResult")
        viewModel.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }


    fun getDistenceInfo()
    {
        //val ldh = LocationDataHelper(activity!!,"Zlota 44,Warsaw",distanceSuccess = this)
        //ldh.getDistanceInfo()
    }




}
