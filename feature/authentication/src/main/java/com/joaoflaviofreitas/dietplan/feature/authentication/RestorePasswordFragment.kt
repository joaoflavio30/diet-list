package com.joaoflaviofreitas.dietplan.feature.authentication

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.feature.authentication.databinding.FragmentRestorePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestorePasswordFragment : Fragment(), RestoreContract.RestorePasswordFragment {

    private val viewModel: RestorePasswordViewModel by viewModels()
    private var _binding: FragmentRestorePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRestorePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()
        progressBarRestoreObserver()
        restoreSuccessObserver()
    }

    override fun setOnClickListener() {
        binding.back.setOnClickListener { navigateToPopBackStack() }

        binding.btnRestore.setOnClickListener {
            if (validEmail()) {
                viewModel.changePasswordByEmail(binding.emailField.text.toString())
            } else {
                showToastLengthLong("Put the Email!")
            }
        }
    }

    override fun navigateToPopBackStack() {
        findNavController().popBackStack()
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }

    override fun validEmail(): Boolean {
        val email = binding.emailField.text.toString()
        var valid = false

        when {
            email.isEmpty() -> {
                binding.emailField.setError("Enter email address.", null)
                binding.emailField.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailField.setError("Enter valid email address.", null)
                binding.emailField.requestFocus()
            }
            else -> {
                valid = true
            }
        }

        return valid
    }

    override fun showProgressBarRestore() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.btnRestore.visibility = View.INVISIBLE
        binding.back.visibility = View.INVISIBLE
    }

    override fun hideProgressBarRestore() {
        binding.progressCircular.visibility = View.INVISIBLE
        binding.btnRestore.visibility = View.VISIBLE
        binding.back.visibility = View.VISIBLE
    }

    override fun restoreSuccessObserver() {
        viewModel.restoreSuccess.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Success -> { showToastLengthLong(text = "Check your email!")
                    navigateToPopBackStack() }
                is DataState.Error -> showToastLengthLong(text = "failed: ${result.exception}")
                is DataState.Loading -> {}
            }
        }
    }

    override fun progressBarRestoreObserver() {
        viewModel.progressBarRestore.observe(viewLifecycleOwner) { result ->
            if (result) {
                showProgressBarRestore()
            } else {
                hideProgressBarRestore()
            }
        }
    }
}
