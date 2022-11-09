package ru.ivos.e_commerce_household_goods.presentation.fragments.login_register

import android.content.Intent
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.ivos.e_commerce_household_goods.R
import ru.ivos.e_commerce_household_goods.databinding.FragmentLoginBinding
import ru.ivos.e_commerce_household_goods.presentation.activities.ShoppingActivity
import ru.ivos.e_commerce_household_goods.presentation.dialogs.setBottomSheetDialog
import ru.ivos.e_commerce_household_goods.presentation.viewmodels.LoginViewModel
import ru.ivos.e_commerce_household_goods.utils.Resource


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDontHaveAccInLF.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmailRF.text.toString().trim()
                val password = etPasswordRF.text.toString().trim()
                viewModel.login(email, password)
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            setBottomSheetDialog {
                viewModel.resetPassword(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect {
                when(it){
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Snackbar.make(requireView(), "Check your email!", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(), "Error!", Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when(it){
                    is Resource.Loading -> {
                        binding.btnLogin.startAnimation()
                    }
                    is Resource.Success -> {
                        Log.d("tag", it.data.toString())
                        binding.btnLogin.revertAnimation()
                        Intent(requireActivity(), ShoppingActivity::class.java).also {
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(it)
                        }
                        Toast.makeText(requireContext(), "Welcome!", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        Log.d("tag", it.message.toString())
                        binding.btnLogin.revertAnimation()
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