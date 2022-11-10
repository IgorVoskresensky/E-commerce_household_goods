package ru.ivos.e_commerce_household_goods.presentation.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.ivos.e_commerce_household_goods.R
import ru.ivos.e_commerce_household_goods.databinding.FragmentHomeBinding
import ru.ivos.e_commerce_household_goods.presentation.adapters.HomeViewPagerAdapter
import ru.ivos.e_commerce_household_goods.presentation.fragments.categories.*
import java.lang.RuntimeException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairCategoryFragment(),
            CupboardCategoryFragment(),
            TableCategoryFragment(),
            AccessoryCategoryFragment(),
            FurnitureCategoryFragment()
        )

        val vp2Adapter = HomeViewPagerAdapter(
            categoryFragments,
            childFragmentManager,
            lifecycle
        )

        binding.vpHome.adapter = vp2Adapter
        TabLayoutMediator(binding.tlHome, binding.vpHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Chair"
                2 -> tab.text = "Cupboard"
                3 -> tab.text = "Table"
                4 -> tab.text = "Accessory"
                5 -> tab.text = "Furniture"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}