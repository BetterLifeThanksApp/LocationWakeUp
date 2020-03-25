package betterlifethanksapp.gmail.com.locationWakeUp.ui.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.data.unit.PreferencesOperations
import betterlifethanksapp.gmail.com.locationWakeUp.data.unit.Unit
import betterlifethanksapp.gmail.com.locationWakeUp.sharedViewModel.UnitSettingsViewModel

class SettingsFragment : PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener {

    // To prevent unintended garbage collection, I must store a strong reference to the listener.
    //That's recommend from https://developer.android.com/guide/topics/ui/settings/use-saved-values#onsharedpreferencechangelistener
    val listener:SharedPreferences.OnSharedPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener{sharedPreferences, key ->
        onSharedPreferenceChanged(sharedPreferences,key)
    }


    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val sharedViewModel:UnitSettingsViewModel by activityViewModels()
    private lateinit var viewModel: SettingsViewModel
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences,rootKey)
    }



    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key =="UNIT_SYSTEM")
        {
            val result = sharedPreferences?.getString(key,"KM")
            Log.i("valuesPreferences","$result")
            result?.let {
                val operations = PreferencesOperations()
                val unitEnumType = operations.getUnitEnumType(result)
                sharedViewModel.setcurrentUnit(unitEnumType)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


}
