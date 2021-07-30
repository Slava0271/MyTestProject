package com.example.mytestproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.mytestproject.database.users.Users
import com.example.mytestproject.database.users.UsersDao
import com.example.mytestproject.lifecycle.SingleLiveEvent
import com.example.mytestproject.model.ProfileFragmentModel
import com.example.mytestproject.ui.ProfileFragmentDirections
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(
    application: Application,
    private val dao: UsersDao,
    private val userId: Long
) : AndroidViewModel(application) {

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    private val _apply = SingleLiveEvent<Boolean>()
    val apply: LiveData<Boolean> = _apply

    val model = ProfileFragmentModel()

    init {
        viewModelScope.launch {
            val user = getCurrentUser()
            if (user != null) {
                model.firstNameProfile = user.firstName
                model.lastNameProfile = user.lastName
                model.loginProfile = user.login
                model.passwordProfile = user.password
            }
        }
    }

    fun onCancelClick() {
        _navigate.postValue(ProfileFragmentDirections.actionProfileFragmentToLoginFragment(false))
    }

    fun onApplyClick() {
        viewModelScope.launch {
            val user = getCurrentUser()
            if (user != null) {
                user.firstName = model.firstNameProfile
                user.lastName = model.lastNameProfile
                user.login = model.loginProfile
                user.password = model.passwordProfile

                update(user)
            }
        }
        _navigate.postValue(ProfileFragmentDirections.actionProfileFragmentToLoginFragment(false))
    }

    private suspend fun getCurrentUser(): Users? {
        val getUser = getAllUsers().filter { it.usersId == userId }
        if (getUser.isEmpty()) return null
        return getUser[0]
    }

    private suspend fun getAllUsers(): List<Users> {
        return dao.getAllAnswers()
    }

    private suspend fun update(user: Users) {
        dao.update(user)
    }
}