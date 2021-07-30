package com.example.mytestproject.viewmodel

import android.app.Application
import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.mytestproject.database.users.Users
import com.example.mytestproject.database.users.UsersDao
import com.example.mytestproject.enums.SnackBarErrors
import com.example.mytestproject.lifecycle.SingleLiveEvent
import com.example.mytestproject.model.ForgotPasswordFragmentModel
import com.example.mytestproject.ui.ForgotPasswordFragmentDirections
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

class ForgotPasswordFragmentViewModel(application: Application, val usersDao: UsersDao) :
    AndroidViewModel(application) {
    val model = ForgotPasswordFragmentModel()

    private val _isShowErrorEvent = SingleLiveEvent<Int>()
    val isShowErrorEvent: LiveData<Int> = _isShowErrorEvent

    private val _isShowSnackBar = SingleLiveEvent<Boolean>()
    val isShowSnackBar: LiveData<Boolean> = _isShowSnackBar

    private val _clearError = SingleLiveEvent<Boolean>()
    val clearError: LiveData<Boolean> = _clearError

    private val _isShowPassword = SingleLiveEvent<Boolean>()
    val isShowPassword: LiveData<Boolean> = _isShowPassword

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    fun onConfirmClick() {
        _clearError.value = true

        model.apply {
            viewModelScope.launch {
                if (!isLoginWrong()) {
                    val user = getCurrentUser()
                    if (user == null) {
                        _isShowErrorEvent.value = SnackBarErrors.USER_NOT_EXISTS.error
                    } else {
                        if (isRePassword()) {
                            user.password = md5(model.passwordForgot)
                            update(user)
                            _isShowSnackBar.value = true
                        } else {
                            _isShowErrorEvent.value = SnackBarErrors.PASSWORD_MISMATCH.error
                        }
                    }
                } else {
                    _isShowErrorEvent.value = SnackBarErrors.EMPTY_LOGIN.error
                }
            }
        }
    }

    fun onCancelClick() {
        _navigate.postValue(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
    }

    fun onCheckedChanged(button: CompoundButton, checked: Boolean) {
        _isShowPassword.value = checked
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    private suspend fun getCurrentUser(): Users? {
        val getUser = getAllUsers().filter { it.login == model.loginForgot }
        if (getUser.isEmpty()) return null
        return getUser.first()
    }

    private suspend fun getAllUsers(): List<Users> {
        return usersDao.getAllAnswers()
    }

    private suspend fun update(users: Users) {
        return usersDao.update(users)
    }
}

