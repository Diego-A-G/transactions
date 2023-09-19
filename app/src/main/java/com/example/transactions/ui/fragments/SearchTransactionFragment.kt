import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.transactions.databinding.FragmentTransactionsByReceiptBinding
import com.example.transactions.ui.IStepListener
import com.example.transactions.ui.managers.ProcessStatus
import com.example.transactions.ui.managers.TransactionViewModel
import com.example.transactions.ui.managers.TransactionViewModelFactory

class SearchTransactionFragment : Fragment() {

    private var _binding: FragmentTransactionsByReceiptBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel
    private lateinit var authStepListener: IStepListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsByReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.getProcessStatus().observe(viewLifecycleOwner) {
            when (it) {
                is ProcessStatus.Success -> {
                    authStepListener.toViewTransaction()
                }

                is ProcessStatus.Loading -> {
                    //show progress bar
                }

                is ProcessStatus.Error -> {
                    //show error
                }
            }
        }
    }

    private fun setListeners() {
        // Configurar un OnClickListener para el botón de búsqueda
        binding.btnSearch.setOnClickListener {
            //verificar antes de hacer get
            val receiptNumber = binding.editTextReceiptNumber.text.toString()
            // Llamar a la función para buscar la transacción por número de recibo
            viewModel.getTransactionByReceipt(receiptNumber)
        }

    }

    private fun initViewModel() {
        val factory = TransactionViewModelFactory(requireContext())
        this.viewModel = ViewModelProvider(this, factory)[TransactionViewModel::class.java]
    }

    private fun setAuthListener(authStepListener: IStepListener) {
        this.authStepListener = authStepListener
    }

    companion object {

        fun newInstance(authStepListener: IStepListener): SearchTransactionFragment {
            return SearchTransactionFragment().apply {
                setAuthListener(authStepListener)
            }
        }
    }
}
