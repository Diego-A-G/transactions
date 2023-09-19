package com.example.transactions.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.transactions.Constants
import com.example.transactions.Constants.IS_LOGGED
import com.example.transactions.databinding.FragmentMenuBinding
import com.example.transactions.ui.IStepListener

class MenuFragment : Fragment() {

    private lateinit var authStepListener: IStepListener
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val sp by lazy {
        requireContext().getSharedPreferences(
            Constants.SHARED_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.btnSendTransaction.setOnClickListener {
            authStepListener.toAuthTransaction()
        }
        binding.btnViewTransactions.setOnClickListener {
            authStepListener.toSearchTransactions()
        }
        binding.btnBack.setOnClickListener {
            sp.edit().putBoolean(IS_LOGGED, false).apply()
            authStepListener.onConfigurationCompleted()
        }
    }

    private fun setAuthListener(authStepListener: IStepListener) {
        this.authStepListener = authStepListener
    }

    companion object {

        fun newInstance(authStepListener: IStepListener): MenuFragment {
            return MenuFragment().apply {
                setAuthListener(authStepListener)
            }
        }
    }
}