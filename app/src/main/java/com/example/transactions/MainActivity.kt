package com.example.transactions

import AuthTransactionFragment
import SearchTransactionFragment
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.transactions.databinding.ActivityMainBinding
import com.example.transactions.ui.IStepListener
import com.example.transactions.ui.fragments.ConfigurationFragment
import com.example.transactions.ui.fragments.DetailTransactionFragment
import com.example.transactions.ui.fragments.LoginFragment
import com.example.transactions.ui.fragments.MenuFragment
import com.example.transactions.ui.fragments.TransactionsFragment
import com.example.transactions.ui.managers.LaunchViewModel
import com.example.transactions.ui.managers.LaunchViewModelFactory

class MainActivity : AppCompatActivity(), IStepListener {

    private lateinit var viewModel: LaunchViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setVideo()
        initViewModel()
        setObservers()
    }

    private fun setObservers() {
        viewModel.launchStatus().observe(this) { step ->
            val fragment: Fragment? = when (step) {
                LaunchViewModel.ELaunchStatus.OPENING -> {
                    ConfigurationFragment.newInstance(this)
                }

                LaunchViewModel.ELaunchStatus.LOGIN -> {
                    LoginFragment.newInstance(this)
                }

                LaunchViewModel.ELaunchStatus.MENU -> {
                    MenuFragment.newInstance(this)
                }

                LaunchViewModel.ELaunchStatus.AUTH -> {
                    AuthTransactionFragment.newInstance(this)
                }

                LaunchViewModel.ELaunchStatus.HISTORY -> {
                    TransactionsFragment.newInstance(this)
                }

                LaunchViewModel.ELaunchStatus.SEARCH -> {
                    SearchTransactionFragment.newInstance(this)
                }

                LaunchViewModel.ELaunchStatus.VIEW -> {
                    DetailTransactionFragment.newInstance(this)
                }

                else -> {
                    null
                }
            }
            fragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerLogin, fragment)
                    .commit()
            }

        }
    }

    private fun initViewModel() {
        val factory = LaunchViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[LaunchViewModel::class.java]
    }

    private fun setVideo() {
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.blue_dust}")
        binding.videoBackground.setVideoURI(videoUri)
    }

    override fun onConfigurationCompleted() {
        viewModel.confirmStep(LaunchViewModel.ELaunchStatus.CONFIG)
    }

    override fun onLoginCompleted() {
        viewModel.confirmStep(LaunchViewModel.ELaunchStatus.LOGIN)
    }

    override fun toAuthTransaction() {
        viewModel.confirmStep(LaunchViewModel.ELaunchStatus.MENU)
    }

    override fun toAuthCancelled() {
        viewModel.confirmStep(LaunchViewModel.ELaunchStatus.LOGIN)
    }

    override fun toSearchTransactions() {
        viewModel.confirmStep(LaunchViewModel.ELaunchStatus.SEARCH)
    }

    override fun toSearchCanceled() {
        viewModel.confirmStep(LaunchViewModel.ELaunchStatus.LOGIN)
    }

    override fun toViewTransaction() {
        viewModel.confirmStep(LaunchViewModel.ELaunchStatus.VIEW)
    }

    override fun toViewCanceled() {
        viewModel.confirmStep(LaunchViewModel.ELaunchStatus.VIEW)
    }
}