package com.example.dietplan.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dietplan.R
import com.example.dietplan.RegisterViewModel
import com.example.dietplan.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    @Inject
    lateinit var auth: FirebaseAuth
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.btnRegister.setOnClickListener { registerUser() }
        binding.login.setOnClickListener { findNavController().navigate(R.id.loginFragment) }
    }

    private fun registerUser() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        if (binding.password.text.toString() == binding.secondPassword.text.toString()) {
            viewModel.setupFirebaseAuthRegister(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("MainActivity", "createUserWithEmail:success")
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        } else {
            Toast.makeText(requireContext(), "Put the two correctly passwords", Toast.LENGTH_LONG).show()
        }
    }
}
