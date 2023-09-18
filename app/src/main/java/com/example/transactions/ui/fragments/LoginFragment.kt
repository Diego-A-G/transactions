package com.example.transactions.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.transactions.Constants
import com.example.transactions.Constants.IS_LOGGED
import com.example.transactions.R
import com.example.transactions.databinding.FragmentLoginBinding
import com.example.transactions.encrypter.RSAEncrypter
import com.example.transactions.isShow
import com.example.transactions.ui.IStepListener
import com.example.transactions.ui.managers.LoginViewModel
import com.example.transactions.ui.managers.LoginViewModelFactory
import com.example.transactions.ui.managers.ProcessStatus

class LoginFragment : Fragment() {

    private lateinit var authStepListener: IStepListener
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private val sp by lazy {
        requireContext().getSharedPreferences(
            Constants.SHARED_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIsLogged()
        initViewModel()
        setListeners()
        setObservers()
    }

    private fun checkIsLogged() {
        if (sp.getBoolean(IS_LOGGED, false)) authStepListener.onLoginCompleted()
    }

    private fun setObservers() {
        viewModel.getProcessStatus().observe(viewLifecycleOwner) {
            when (it) {
                is ProcessStatus.Error -> {
                    errorLogin(it.message)
                }

                is ProcessStatus.Success -> {
                    sp.edit().putBoolean(IS_LOGGED, it.data).apply()
                }

                is ProcessStatus.Loading -> {
                    showButton(false)
                }
            }
        }
        viewModel.isLogged().observe(viewLifecycleOwner) {
            if (it) authStepListener.onLoginCompleted()
        }
    }

    private fun setListeners() {
        binding.loginButton.setOnClickListener { validateUserAndPass() }
    }

    private fun validateUserAndPass() {
        val user = binding.userLoginEditText.text.toString()
        val pass = binding.passwordLoginEditText.text.toString()
        if (pass.isEmpty() || user.isEmpty()) {
            errorLogin(getString(R.string.error_empty_fields))
            showButton(true)
        } else {
            showButton(false)
            val encryptedPassword = RSAEncrypter()
                .getEncryptedString(
                    requireContext(),
                    pass
                )?.replace("\n", "") ?: ""
            viewModel.validateLogin(user, encryptedPassword)
        }
    }

    private fun errorLogin(error: String) {
        binding.loginErrorTextView.text = error
        binding.loginErrorTextView.visibility = View.VISIBLE
    }

    private fun showButton(isShow: Boolean) {
        binding.loginProgress.isShow(!isShow)
        binding.loginButton.isShow(isShow)
    }

    private fun initViewModel() {
        val factory = LoginViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    private fun setAuthListener(authStepListener: IStepListener) {
        this.authStepListener = authStepListener
    }

    companion object {

        fun newInstance(authStepListener: IStepListener): LoginFragment {
            return LoginFragment().apply {
                setAuthListener(authStepListener)
            }
        }
    }
}