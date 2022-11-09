package ru.ivos.e_commerce_household_goods.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import ru.ivos.e_commerce_household_goods.data.User
import ru.ivos.e_commerce_household_goods.utils.Resource
import ru.ivos.e_commerce_household_goods.utils.USER_COLLECTION
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    private var _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register: Flow<Resource<User>> = _register

    fun createAccountWithEmailAndPassword(user: User) {
        runBlocking {
            _register.emit(Resource.Loading())
        }
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                it.user?.let {
                    saveUserInfo(it.uid, user)
                }
            }.addOnFailureListener {

            }
    }

    private fun saveUserInfo(userUid: String, user: User) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }
}