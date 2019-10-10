package betterlifethanksapp.gmail.com.locationWakeUp.ui.Routes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import betterlifethanksapp.gmail.com.locationWakeUp.R

class RoutesFragment : Fragment() {

    companion object {
        fun newInstance() = RoutesFragment()
    }

    private lateinit var viewModel: RoutesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.routes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RoutesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
