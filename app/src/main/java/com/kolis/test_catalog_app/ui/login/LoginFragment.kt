package com.kolis.test_catalog_app.ui.login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import com.kolis.test_catalog_app.Constants
import com.kolis.test_catalog_app.MainActivity
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.databinding.InfoFragmentBinding
import com.kolis.test_catalog_app.extensions.observeOnce

class LoginFragment : Fragment() {

    lateinit var viewModel: LoginViewModel

    private var _binding: InfoFragmentBinding? = null
    private val binding get() = _binding!!

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(requireContext())

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = InfoFragmentBinding.inflate(inflater)
        switchVisibilityForLastFragment()
        setupListeners()
        return binding.root
    }

    private fun switchVisibilityForLastFragment() {
        binding.textInfo.visibility = View.GONE
        binding.signInRegister.visibility = View.VISIBLE
    }

    private fun setupListeners() {
        binding.signInRegister.setOnClickListener {
            googleLogIn()
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun googleLogIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult?) {
        val response = result?.idpResponse
        if (response == null) {
            Toast.makeText(requireContext(), "Registration was aborted by user", Toast.LENGTH_LONG).show()
        }

        when (result?.resultCode) {
            Activity.RESULT_OK -> {
                Toast.makeText(requireContext(), "Wow! successful registration!", Toast.LENGTH_LONG).show()
                var password = Constants.NO_PASSWORD_STRING
                val login = when (result.idpResponse?.providerType) {
                    PhoneAuthProvider.PROVIDER_ID -> {
                        result.idpResponse?.phoneNumber ?: throw Exception("Phone authorization without phone number")
                    }
                    EmailAuthProvider.PROVIDER_ID -> {
                        //TODO тут можно подтянуть фотку и имя/фамилию
                        result.idpResponse?.email ?: throw Exception("email authorization without email address")
                    }
                    GoogleAuthProvider.PROVIDER_ID -> {
                        //TODO тут можно подтянуть фотку и имя/фамилию
                        result.idpResponse?.email ?: throw Exception("email authorization without email address")
                    }
                    else -> throw Exception("Unsupported authorization way: ${result.idpResponse?.providerType}.")
                }
                viewModel.saveLoginPassword(login, password, preferences)
                startMainActivity()
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(requireContext(), "Registration canceled..", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Strange result code: ${result?.resultCode}", Toast.LENGTH_LONG).show()
            }
        }
    }
}