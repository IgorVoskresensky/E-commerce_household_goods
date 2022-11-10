package ru.ivos.e_commerce_household_goods.presentation.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.ivos.e_commerce_household_goods.databinding.FragmentMainCategotyBinding
import ru.ivos.e_commerce_household_goods.presentation.adapters.BestDealsAdapter
import ru.ivos.e_commerce_household_goods.presentation.adapters.BestProductsAdapter
import ru.ivos.e_commerce_household_goods.presentation.adapters.SpecialProductsAdapter
import ru.ivos.e_commerce_household_goods.presentation.viewmodels.MainCategoryViewModel
import ru.ivos.e_commerce_household_goods.utils.Resource

@AndroidEntryPoint
class MainCategoryFragment : Fragment() {

    private var _binding: FragmentMainCategotyBinding? = null
    private val binding: FragmentMainCategotyBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    private lateinit var adapterSpecial: SpecialProductsAdapter
    private lateinit var adapterBestDeals: BestDealsAdapter
    private lateinit var adapterBestProducts: BestProductsAdapter

    private val viewModel by viewModels<MainCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainCategotyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        specialProductAdapterRV()
        bestDealsAdapter()
        bestProductAdapter()

        lifecycleScope.launchWhenStarted {
            viewModel.specialProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.pbMainFrag.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        adapterSpecial.differ.submitList(it.data)
                        binding.pbMainFrag.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.pbMainFrag.visibility = View.GONE
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestDealsProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.pbMainFrag.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        adapterBestDeals.differ.submitList(it.data)
                        binding.pbMainFrag.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.pbMainFrag.visibility = View.GONE
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.pbMainFrag.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        adapterBestProducts.differ.submitList(it.data)
                        binding.pbMainFrag.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.pbMainFrag.visibility = View.GONE
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun bestProductAdapter() {
        adapterBestProducts = BestProductsAdapter()
        binding.rvBestProducts.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = adapterBestProducts
        }
    }

    private fun bestDealsAdapter() {
        adapterBestDeals = BestDealsAdapter()
        binding.rvBestDealsProducts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterBestDeals
        }
    }

    fun specialProductAdapterRV() {
        adapterSpecial = SpecialProductsAdapter()
        binding.rvSpecialProducts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterSpecial
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}