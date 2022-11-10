package ru.ivos.e_commerce_household_goods.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ivos.e_commerce_household_goods.R
import ru.ivos.e_commerce_household_goods.databinding.ActivityShoppingBinding

@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.shoppingFragmentContainer)
        binding.bnvShoppingMenu.setupWithNavController(navController)
    }
}