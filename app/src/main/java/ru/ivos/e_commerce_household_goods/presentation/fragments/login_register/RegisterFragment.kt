package ru.ivos.e_commerce_household_goods.presentation.fragments.login_register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.ivos.e_commerce_household_goods.R
import ru.ivos.e_commerce_household_goods.data.User
import ru.ivos.e_commerce_household_goods.databinding.FragmentRegisterBinding
import ru.ivos.e_commerce_household_goods.presentation.viewmodels.RegisterViewModel
import ru.ivos.e_commerce_household_goods.utils.Resource
import java.lang.RuntimeException

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("Binding is empty")

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHaveAccInRF.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.apply {
            btnRegisterRF.setOnClickListener {
                val user = User(
                    name = etNameRF.text.toString().trim(),
                    phone = etPhoneRF.text.toString().trim(),
                    email = etEmailRF.text.toString().trim(),
                    password = etPasswordRF.text.toString().trim()
                )
                viewModel.createAccountWithEmailAndPassword(user = user)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when(it){
                    is Resource.Loading -> {
                        binding.btnRegisterRF.startAnimation()
                    }
                    is Resource.Success -> {
                        Log.d("tag", it.data.toString())
                        binding.btnRegisterRF.revertAnimation()
                    }
                    is Resource.Error -> {
                        Log.d("tag", it.message.toString())
                        binding.btnRegisterRF.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}