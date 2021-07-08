package com.kolis.test_catalog_app.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.kolis.test_catalog_app.MainActivity
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.databinding.FragmentLoginBinding
import com.kolis.test_catalog_app.extensions.observeOnce
import com.kolis.test_catalog_app.util.PrefConstants

class LoginFragment : Fragment() {

    lateinit var viewModel: LoginViewModel

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.signInButton.setOnClickListener {
            binding.loginText.text?.let { login ->
                binding.passwordText.text?.let { password ->
                    logIn(login.toString(), password.toString())
                }
            }
        }

        binding.restorePassword.setOnClickListener {
            Toast.makeText(
                requireContext(),
                requireContext().resources.getString(R.string.work_in_progress),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun logIn(login: String, password: String) {
        viewModel.signIn(login, password)
            .observeOnce(viewLifecycleOwner, Observer { loginResult ->
                if (loginResult.success) {
                    startMainActivity(login, password)
                } else {
                    Toast.makeText(requireContext(), loginResult.errorMessage, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun startMainActivity(login: String, password: String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        pref.edit().putBoolean(PrefConstants.IS_LOGGED_PREF, true).apply()
        pref.edit().putString(PrefConstants.USER_NAME_PREF, login).apply()
        pref.edit().putString(PrefConstants.USER_PASSWORD_PREF, password).apply()
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}