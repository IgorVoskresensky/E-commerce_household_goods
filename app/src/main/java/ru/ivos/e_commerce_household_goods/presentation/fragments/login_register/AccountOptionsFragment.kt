package ru.ivos.e_commerce_household_goods.presentation.fragments.login_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ivos.e_commerce_household_goods.R
import ru.ivos.e_commerce_household_goods.databinding.FragmentAccountOptionsBinding

class AccountOptionsFragment : Fragment() {

    private var _binding: FragmentAccountOptionsBinding? = null
    private val binding: FragmentAccountOptionsBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterFAO.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionsFragment_to_registerFragment)
        }
        binding.btnLoginFAO.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionsFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}