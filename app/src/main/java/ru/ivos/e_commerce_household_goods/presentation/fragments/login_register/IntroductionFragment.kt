package ru.ivos.e_commerce_household_goods.presentation.fragments.login_register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import ru.ivos.e_commerce_household_goods.R
import ru.ivos.e_commerce_household_goods.databinding.FragmentIntroductionBinding
import ru.ivos.e_commerce_household_goods.presentation.activities.ShoppingActivity
import ru.ivos.e_commerce_household_goods.presentation.viewmodels.IntroductionViewModel
import ru.ivos.e_commerce_household_goods.utils.SHOPPING_ACTIVITY
import java.lang.RuntimeException

class IntroductionFragment : Fragment() {

    private var _binding: FragmentIntroductionBinding? = null
    private val binding: FragmentIntroductionBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    private val viewModel by viewModels<IntroductionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.navigate.collect {
                when (it) {
                    SHOPPING_ACTIVITY -> {
                        Intent(requireActivity(), ShoppingActivity::class.java).also {
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(it)
                        }
                    }
                    IntroductionViewModel.ACCOUNT_OPTIONS_FRAGMENT -> {
                        findNavController().navigate(it)

                    }
                    else -> Unit
                }
            }
        }

        binding.btnGetStarted.setOnClickListener {
            viewModel.startButtonClicked()
            findNavController().navigate(R.id.action_introductionFragment_to_accountOptionsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}