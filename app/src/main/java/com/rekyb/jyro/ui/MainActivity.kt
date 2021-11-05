package com.rekyb.jyro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rekyb.jyro.R
import com.rekyb.jyro.common.Themes
import com.rekyb.jyro.databinding.ActivityMainBinding
import com.rekyb.jyro.domain.use_case.data_store.GetThemeUseCase
import com.rekyb.jyro.utils.ThemeChanger
import com.rekyb.jyro.utils.gone
import com.rekyb.jyro.utils.show
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var appBarConfiguration: AppBarConfiguration? = null
    private var navController: NavController? = null
    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var getThemeUseCase: GetThemeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding?.root)

        val bottomNavView = binding?.bottomNavView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        navController = navHostFragment.navController
        navController!!.addOnDestinationChangedListener { _, destination, _ ->
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

        setupActionBarWithNavController(navController!!, appBarConfiguration!!)
        bottomNavView?.setupWithNavController(navController!!)

        appThemeController()
    }

    override fun onSupportNavigateUp(): Boolean =
        appBarConfiguration?.let { navController!!.navigateUp(it) }!! || super.onSupportNavigateUp()

    override fun onDestroy() {
        super.onDestroy()

        binding?.unbind()
        binding = null
        appBarConfiguration = null
        navController = null
    }

    private fun appThemeController() {
        lifecycleScope.launchWhenCreated {
            val themeSelection = getThemeUseCase()
            val themeChanger = ThemeChanger()

            themeSelection?.let { theme ->
                themeChanger.changeBy(theme)
            }

            Timber.d("Theme changed to $themeSelection")
        }
    }

    private fun removeBottomNavView() = binding?.bottomNavView?.gone()
    private fun showBottomNavView() = binding?.bottomNavView?.show()
}
