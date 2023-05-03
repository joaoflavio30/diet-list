package com.joaoflaviofreitas.dietplan.feature.profile

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.feature.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(), ProfileContract.ProfileFragment {

    @Inject
    lateinit var auth: FirebaseAuth

    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
//    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let { nonNullUri ->
//            firebaseImageUploader.uploadImageToFirebaseStorage(
//                nonNullUri,
//                successCallback = { uri ->
//                    Glide.with(this).load(uri).into(binding.imgProfile)
//                },
//                errorCallback = { exception ->
//                    Log.e("TAG", "Error loading image from Firebase Storage", exception)
//                },
//            )
// //            viewModel.bindUriProfileImg(uri.toString())
//        }
//    }

//    @Inject
//    lateinit var firebaseImageUploader: FirebaseImageUploader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()
        bindFields()
        Log.d("profilefragment", "email: ${auth.currentUser!!.email}")
    }

    private fun openImage() {
        val options = arrayOf("Take a Picture", "Choose from Gallery")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select an Option")
            .setItems(options) { dialog, which ->
                handleDialogOptionSelected(which)
            }
            .show()
    }

    private fun handleDialogOptionSelected(option: Int) {
        when (option) {
            0 -> {
                // Lógica para tirar uma foto
            }
            1 -> {
//                pickImage.launch("image/*")
            }
        }
    }

    override fun checkFieldForEmail(): Boolean {
        val email = binding.inputEmail.text.toString()
        var valid = false

        when {
            email.isEmpty() -> {
                binding.inputEmail.setError("Enter Email!", null)
                binding.inputEmail.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.inputEmail.setError("Enter valid email address.", null)
                binding.inputEmail.requestFocus()
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    override fun checkFieldForPassword(): Boolean {
        var valid = false
//        val password = binding.in
//
//        when{
//            password.isEmpty() -> {
//                binding.password.setError("Enter password.", null)
//                binding.password.requestFocus()
//            }
//            password.length <= 6 -> {
//                binding.password.setError("Password length must be > 6 characters.", null)
//                binding.password.requestFocus()
//            }
//        }
        return valid
    }

    override fun setOnClickListener() {
        binding.imgProfile.setOnClickListener {
            openImage()
        }
        binding.editEmailAndUsernameBtn.setOnClickListener {
            if (checkFieldForEmail()) { Log.d("chamado", "chamado")
                showDialogForAuthenticateUser()
            }
        }
    }

    private fun showDialogForAuthenticateUser() {
        // Crie um EditText para a senha
        val passwordEditText = EditText(context)
        passwordEditText.hint = "Type your password"
        passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

// Crie um AlertDialog com o EditText e os botões "OK" e "Cancelar"
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Re-authentication required")
        builder.setMessage("Please enter your password to continue.")
        builder.setView(passwordEditText)
        builder.setPositiveButton("OK") { dialog, which ->
            // Obtém a senha digitada pelo usuário
            val password = passwordEditText.text.toString()

            // Reautentica o usuário com as credenciais fornecidas
            val credential = EmailAuthProvider.getCredential(auth.currentUser!!.email!!, password)

            changeEmail(credential)
        }
        builder.setNegativeButton("Cancel", null)

// Mostra o AlertDialog
        builder.create().show()
    }

    override fun changeEmail(credential: AuthCredential) {
        viewModel.changeEmail(credential, binding.inputEmail.text.toString())
        observeEmailChangeSuccess()
    }

    override fun observeEmailChangeSuccess() {
        viewModel.changeSuccessObserver.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Success -> { showToastLengthLong("Email changed successfully") }
                is DataState.Error -> { showToastLengthLong("Error in changing email: ${result.exception}") }
                is DataState.Loading -> {}
            }
        }
    }

    override fun observePasswordChangeSuccess() {
        viewModel.changeSuccessObserver.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Success -> { showToastLengthLong("Password changed successfully") }
                is DataState.Error -> { showToastLengthLong("Error in changing password") }
                is DataState.Loading -> {}
            }
        }
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun bindFields() {
        binding.inputEmail.setText(auth.currentUser!!.email.toString())
    }
}
