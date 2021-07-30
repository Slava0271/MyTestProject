package com.example.mytestproject.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ProfileFragmentModel(
    private var _firstNameProfile: String = "",
    private var _lastNameProfile: String = "",
    private var _loginProfile: String = "",
    private var _passwordProfile: String = ""
) : BaseObservable(), Parcelable {

    @get:Bindable
    var firstNameProfile: String = _firstNameProfile
        set(value) {
            _firstNameProfile = value
            field = value
            notifyPropertyChanged(BR.firstNameProfile)
        }

    @get:Bindable
    var lastNameProfile: String = _lastNameProfile
        set(value) {
            _lastNameProfile = value
            field = value
            notifyPropertyChanged(BR.lastNameProfile)
        }

    @get:Bindable
    var loginProfile: String = _loginProfile
        set(value) {
            _loginProfile = value
            field = value
            notifyPropertyChanged(BR.loginProfile)
        }

    @get:Bindable
    var passwordProfile: String = _passwordProfile
        set(value) {
            _passwordProfile = value
            field = value
            notifyPropertyChanged(BR.passwordProfile)
        }

    fun isFirstNameEmpty() = firstNameProfile.isEmpty() || firstNameProfile == " " || firstNameProfile.length < 3
    fun isLastNameEmpty() = lastNameProfile.isEmpty() || lastNameProfile == " " || lastNameProfile.length < 3
    fun isLoginEmpty() = loginProfile.isEmpty() || loginProfile == " " || loginProfile.length < 3
    fun isPasswordEmpty() = passwordProfile.isEmpty() || passwordProfile == " " || passwordProfile.length < 3
}