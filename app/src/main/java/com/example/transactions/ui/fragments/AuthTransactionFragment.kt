import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.transactions.Constants.REQUEST_INTERNET_PERMISSION
import com.example.transactions.R
import com.example.transactions.databinding.FragmentAuthTransactionsBinding
import com.example.transactions.isFilled
import com.example.transactions.isShow
import com.example.transactions.ui.IStepListener
import com.example.transactions.ui.dialog.RequiredDialogFragment
import com.example.transactions.ui.managers.ProcessStatus
import com.example.transactions.ui.managers.TransactionViewModel
import com.example.transactions.ui.managers.TransactionViewModelFactory
import com.example.transactions.ui.vos.TransactionVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class AuthTransactionFragment : Fragment() {

    private var _binding: FragmentAuthTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel
    private lateinit var authStepListener: IStepListener
    private var requiredInfo: MutableSet<String> = mutableSetOf()
    private var scope = CoroutineScope(Dispatchers.IO)

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
        fillRequiredList()
        fillDefaultInfo()
    }

    private fun fillDefaultInfo() {
        binding.editTextId.setText(UUID.randomUUID().toString())
        binding.editTextCommerceCode.setText("000123")
        binding.editTextTerminalCode.setText("000ABC")
        binding.editTextCard.setText("1234567890123456")
    }

    private fun fillRequiredList() {
        requiredInfo = mutableSetOf(
            resources.getString(R.string.transaction_id),
            resources.getString(R.string.commerce_code),
            resources.getString(R.string.terminal_code),
            resources.getString(R.string.amount),
            resources.getString(R.string.card)
        )
    }

    private fun setObservers() {
        viewModel.getProcessStatus().observe(viewLifecycleOwner) {
            when (it) {
                is ProcessStatus.Loading -> {
                    showButton(false)
                    showMessage(resources.getString(R.string.sending_auth), true)
                    binding.btnSend.isEnabled = false
                }

                is ProcessStatus.Error -> {
                    showButton(true)
                    showMessage(it.message, it.message.isNotBlank())
                    binding.btnSend.isEnabled = it.message.isNotBlank()
                }

                is ProcessStatus.Success -> {
                    showButton(true)
                    showMessage("", false)
                    binding.btnSend.isEnabled = false
                    showMessage("enviado", true)
                    scope.launch {
                        delay(2000)
                        authStepListener.onLoginCompleted()
                    }
                }
            }
        }
    }

    private fun showButton(isShow: Boolean) {
        binding.authProgress.isShow(!isShow)
        binding.btnBack.isShow(isShow)
        binding.btnSend.isShow(isShow)
    }

    private fun showMessage(error: String, visible: Boolean) {
        binding.authErrorTextView.text = error
        binding.authErrorTextView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun checkIsView() {

    }

    private fun initViewModel() {
        val factory = TransactionViewModelFactory(requireContext())
        this.viewModel = ViewModelProvider(this, factory)[TransactionViewModel::class.java]
    }

    private fun setListeners() {
        binding.btnSend.setOnClickListener {
            showMessage("", false)
            if (hasCompletedInfo()) {
                if (checkInternetPermission()) {
                    // El permiso de Internet ya está otorgado, procede con la operación de red
                    sendAuthorization(getTransactionInfo())
                } else {
                    // El permiso de Internet no está otorgado, solicítalo al usuario
                    requestInternetPermission()
                }
            } else {
                showRequiredInfo()
            }
        }
        binding.btnBack.setOnClickListener {
            authStepListener.toAuthCancelled()
        }
    }


    private fun checkInternetPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestInternetPermission() {
        requestPermissions(arrayOf(Manifest.permission.INTERNET), REQUEST_INTERNET_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_INTERNET_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso de Internet otorgado, procede con la operación de red
                    sendAuthorization(getTransactionInfo())
                } else {
                    // Permiso de Internet denegado, muestra un mensaje al usuario o maneja el caso en consecuencia
                }
            }
        }
    }


    private fun showRequiredInfo() {
        val alertDialog = RequiredDialogFragment.newInstance(
            resources.getString(R.string.error_empty_fields),
            requiredInfo.toList()
        )
        alertDialog.show(parentFragmentManager, "fragment_alert")
    }

    private fun hasCompletedInfo(): Boolean {
        if (binding.editTextId.isFilled()) requiredInfo.remove(resources.getString(R.string.transaction_id))
        if (binding.editTextCommerceCode.isFilled()) requiredInfo.remove(resources.getString(R.string.commerce_code))
        if (binding.editTextTerminalCode.isFilled()) requiredInfo.remove(resources.getString(R.string.terminal_code))
        if (binding.editTextAmount.isFilled()) requiredInfo.remove(resources.getString(R.string.amount))
        if (binding.editTextCard.isFilled()) requiredInfo.remove(resources.getString(R.string.card))
        return requiredInfo.isEmpty()
    }

    private fun sendAuthorization(transactionInfo: TransactionVO) {
        viewModel.sendAuthorization(transactionInfo)
    }

    private fun getTransactionInfo(): TransactionVO {
        return TransactionVO(
            id = binding.editTextId.text.toString(),
            amount = convertAmountToInteger(binding.editTextAmount.text.toString()),
            card = binding.editTextCard.text.toString(),
            commerceCode = binding.editTextCommerceCode.text.toString(),
            terminalCode = binding.editTextTerminalCode.text.toString()
        )
    }

    private fun convertAmountToInteger(text: String): String {
        val sanitizedText = text.replace("[^\\d.]".toRegex(), "") // Elimina caracteres no numéricos
        val decimalIndex =
            sanitizedText.indexOf('.') // Encuentra el índice del punto decimal
        // Si hay un punto decimal, elimina los dígitos después del punto
        return if (decimalIndex != -1) sanitizedText.substring(0, decimalIndex)
        else sanitizedText
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
