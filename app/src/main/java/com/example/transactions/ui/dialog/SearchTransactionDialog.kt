package com.example.transactions.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.transactions.databinding.DialogSearchTransactionBinding
import com.example.transactions.repository.declaration.IAnnulmentDialog
import com.example.transactions.repository.declaration.ISearchDialog

class SearchTransactionDialog : DialogFragment() {


    private var _binding: DialogSearchTransactionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSearchTransactionBinding.inflate(
            inflater,
            container,
            false
        )  // Reemplaza YourXmlLayoutBinding con el nombre real del archivo de diseño XML
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asigna un OnClickListener al botón de búsqueda
        binding.btnSearchTransaction.setOnClickListener {
            val transactionId = binding.editTextTransactionId.text.toString()
            val item = searchDialog.searchTransaction(transactionId)
            if (item == null) {
                binding.errorTextView.text = "No existe esa transaccion"
            } else {
                val detailDialog = DetailTransactionDialog.newInstance(
                    item,
                    annulmentDialog
                ) // Pasa los datos del elemento al fragmento
                detailDialog.show(parentFragmentManager, "DetailTransactionDialog")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private lateinit var searchDialog: ISearchDialog
        private lateinit var annulmentDialog: IAnnulmentDialog
        fun newInstance(
            searchDialogParam: ISearchDialog,
            annulmentDialogParam: IAnnulmentDialog
        ): SearchTransactionDialog {
            val fragment = SearchTransactionDialog()
            searchDialog = searchDialogParam
            annulmentDialog = annulmentDialogParam
            return fragment
        }
    }
}


