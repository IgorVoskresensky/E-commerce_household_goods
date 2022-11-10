package ru.ivos.e_commerce_household_goods.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ivos.e_commerce_household_goods.R
import ru.ivos.e_commerce_household_goods.utils.INTRODUCTION_KEY
import ru.ivos.e_commerce_household_goods.utils.SHOPPING_ACTIVITY
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val  sharedPreferences: SharedPreferences,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var _navigate = MutableStateFlow(0)
    val navigate: StateFlow<Int> = _navigate

    init {
        val isButtonClicked = sharedPreferences.getBoolean(INTRODUCTION_KEY, false)
        val user = auth.currentUser
        if(user != null){
            viewModelScope.launch {
                _navigate.emit(SHOPPING_ACTIVITY)
            }
        } else if (isButtonClicked){
            viewModelScope.launch {
                _navigate.emit(ACCOUNT_OPTIONS_FRAGMENT)
            }
        }
    }

    fun startButtonClicked() {
        sharedPreferences.edit().putBoolean(INTRODUCTION_KEY, true).apply()
    }

    companion object {
        const val ACCOUNT_OPTIONS_FRAGMENT = R.id.action_introductionFragment_to_accountOptionsFragment
    }
}