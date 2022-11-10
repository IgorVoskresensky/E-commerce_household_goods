package ru.ivos.e_commerce_household_goods.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ivos.e_commerce_household_goods.data.Product
import ru.ivos.e_commerce_household_goods.utils.Resource
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore
) : ViewModel() {

    private var _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProducts: StateFlow<Resource<List<Product>>> = _specialProducts

    private var _bestDealsProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestDealsProducts: StateFlow<Resource<List<Product>>> = _bestDealsProducts

    private var _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProducts: StateFlow<Resource<List<Product>>> = _bestProducts

    init {
        fetchSpecialProducts()
        fetchBestDeals()
        fetchBestProducts()
    }

    private fun fetchSpecialProducts() {
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }
        fireStore.collection("Products")
            .whereEqualTo("category", "Special Products")
            .get()
            .addOnSuccessListener {

                val specialProductList = it.toObjects(Product::class.java)
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(specialProductList))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Error(it.message))
                }
            }
    }

    private fun fetchBestDeals() {
        viewModelScope.launch {
            _bestDealsProducts.emit(Resource.Loading())
        }
        fireStore.collection("Products")
            .whereEqualTo("category", "Best Deals")
            .get()
            .addOnSuccessListener {

                val bestDealsList = it.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Success(bestDealsList))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Error(it.message))
                }
            }
    }

    private fun fetchBestProducts() {
        viewModelScope.launch {
            _bestProducts.emit(Resource.Loading())
        }
        fireStore.collection("Products")
            .get()
            .addOnSuccessListener {

                val bestProductsList = it.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Success(bestProductsList))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Error(it.message))
                }
            }
    }
}