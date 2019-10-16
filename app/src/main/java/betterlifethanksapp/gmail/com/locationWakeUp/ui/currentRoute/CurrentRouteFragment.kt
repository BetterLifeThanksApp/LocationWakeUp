package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.databinding.CurrentRouteFragmentBinding

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
        val binding:CurrentRouteFragmentBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.current_route_fragment,
                container,
                false)
        viewModel = ViewModelProviders.of(this).get(CurrentRouteViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }



}
