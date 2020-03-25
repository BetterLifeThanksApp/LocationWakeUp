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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.DistanceSuccess
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import betterlifethanksapp.gmail.com.locationWakeUp.sharedViewModel.DestinationLocationViewModel
import betterlifethanksapp.gmail.com.locationWakeUp.sharedViewModel.UnitSettingsViewModel
import betterlifethanksapp.gmail.com.locationWakeUp.ui.MainActivity
import kotlinx.android.synthetic.main.current_route_fragment.*
import kotlinx.android.synthetic.main.current_route_fragment.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class CurrentRouteFragment : Fragment(){



    companion object {
        fun newInstance() =
            CurrentRouteFragment()
    }

    private lateinit var viewModel: CurrentRouteViewModel
    private val sharedViewModel:DestinationLocationViewModel by activityViewModels()
    private val sharedUnitViewModel:UnitSettingsViewModel by activityViewModels()


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
                            dialog,id ->
                        sharedViewModel.setDestination(etWhere.text.toString())
                        viewModel.setAlarmClockWithLocation(etWhere.text.toString())
                    })
                    .setNegativeButton("NIE",DialogInterface.OnClickListener {
                            dialog, which -> Toast.makeText(context,"NEIN",Toast.LENGTH_SHORT).show()
                            viewModel.enableButtonDisableProgressBar()
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
        viewModel.buttonClick.observe(viewLifecycleOwner, Observer { clicked->
            if(clicked)
            {
                clickButton()
            }
        })

        v.button.setOnClickListener{clickButton() }


        viewModel.textView.observe(viewLifecycleOwner, Observer { text->
            tvInfo.text = text
        })

        viewModel.wakeUpMode.observe(viewLifecycleOwner, Observer { wakeUpModeOn->
            if(wakeUpModeOn)
            {
                replaceViewToWaitingTimeView()
            }
            else
            {
                sharedViewModel.setDestination("no destination")
                backToStandardView()
            }
        })

        sharedUnitViewModel.currentUnit.observe(viewLifecycleOwner, Observer { unit->
            viewModel.onUnitChanged(unit)
        })

        //TODO Maybe add alert to inform that app use location,internet and notification,so make sure that your notification sound,location and internet is on ;)
        //Set observer internet and location checked.
        //if something wrong,display appropriate message.
        //getDistenceInfo()
        return v
    }

    private fun clickButton(){
        viewModel.onButtonClicked(etWhere.text.toString())
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("Permission","onRequstPermissionResult")
        viewModel.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }


    override fun onDestroyView() {
        viewModel.onDestroyView()
        super.onDestroyView()
    }


    private fun replaceViewToWaitingTimeView(){
        tvWhere.visibility = View.INVISIBLE
        etWhere.visibility = View.INVISIBLE
        button.visibility = View.INVISIBLE
    }

    private fun backToStandardView(){
        tvWhere.visibility = View.VISIBLE
        etWhere.visibility = View.VISIBLE
        button.visibility = View.VISIBLE
    }


}
