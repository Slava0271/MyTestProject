package com.example.mytestproject.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginFragmentModel(
    private var _login: String = "",
    private var _password: String = ""
) : BaseObservable(), Parcelable {

    @get:Bindable
    var login: String = _login
        set(value) {
            _login = value
            field = value
            notifyPropertyChanged(BR.login)
        }

    @get:Bindable
    var password: String = _password
        set(value) {
            _password = value
            field = value
            notifyPropertyChanged(BR.password)
        }

    fun isLoginEmpty() = login.isEmpty() || login == " " || login.length < 3
    fun isPasswordEmpty() = password.isEmpty() || password == " " || password.length < 3

}