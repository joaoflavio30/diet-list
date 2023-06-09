package com.joaoflaviofreitas.dietplan.ui.authentication.signin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.DataState
import com.joaoflaviofreitas.dietplan.component.authentication.domain.model.UserAuth
import com.joaoflaviofreitas.dietplan.ui.authentication.R
import com.joaoflaviofreitas.dietplan.ui.authentication.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(), SignInContracts.SignInFragment {

    private lateinit var googleSingInClient: GoogleSignInClient
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("tag", "$result")
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            handleSignInResult(task)
        } catch (e: Exception) {
            Log.e("Error", e.message ?: "Error in sign in with Google")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        if (auth.currentUser != null) {
            checkIfUserMakesDailyGoalObserver()
        }
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindRememberedEmail()
        initGoogleSignInClient()
        setOnClickListener()
        signInSuccessObserver()
        progressBarSignInObserver()
    }
    override fun checkFields(): Boolean {
        val email = binding.loginField.text.toString()
        val password = binding.password.text.toString()
        var valid = false

        when {
            email.isEmpty() -> {
                binding.loginField.setError("Enter Email!", null)
                binding.loginField.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.loginField.setError("Enter valid email address.", null)
                binding.loginField.requestFocus()
            }
            password.isEmpty() -> {
                binding.password.setError("Enter password.", null)
                binding.password.requestFocus()
            }
            password.length <= 6 -> {
                binding.password.setError("Password length must be > 6 characters.", null)
                binding.password.requestFocus()
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    override fun setOnClickListener() {
        binding.signInButton.setOnClickListener {
            val signInIntent = googleSingInClient.signInIntent
            launcher.launch(signInIntent)
        }

        binding.btnLogin.setOnClickListener {
            if (checkFields()) {
                val userAuth = UserAuth(binding.loginField.text.toString(), binding.password.text.toString())
                viewModel.signIn(userAuth)
                switchButtonsAsNotClickable()
            }
        }

        binding.forgotPassword.setOnClickListener {
            navigateToRestorePasswordFragment()
        }

        binding.register.setOnClickListener {
            navigateToSignUp()
        }
    }

    override fun signInSuccessObserver() {
        viewModel.signInSuccess.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Success -> {
                    Log.d("teste", "signInSucess chamado")
                    showToastLengthLong("Login success!")
                    checkUserWantsRememberPassword()
                    checkIfUserMakesDailyGoalObserver()
                    requireActivity().supportFragmentManager.popBackStack(R.id.loginFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
                is DataState.Error -> {
                    showToastLengthLong("Failed to login")
                    Log.d("erro", "E:${result.exception}")
                    switchButtonsAsClickable()
                }
                is DataState.Loading -> {}
            }
        }
    }

    override fun showProgressBarSignIn() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.INVISIBLE
        binding.signInButton.visibility = View.INVISIBLE
    }

    override fun hideProgressBarSignIn() {
        binding.progressCircular.visibility = View.INVISIBLE
        binding.btnLogin.visibility = View.VISIBLE
        binding.signInButton.visibility = View.VISIBLE
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG)
            .show()
    }

    override fun navigateToPopBackStack() {
        findNavController().popBackStack()
    }

    override fun navigateToSignUp() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            Log.d("chamado2", "teste")
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogle(credential)
            switchButtonsAsNotClickable()
        } catch (e: ApiException) {
            // Trate a exceção de acordo com suas necessidades
            Log.e("chamado", "Erro ao fazer login com o Google: ${e.message}")
        }
    }

    override fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSingInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun progressBarSignInObserver() {
        viewModel.progressBarSignIn.observe(viewLifecycleOwner) { result ->
            if (result) {
                showProgressBarSignIn()
            } else {
                hideProgressBarSignIn()
            }
        }
    }

    override fun navigateToDailyGoalFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_dailyGoalFragment)
    }

    override fun navigateToRestorePasswordFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_restorePasswordFragment)
    }

    override fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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

    override fun rememberEmailInMemory() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefsOfEmail", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("userEmail", auth.currentUser!!.email)
            putBoolean("checkbox", true)
            apply()
        }
    }

    override fun checkUserWantsRememberPassword() {
        if (binding.checkbox.isChecked) {
            rememberEmailInMemory()
        } else {
            requireContext().getSharedPreferences("MyPrefsOfEmail", Context.MODE_PRIVATE).edit().clear().apply()
        }
    }

    override fun bindRememberedEmail() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefsOfEmail", Context.MODE_PRIVATE)
        binding.loginField.setText(sharedPreferences.getString("userEmail", ""))
        binding.checkbox.isChecked = sharedPreferences.getBoolean("checkbox", false)
    }

    override fun switchButtonsAsClickable() {
        binding.signInButton.isClickable = true
        binding.btnLogin.isClickable = true
        binding.register.isClickable = true
        binding.forgotPassword.isClickable = true
    }

    override fun switchButtonsAsNotClickable() {
        binding.signInButton.isClickable = false
        binding.btnLogin.isClickable = false
        binding.register.isClickable = false
        binding.forgotPassword.isClickable = false
    }
}
