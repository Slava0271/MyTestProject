package com.example.mytestproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.mytestproject.database.users.UsersDao
import com.example.mytestproject.lifecycle.SingleLiveEvent
import com.example.mytestproject.network.MarsApi
import com.example.mytestproject.ui.LoadDataFragmentDirections
import kotlinx.coroutines.launch

class LoadDataFragmentViewModel(application: Application, dao: UsersDao, val id: Long) :
    AndroidViewModel(application) {
    private val _eventGetURL = SingleLiveEvent<String>()
    val eventGetURL: LiveData<String> = _eventGetURL

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    private val _showSnackBarEvent = SingleLiveEvent<Boolean>()
    val showSnackBarEvent: LiveData<Boolean> = _showSnackBarEvent

    fun toProfile() {
        _navigate.postValue(LoadDataFragmentDirections.actionLoadDataFragmentToProfileFragment(id));
    }

    fun loadMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()
                _eventGetURL.value = listResult.random().imgSrcUrl
            } catch (e: Exception) {
                _showSnackBarEvent.value = true
                e.printStackTrace()
            }
        }
    }
}