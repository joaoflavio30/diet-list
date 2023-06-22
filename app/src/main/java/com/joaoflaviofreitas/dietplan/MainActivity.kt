package com.joaoflaviofreitas.dietplan

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.databinding.ActivityMainBinding
import com.joaoflaviofreitas.dietplan.ui.authentication.signin.LoginViewModel
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

    private val authViewModel: LoginViewModel by viewModels()

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
                    if (navController.currentDestination?.id == R.id.profileFragment) {
                        navController.navigate(R.id.action_profileFragment_to_homeFragment)
                    }
                    true
                }
                "Profile" -> {
                    if (navController.currentDestination?.id == R.id.homeFragment) {
                        navController.navigate(R.id.action_homeFragment_to_profileFragment)
                    }
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
            authViewModel.checkIfUserMakesDailyGoal(auth.currentUser!!.email!!)
            authViewModel.checkIfUserMakesDailyGoal.observe(this) { result ->
                if (result) {
                    navGraph.setStartDestination(R.id.homeFragment)
                } else {
                    navGraph.setStartDestination(R.id.dailyGoalFragment)
                }
                navController.setGraph(navGraph, null)
            }
        } else {
            navGraph.setStartDestination(R.id.loginFragment)
            navController.setGraph(navGraph, null)
        }
    }

    private fun showBottomMenu() {
        binding.bottomMenu.visibility = View.VISIBLE
    }

    private fun hideBottomMenu() {
        binding.bottomMenu.visibility = View.INVISIBLE
    }
}
