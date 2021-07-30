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
import com.example.mytestproject.model.LoginFragmentModel
import com.example.mytestproject.ui.LoginFragmentDirections
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

class LoginFragmentViewModel(application: Application, val usersDao: UsersDao) :
    AndroidViewModel(application) {
    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    private val _showSnackBar = SingleLiveEvent<Int>()
    val showSnackBar: LiveData<Int> = _showSnackBar

    private val _isShowPassword = SingleLiveEvent<Boolean>()
    val isShowPassword: LiveData<Boolean> = _isShowPassword

    val model = LoginFragmentModel()


    fun navigateToRegistration() {
        _navigate.postValue(LoginFragmentDirections.actionLoginFragmentToRegistration())
    }

    fun onForgotPassword() {
        _navigate.postValue(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
    }

    fun onLoginClick() {
        viewModelScope.launch {
            val user = getUser(model.login)

            if (user != null) {
                if (checkPassword(user)) {
                    _navigate.postValue(
                        LoginFragmentDirections.actionLoginFragmentToLoadDataFragment(user.usersId)
                    )
                } else {
                    _showSnackBar.value = SnackBarErrors.PASSWORD_WRONG.error
                }
            } else {
                _showSnackBar.value = SnackBarErrors.USER_NOT_EXISTS.error
            }
        }
    }

    fun onCheckedChanged(button: CompoundButton, checked: Boolean) {
        _isShowPassword.value = checked
    }

    private fun checkPassword(users: Users) = md5(model.password) == users.password

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    private suspend fun getAllUsers(): List<Users> {
        return usersDao.getAllAnswers()
    }

    private suspend fun getUser(userName: String): Users? {
        val list = getAllUsers().filter { it.login == userName }
        if (list.isNotEmpty()) return getAllUsers().filter { it.login == userName }[0]
        return null
    }

    private suspend fun clear() = usersDao.clear()
}