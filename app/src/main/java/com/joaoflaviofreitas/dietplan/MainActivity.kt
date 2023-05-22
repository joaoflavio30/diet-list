package com.joaoflaviofreitas.dietplan

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var myDialog: MyDialog
    lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNav()
        binding.fab.setOnClickListener {
            myDialog.show()
        }
        setupOnItemBottomMenuListener()
    }
    private fun setupOnItemBottomMenuListener() {
        navController = navHostFragment.navController
        binding.bottomMenu.setOnItemSelectedListener { menu ->
            when (menu.title) {
                "Home" -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                "Profile" -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupNav() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        NavigationUI.setupWithNavController(binding.bottomMenu, navController)
        myDialog = MyDialog(this, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomMenu()
                R.id.profileFragment -> showBottomMenu()
                else -> hideBottomMenu()
            }
        }
        if (auth.currentUser != null) {
            navGraph.setStartDestination(R.id.homeFragment)
        } else {
            navGraph.setStartDestination(R.id.loginFragment)
        }
        navController.setGraph(navGraph, null)
    }

    private fun showBottomMenu() {
        binding.bottomMenu.visibility = View.VISIBLE
    }

    private fun hideBottomMenu() {
        binding.bottomMenu.visibility = View.INVISIBLE
    }
}
