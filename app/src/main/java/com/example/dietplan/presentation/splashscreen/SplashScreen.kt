package com.example.dietplan.presentation.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dietplan.DataState
import com.example.dietplan.R
import com.example.dietplan.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(), SplashScreenContract.SplashScreenFragment {
    private val viewModel: SplashScreenViewModel by viewModels()

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

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
                is DataState.Success -> navigateToDailyGoalFragment()
                is DataState.Error -> navigateToSignInFragment()
                is DataState.Loading -> navigateToSignInFragment()
            }
        }
    }

    override fun navigateToDailyGoalFragment() {
        findNavController().navigate(R.id.action_splashScreen_to_dailyGoalFragment)
    }
}
