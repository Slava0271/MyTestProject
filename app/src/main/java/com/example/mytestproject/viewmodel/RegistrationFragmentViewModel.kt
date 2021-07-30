package com.example.mytestproject.viewmodel

import android.app.Application
import android.widget.CompoundButton
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.mytestproject.database.users.Users
import com.example.mytestproject.database.users.UsersDao
import com.example.mytestproject.enums.SnackBarErrors
import com.example.mytestproject.lifecycle.SingleLiveEvent
import com.example.mytestproject.model.RegistrationFragmentModel
import com.example.mytestproject.ui.RegistrationDirections
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

class RegistrationFragmentViewModel(application: Application, val usersDao: UsersDao) :
    AndroidViewModel(application) {

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    private val _clearErrors = SingleLiveEvent<Boolean>()
    val clearErrors = _clearErrors

    private val _showError = SingleLiveEvent<Int>()
    val showError: LiveData<Int> = _showError

    private val _isShowPassword = SingleLiveEvent<Boolean>()
    val isShowPassword: LiveData<Boolean> = _isShowPassword

    val model = RegistrationFragmentModel()

    fun onSubmitClick() {
        _clearErrors.value = true

        model.apply {
            if (!isFirstNameEmpty() && !isLastNameEmpty() && !isLoginEmpty() && !isPasswordEmpty()) {
                viewModelScope.launch {
                    createUser()
                }
                _navigate.postValue(RegistrationDirections.actionRegistrationToLoginFragment(true))
            } else {
                if (isFirstNameEmpty()) _showError.value = SnackBarErrors.EMPTY_NAME.error
                if (isLastNameEmpty()) _showError.value = SnackBarErrors.EMPTY_LASTNAME.error
                if (isLoginEmpty()) _showError.value = SnackBarErrors.EMPTY_LOGIN.error
                if (isPasswordEmpty()) _showError.value = SnackBarErrors.EMPTY_PASSWORD.error
            }
        }
    }

    fun onCheckedChanged(button: CompoundButton, checked: Boolean) {
        _isShowPassword.value = checked
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    private suspend fun createUser() {
        val user = Users()
        user.firstName = model.firstName
        user.lastName = model.lastName
        user.login = model.login
        user.password = md5(model.password)
        insert(user)
    }

    private suspend fun insert(user: Users) {
        usersDao.insert(user)
    }

    private suspend fun getAllUsers(): List<Users> {
        return usersDao.getAllAnswers()
    }
}

