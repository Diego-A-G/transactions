package com.example.transactions.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.transactions.databinding.FragmentConfigBinding
import com.example.transactions.ui.IStepListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Fragmento que se encarga de cargar las configuraciones en Configurations.
 */
class ConfigurationFragment : Fragment() {

    private lateinit var authStepListener: IStepListener
    private var _binding: FragmentConfigBinding? = null
    private val binding get() = _binding!!
    private var scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chargeConfigurations()
    }

    private fun chargeConfigurations() {
        scope.launch {
            delay(2000)
            //cargar configs
            authStepListener.onConfigurationCompleted()
        }
    }

    private fun setAuthListener(authStepListener: IStepListener) {
        this.authStepListener = authStepListener
    }

    companion object {

        fun newInstance(authStepListener: IStepListener): ConfigurationFragment {
            return ConfigurationFragment().apply {
                setAuthListener(authStepListener)
            }
        }
    }
}