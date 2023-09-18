package com.example.transactions.ui.vos
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.transactions.R
import com.example.transactions.databinding.TransactionItemBinding

class TransactionListAdapter(
    private var transactionList: List<TransactionVO>
) : RecyclerView.Adapter<TransactionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TransactionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = transactionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    inner class ViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransactionVO) {
            binding.textViewTransactionId.text = "ID de Transacción: ${item.id}"
            binding.textViewTransactionAmount.text = "Monto: ${item.amount}"
            binding.textViewTransactionStatus.text = "Estado: ${item.statusDescription}"

            // Puedes personalizar la vista según tus necesidades aquí

            // Ejemplo: Cambiar el color del estado según el valor
            if (item.statusDescription == "Aprobada") {
                binding.textViewTransactionStatus.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.approved)
                )
            } else {
                binding.textViewTransactionStatus.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.denied)
                )
            }

            // Establecer un clic en el elemento
            itemView.setOnClickListener {
                // Abre el detalle del elemento aquí
            }
        }
    }
}
