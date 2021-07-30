package com.example.mytestproject.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytestproject.database.users.UsersDao
import com.example.mytestproject.viewmodel.LoadDataFragmentViewModel

class LoadDataFragmentFactory(
    private val application: Application,
    private val usersDao: UsersDao,
    private val userId: Long
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadDataFragmentViewModel::class.java)) {
            return LoadDataFragmentViewModel(application, usersDao, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}