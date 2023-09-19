package com.example.transactions.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.transactions.databinding.DialogRequiredInfoBinding

class RequiredDialogFragment : DialogFragment() {

    // Lista de campos faltantes
    private lateinit var missingFieldsList: List<String>

    private var _binding: DialogRequiredInfoBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String, missingFields: List<String>): RequiredDialogFragment {
            val fragment = RequiredDialogFragment()
            fragment.missingFieldsList = missingFields
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRequiredInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el adaptador para la lista de campos faltantes
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            missingFieldsList
        )
        binding.blankItemsListView.adapter = adapter

        // Configura el botón OK para cerrar el diálogo
        binding.okButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
