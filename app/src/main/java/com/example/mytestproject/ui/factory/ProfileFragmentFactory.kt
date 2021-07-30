package com.example.mytestproject.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytestproject.database.users.UsersDao
import com.example.mytestproject.viewmodel.ProfileFragmentViewModel
import com.example.mytestproject.viewmodel.RegistrationFragmentViewModel

class ProfileFragmentFactory(
    private val application: Application,
    private val usersDao: UsersDao,
    private val userId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java)) {
            return ProfileFragmentViewModel(application, usersDao , userId ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}