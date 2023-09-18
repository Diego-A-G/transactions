import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.transactions.Constants
import com.example.transactions.Session
import com.example.transactions.databinding.FragmentAuthTransactionsBinding
import com.example.transactions.ui.IStepListener
import com.example.transactions.ui.managers.ProcessStatus
import com.example.transactions.ui.managers.TransactionViewModel
import com.example.transactions.ui.managers.TransactionViewModelFactory
import com.example.transactions.ui.vos.TransactionVO

class AuthTransactionFragment : Fragment() {

    private var _binding: FragmentAuthTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel
    private lateinit var authStepListener: IStepListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        checkIsView()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.getProcessStatus().observe(viewLifecycleOwner) {
            when (it) {
                is ProcessStatus.Success -> {
                    authStepListener.toAuthTransaction()
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

    private fun checkIsView() {

    }

    private fun initViewModel() {
        val factory = TransactionViewModelFactory(Session.header, Constants.URL, requireContext())
        this.viewModel = ViewModelProvider(this, factory)[TransactionViewModel::class.java]
    }

    private fun setListeners() {
        binding.btnSend.setOnClickListener {
            //validar campos faltantes
            sendAuthorization(getTransactionInfo())
        }
        binding.btnBack.setOnClickListener {
            authStepListener.toAuthCancelled()
        }

    }

    private fun sendAuthorization(transactionInfo: TransactionVO) {
        viewModel.sendAuthorization(transactionInfo)
    }

    private fun getTransactionInfo(): TransactionVO {
        return TransactionVO(
            id = binding.editTextId.text.toString(),
            amount = binding.editTextAmount.text.toString(),
            card = binding.editTextCard.text.toString(),
            commerceCode = binding.editTextCommerceCode.text.toString(),
            terminalCode = binding.editTextTerminalCode.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Desvincular el objeto ViewBinding para evitar pérdida de memoria
        _binding = null
    }


    private fun setAuthListener(authStepListener: IStepListener) {
        this.authStepListener = authStepListener
    }

    companion object {

        fun newInstance(authStepListener: IStepListener): AuthTransactionFragment {
            return AuthTransactionFragment().apply {
                setAuthListener(authStepListener)
            }
        }
    }
}
