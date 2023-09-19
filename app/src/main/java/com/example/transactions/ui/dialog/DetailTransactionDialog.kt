package com.example.transactions.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.transactions.databinding.DialogDetailTransactionBinding
import com.example.transactions.repository.declaration.IAnnulmentDialog
import com.example.transactions.ui.vos.TransactionVO

class DetailTransactionDialog : DialogFragment() {


    private var _binding: DialogDetailTransactionBinding? = null
    private val binding get() = _binding!!

    companion object {

        private lateinit var annulmentDialog: IAnnulmentDialog
        fun newInstance(
            transaction: TransactionVO,
            annulmentDialogParam: IAnnulmentDialog
        ): DetailTransactionDialog {
            val args = Bundle()
            args.putParcelable("transaction", transaction)
            val fragment = DetailTransactionDialog()
            fragment.arguments = args
            annulmentDialog = annulmentDialogParam
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDetailTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transaction = arguments?.getParcelable<TransactionVO>("transaction")

        // Configura la vista con los datos de la transacción
        if (transaction != null) {
            // Asigna los valores de la transacción a las vistas
            binding.textTransactionId.text = transaction.id
            binding.textCommerceCode.text = transaction.commerceCode
            binding.textTerminalCode.text = transaction.terminalCode
            binding.textAmount.text = transaction.amount
            binding.textCard.text = transaction.card
            binding.textReceipt.text = transaction.receiptId ?: "N/A"
            binding.textRrn.text = transaction.rrn ?: "N/A"
            binding.textStatusCode.text = transaction.statusCode ?: "N/A"
            binding.textStatusDesc.text = transaction.statusDescription ?: "N/A"

            // Ahora, configura el OnClickListener para los botones
            binding.okButton.setOnClickListener {
                dismiss()
            }

            binding.annulButton.setOnClickListener {
                // Lógica para el botón Anular
                with(transaction) {
                    annulmentDialog.onAnnulmentTransaction(
                        receiptId!!,
                        rrn!!,
                        commerceCode,
                        terminalCode
                    )
                }
                binding.annulButton.isEnabled = false
            }
        }

    }
}
