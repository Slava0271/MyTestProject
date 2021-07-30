package com.example.mytestproject.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytestproject.database.users.UsersDao
import com.example.mytestproject.viewmodel.LoginFragmentViewModel

class LoginFragmentFactory(
    private val application: Application,
    private val usersDao: UsersDao
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel(application, usersDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}