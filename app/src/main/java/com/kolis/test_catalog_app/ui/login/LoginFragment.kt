package com.kolis.test_catalog_app.ui.login

import android.content.Intent
import android.content.SharedPreferences
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

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(requireContext())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater)
        checkRememberMe()
        setupListeners()
        return binding.root
    }

    private fun checkRememberMe() {
        val isRemember = preferences.getBoolean(PrefConstants.IS_REMEMBER_ME_PREF, false)
        binding.rememberMeCheckbox.isChecked = isRemember
        if (isRemember) {
            binding.loginText.setText(preferences.getString(PrefConstants.USER_NAME_PREF, ""))
        } else {
            binding.loginText.setText("")
        }
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

        binding.rememberMeCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setRememberMe(isChecked, preferences)
        }
    }

    private fun logIn(login: String, password: String) {
        viewModel.signIn(login, password)
            .observeOnce(viewLifecycleOwner, Observer { loginResult ->
                if (loginResult.success) {
                    viewModel.saveLoginPassword(login, password, preferences)
                    startMainActivity()
                } else {
                    Toast.makeText(requireContext(), loginResult.errorMessage, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun startMainActivity() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}