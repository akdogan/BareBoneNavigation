package com.akdogan.barebonenavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var drawerLayout: DrawerLayout

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Actionbar is automatically created when we use a theme with actionbar
         * Toolbar can be created and placed, is more flexible, but i didn't get it to work
         * Both are types of App Bars
         */

        // SETUP NAVIGATION DRAWER
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_root_layout)

        // SETUP THE ACTION BAR TO USE WITH NAVIGATION
        // Get the navhostfragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Get the navController from the navhost fragment
        navController = navHostFragment.navController
        // Create an appbarconfiguration object passing in the navGraph of the navController
        //appBarConfiguration = AppBarConfiguration(navController.graph)

        //DRAWER: Create AppBar Configuration with Drawer
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // call setupWithActionBarWithNavController on the MainActivities toolbar
        //setupActionBarWithNavController(navController, appBarConfiguration)


        //DRAWER: Setup ActionBar with the drawer root layout
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        //DAWER: Setup with Navcontroller with the actual drawerView
       //val navDrawer = findViewById<NavigationView>(R.id.nav_drawer)
        //navDrawer.setupWithNavController(navController)
        //NavigationUI.setupWithNavController(navDrawer, navController)

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        setupTopLevelDrawer()
        //setupStandardDrawer()
    }

    // Because we use an appbar and not a toolbar, we need to take care of navigate up ourselves
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        // With only actionbar
        //return navController.navigateUp(appBarConfiguration) || super.onNavigateUp()
        // With Drawer Layout:
        return NavigationUI.navigateUp(navController, appBarConfiguration/*drawerLayout*/)
    }

    fun setupStandardDrawer(){
        //DRAWER: Setup ActionBar with the drawer root layout
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        //DAWER: Setup with Navcontroller with the actual drawerView
        val navDrawer = findViewById<NavigationView>(R.id.nav_drawer)
        //navDrawer.setupWithNavController(navController)
        NavigationUI.setupWithNavController(navDrawer, navController)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    fun setupTopLevelDrawer(){
        // https://github.com/isaul32/android-sunflower/blob/master/app/src/main/java/com/google/samples/apps/sunflower/GardenActivity.kt
        // DRAWER WITH MULTIPLE TOP LEVEL DESTINATIONS:
        // use Builder for AppBarConfiguration:
        val configBuilder = AppBarConfiguration.Builder(setOf(
            R.id.homeFragment,
            R.id.favouritesFragment
        )).setOpenableLayout(drawerLayout)
        appBarConfiguration = configBuilder.build()
        //setup ActionBar with NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
        val navDrawer = findViewById<NavigationView>(R.id.nav_drawer)
        navDrawer.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.options_navto_settings -> navTo(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
            R.id.options_navto_info -> navTo(HomeFragmentDirections.actionHomeFragmentToInfoFragment())
            R.id.options_navto_about -> navTo(HomeFragmentDirections.actionHomeFragmentToAboutFragment())
            else -> super.onOptionsItemSelected(item)
        }

    }

    fun navTo(dir: NavDirections): Boolean {
        findNavController(R.id.nav_host_fragment).navigate(dir)
        return true
    }


}