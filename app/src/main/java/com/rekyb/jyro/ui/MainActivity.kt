package com.rekyb.jyro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rekyb.jyro.R
import com.rekyb.jyro.databinding.ActivityMainBinding
import com.rekyb.jyro.utils.gone
import com.rekyb.jyro.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding?.root)

        val bottomNavView = binding?.bottomNavView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.profile_fragment -> removeBottomNavView()
                else -> showBottomNavView()
            }
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.item_discover_fragment,
                R.id.item_favourites_fragment,
                R.id.item_more_fragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun onDestroy() {
        super.onDestroy()

        binding?.unbind()
        binding = null
    }

    private fun removeBottomNavView() = binding?.bottomNavView?.gone()
    private fun showBottomNavView() = binding?.bottomNavView?.show()
}
