package ru.ivos.e_commerce_household_goods.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ru.ivos.e_commerce_household_goods.R
import ru.ivos.e_commerce_household_goods.databinding.ActivityLoginRegisterBinding

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}