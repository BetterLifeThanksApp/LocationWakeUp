package betterlifethanksapp.gmail.com.locationWakeUp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import betterlifethanksapp.gmail.com.locationWakeUp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        bottom_nav.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.currentRouteFragment->{
                    navController.popBackStack(R.id.currentRouteFragment,false)
                }
                R.id.routesFragment->{
                    navController.navigate(R.id.action_global_routesFragment)
                }
                R.id.settingsFragment->{
                    navController.navigate(R.id.action_global_settingsFragment)
                }

            }
            return@setOnNavigationItemSelectedListener true

        }

        NavigationUI.setupActionBarWithNavController(
            this,
            navController
        )

    }


    override fun onSupportNavigateUp(): Boolean {
        navController.popBackStack(R.id.currentRouteFragment,false)
        return true//return NavigationUI.navigateUp(navController,null)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navController.popBackStack(R.id.currentRouteFragment,false)
    }


}
