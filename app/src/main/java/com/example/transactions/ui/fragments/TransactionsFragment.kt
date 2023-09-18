import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transactions.Constants
import com.example.transactions.Session
import com.example.transactions.databinding.FragmentListTransactionsBinding
import com.example.transactions.ui.managers.TransactionViewModel
import com.example.transactions.ui.managers.TransactionViewModelFactory
import com.example.transactions.ui.vos.TransactionListAdapter
import com.example.transactions.ui.vos.TransactionVO

class TransactionsFragment : Fragment() {

    private var _binding: FragmentListTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setListeners()
        setObservers()
    }

    private fun setObservers() {

    }

    private fun setListeners() {
        val transactionList = getTransactionList()
        // Configurar el RecyclerView
        binding.recyclerViewTransactions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TransactionListAdapter(transactionList)
        }
    }

    private fun initViewModel() {
        val factory = TransactionViewModelFactory(Session.header, Constants.URL, requireContext())
        this.viewModel = ViewModelProvider(this, factory)[TransactionViewModel::class.java]
    }

    // Esta función es solo un ejemplo, debes reemplazarla con la lógica real para obtener la lista de transacciones.
    private fun getTransactionList(): List<TransactionVO> {
        val transactionList = mutableListOf<TransactionVO>()
        val transactionVO = TransactionVO(
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
        // Agregar transacciones a la lista (simuladas en este ejemplo)
        transactionList.add(transactionVO)
        transactionList.add(transactionVO)

        return transactionList
    }
}
