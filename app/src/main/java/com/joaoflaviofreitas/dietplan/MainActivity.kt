package com.joaoflaviofreitas.dietplan

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.joaoflaviofreitas.dietplan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var myDialog: MyDialog
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNav()
        binding.fab.setOnClickListener {
            myDialog.show()
        }
    }
    private fun setupNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomMenu, navController)
        myDialog = MyDialog(this,navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomMenu()
                R.id.profileFragment -> showBottomMenu()
                else -> hideBottomMenu()
            }
        }
    }

    private fun showBottomMenu() {
        binding.bottomMenu.visibility = View.VISIBLE
    }

    private fun hideBottomMenu() {
        binding.bottomMenu.visibility = View.INVISIBLE
    }
}
