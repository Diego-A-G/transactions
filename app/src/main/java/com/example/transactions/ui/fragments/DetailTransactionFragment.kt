import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.transactions.databinding.FragmentDetailTransactionBinding
import com.example.transactions.ui.IStepListener
import com.example.transactions.ui.vos.TransactionVO

class DetailTransactionFragment : Fragment() {

    private lateinit var binding: FragmentDetailTransactionBinding
    private lateinit var authStepListener: IStepListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el diseño del fragmento usando ViewBinding
        binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener los detalles de la transacción de algún lugar (por ejemplo, argumentos o base de datos)
        val transactionDetails = getTransactionDetails()

        // Configurar los TextViews con los detalles de la transacción
        binding.textViewReceiptId.text = "Número de Recibo: ${transactionDetails.receiptId}"
        binding.textViewRRN.text = "Número de RRN: ${transactionDetails.rrn}"
        binding.textViewStatusCode.text = "Código de Estado: ${transactionDetails.statusCode}"
        binding.textViewStatusDescription.text =
            "Descripción de Estado: ${transactionDetails.statusDescription}"
    }

    private fun getTransactionDetails(): TransactionVO {
        // Aquí deberías implementar la lógica para obtener los detalles de la transacción
        // Puedes obtenerlos de argumentos, una base de datos, o de cualquier otra fuente de datos
        // En este ejemplo, asumimos que tienes una función que los devuelve.
        return TransactionVO(
            "12345",
            "67890",
            "00",
            "Aprobada",
            "1234567890123456",
            "",
            "",
            "",
            ""
        )
    }

    private fun setAuthListener(authStepListener: IStepListener) {
        this.authStepListener = authStepListener
    }

    companion object {

        fun newInstance(authStepListener: IStepListener): DetailTransactionFragment {
            return DetailTransactionFragment().apply {
                setAuthListener(authStepListener)
            }
        }
    }
}
