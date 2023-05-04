package com.joaoflaviofreitas.dietplan.feature.profile

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.feature.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(), ProfileContract.ProfileFragment {

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                // A permissão foi concedida pelo usuário.
                // Faça algo aqui.
                navigateToProfileImageFragment()
            } else {
                // A permissão foi negada pelo usuário.
                // Faça algo aqui.
            }
        }

    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }

    @Inject
    lateinit var auth: FirebaseAuth

    private val viewModel: ProfileViewModel by activityViewModels()
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
        if (checkIfUserHasPermission()) navigateToProfileImageFragment()
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
        val password = binding.inputPassword.text.toString()

        when {
            password.isEmpty() -> {
                binding.inputPassword.setError("Enter password.", null)
                binding.inputPassword.requestFocus()
            }
            password.length <= 6 -> {
                binding.inputPassword.setError("Password length must be > 6 characters.", null)
                binding.inputPassword.requestFocus()
            }
            else -> valid = true
        }
        return valid
    }

    override fun setOnClickListener() {
        binding.imgProfile.setOnClickListener {
            openImage()
        }
        binding.editEmailBtn.setOnClickListener {
            if (checkFieldForEmail()) {
                changeEmailOrPasswordWithAuthenticationUser(EMAIL)
            }
        }
        binding.editPasswordBtn.setOnClickListener {
            if (checkFieldForPassword()) changeEmailOrPasswordWithAuthenticationUser(PASSWORD)
        }
    }

    override fun changeEmailOrPasswordWithAuthenticationUser(changeEmailOrPassword: String) {
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

            if (changeEmailOrPassword == EMAIL) {
                changeEmail(credential)
            }
            if (changeEmailOrPassword == PASSWORD) {
                changePassword(credential)
            }
        }
        builder.setNegativeButton("Cancel", null)

// Mostra o AlertDialog
        builder.create().show()
    }

    override fun navigateToProfileImageFragment() {
        val extras = FragmentNavigatorExtras(
            binding.imgProfile to "imageView",
        )
        findNavController().navigate(R.id.action_profileFragment_to_profileImageFragment, null, null, extras)
    }

    override fun changeEmail(credential: AuthCredential) {
        viewModel.changeEmail(credential, binding.inputEmail.text.toString())
        observeEmailChangeSuccess()
    }

    override fun changePassword(credential: AuthCredential) {
        viewModel.changePassword(credential, binding.inputPassword.text.toString())
        observePasswordChangeSuccess()
    }

    override fun observeEmailChangeSuccess() {
        viewModel.changeSuccessObserver.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Success -> { lifecycleScope.launch(Dispatchers.Main) {
                    showToastLengthLong("Email changed successfully")
                }
                }
                is DataState.Error -> { lifecycleScope.launch(Dispatchers.Main) {
                    showToastLengthLong("Error in changing email: ${result.exception}")
                } }
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

    fun checkIfUserHasPermission(): Boolean {
        return when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED -> {
                true
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) || shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                showInContextUI()
                false
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                )
                false
            }
        }
    }

    private fun showInContextUI() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.permission_needed)
            .setMessage(R.string.permission_camera_and_storage_rationale)
            .setPositiveButton(R.string.ok) { dialog, which ->
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                    ),
                )
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
                // do nothing
            }
            .create()
            .show()
    }
}
