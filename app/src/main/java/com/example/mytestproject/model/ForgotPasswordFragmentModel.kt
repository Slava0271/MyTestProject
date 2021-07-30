package com.example.mytestproject.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForgotPasswordFragmentModel(
    private var _loginForgot: String = "",
    private var _passwordForgot: String = "",
    private var _rePasswordForgot: String = ""
) : BaseObservable(), Parcelable {

    @get:Bindable
    var loginForgot: String = _loginForgot
        set(value) {
            _loginForgot = value
            field = value
            notifyPropertyChanged(BR.loginForgot)
        }

    @get:Bindable
    var passwordForgot: String = _passwordForgot
        set(value) {
            _passwordForgot = value
            field = value
            notifyPropertyChanged(BR.passwordForgot)
        }

    @get:Bindable
    var rePasswordForgot: String = _rePasswordForgot
        set(value) {
            _rePasswordForgot = value
            field = value
            notifyPropertyChanged(BR.rePasswordForgot)
        }

    fun isLoginWrong() = loginForgot.isEmpty() || loginForgot == " " || loginForgot.length < 3
    fun isPasswordWrong() =
        passwordForgot.isEmpty() || passwordForgot == " " || passwordForgot.length < 3

    fun isRePassword() = passwordForgot == rePasswordForgot
}