package com.joaoflaviofreitas.dietplan.ui.authentication.signout

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth
import com.joaoflaviofreitas.dietplan.ui.authentication.R
import com.joaoflaviofreitas.dietplan.ui.authentication.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment(), SignUpContract.SignUpFragment {

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
        setOnClickListener()
        progressBarSignUpObserver()
        signUpSuccessObserver()
    }

    override fun checkFields(): Boolean {
        val username = binding.username.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.secondPassword.text.toString()
        var valid = false

        when {
            username.isEmpty() -> {
                binding.username.setError("Enter username!", null)
                binding.username.requestFocus()
            }
            email.isEmpty() -> {
                binding.email.setError("Enter email address.", null)
                binding.email.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.email.setError("Enter valid email address.", null)
                binding.email.requestFocus()
            }
            password.isEmpty() -> {
                binding.password.setError("Enter password.", null)
                binding.password.requestFocus()
            }
            password.length <= 6 -> {
                binding.password.setError("Password length must be > 6 characters.", null)
                binding.password.requestFocus()
            }
            confirmPassword != password -> {
                binding.secondPassword.setError("Passwords must match.", null)
                binding.secondPassword.requestFocus()
            }
            else -> {
                valid = true
            }
        }

        return valid
    }

    override fun setOnClickListener() {
        binding.btnRegister.setOnClickListener {
            if (checkFields()) {
                val userAuth = UserAuth(binding.email.text.toString(), binding.password.text.toString())
                viewModel.signUp(userAuth)
                signUpSuccessObserver()
            }
        }
        binding.login.setOnClickListener { navigateToSignIn() }
    }

    override fun progressBarSignUpObserver() {
        viewModel.progressBarSignUp.observe(viewLifecycleOwner) { result ->
            if (result) {
                showProgressBarSignUp()
            } else {
                hideProgressBarSignUp()
            }
        }
    }

    override fun signUpSuccessObserver() {
        viewModel.signUpSuccess.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Success -> { showToastLengthLong("Sign up success!") }
                is DataState.Loading -> {}
                is DataState.Error -> { showToastLengthLong("User register false: ${result.exception}") }
            }
        }
    }

    override fun showProgressBarSignUp() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.btnRegister.visibility = View.INVISIBLE
    }

    override fun hideProgressBarSignUp() {
        binding.progressCircular.visibility = View.INVISIBLE
        binding.btnRegister.visibility = View.VISIBLE
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }

    override fun navigateToPopBackStack() {
        findNavController().popBackStack()
    }

    override fun navigateToSignIn() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}
