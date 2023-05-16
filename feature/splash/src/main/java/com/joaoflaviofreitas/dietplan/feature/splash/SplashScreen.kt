package com.joaoflaviofreitas.dietplan.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.feature.splash.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(), SplashScreenContract.SplashScreenFragment {
    private val viewModel: SplashScreenViewModel by viewModels()

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.checkUserAuthSignedIn()
            delay(2000)
            withContext(Dispatchers.Main) {
                signInSuccessObserver()
            }
        }
    }

    override fun navigateToSignInFragment() {
        findNavController().navigate(R.id.action_splashScreen_to_loginFragment)
    }

    override fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_splashScreen_to_homeFragment)
    }

    override fun signInSuccessObserver() {
        viewModel.signInSuccess.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Success -> checkIfUserMakesDailyGoalObserver()
                is DataState.Error -> { Log.d("tag", "${result.exception}")
                    navigateToSignInFragment()
                }
                is DataState.Loading -> {}
            }
        }
    }

    override fun navigateToDailyGoalFragment() {
        findNavController().navigate(R.id.action_splashScreen_to_dailyGoalFragment)
    }

    override fun checkIfUserMakesDailyGoalObserver() {
        viewModel.checkIfUserMakesDailyGoal(auth.currentUser!!.email!!.toString())
        viewModel.checkIfUserMakesDailyGoal.observe(viewLifecycleOwner) { result ->
            if (result) {
                navigateToHomeFragment()
            } else {
                navigateToDailyGoalFragment()
            }
        }
    }
}
