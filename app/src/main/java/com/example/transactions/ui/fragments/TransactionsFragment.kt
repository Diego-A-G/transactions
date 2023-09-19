import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transactions.databinding.FragmentListTransactionsBinding
import com.example.transactions.repository.declaration.IAnnulmentDialog
import com.example.transactions.ui.IStepListener
import com.example.transactions.ui.managers.TransactionViewModel
import com.example.transactions.ui.managers.TransactionViewModelFactory
import com.example.transactions.ui.vos.TransactionListAdapter

class TransactionsFragment : Fragment(), IAnnulmentDialog {

    private var _binding: FragmentListTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel
    private lateinit var authStepListener: IStepListener

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
        viewModel.getTransactions().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.recyclerViewTransactions.visibility = View.GONE
                binding.emptyTextView.visibility = View.VISIBLE
            } else {
                binding.recyclerViewTransactions.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TransactionListAdapter(it, this@TransactionsFragment)
                }
            }
        }
        viewModel.fetchTransactions()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            authStepListener.toAuthCancelled()
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

        fun newInstance(authStepListener: IStepListener): TransactionsFragment {
            return TransactionsFragment().apply {
                setAuthListener(authStepListener)
            }
        }
    }

    override fun onAnnulmentTransaction(
        receiptId: String,
        rrn: String,
        commerceCode: String,
        terminalCode: String
    ) {
        viewModel.annulTransaction(receiptId, rrn, commerceCode, terminalCode)
    }

}
