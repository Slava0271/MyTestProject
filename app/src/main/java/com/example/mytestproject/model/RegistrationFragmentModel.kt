package com.example.mytestproject.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistrationFragmentModel(
    private var _firstName: String = "",
    private var _lastName: String = "",
    private var _login: String = "",
    private var _password: String = ""
) : BaseObservable(), Parcelable {

    @get:Bindable
    var firstName: String = _firstName
        set(value) {
            _firstName = value
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var lastName: String = _lastName
        set(value) {
            _lastName = value
            field = value
            notifyPropertyChanged(BR.lastName)
        }

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

    fun isFirstNameEmpty() = firstName.isEmpty() || firstName == " " || firstName.length < 3
    fun isLastNameEmpty() = lastName.isEmpty() || lastName == " " || lastName.length < 3
    fun isLoginEmpty() = login.isEmpty() || login == " " || login.length < 3
    fun isPasswordEmpty() = password.isEmpty() || password == " " || password.length < 3
}