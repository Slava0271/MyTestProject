package com.example.mytestproject.ui

import android.graphics.Color
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.mytestproject.R
import com.example.mytestproject.database.DataBase
import com.example.mytestproject.databinding.FragmentForgotPasswordBinding
import com.example.mytestproject.enums.SnackBarErrors
import com.example.mytestproject.ui.factory.ForgotPasswordFragmentFactory
import com.example.mytestproject.viewmodel.ForgotPasswordFragmentViewModel
import com.google.android.material.snackbar.Snackbar

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_forgot_password, container, false
        )
        val application = requireNotNull(this.activity).application

        val dataSourceUsers = DataBase.getInstance(application).usersDao

        val viewModelFactory = ForgotPasswordFragmentFactory(application, dataSourceUsers)


        val viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(ForgotPasswordFragmentViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        viewModel.clearError.observe(viewLifecycleOwner) {
            binding.textFieldEnterLogin.error = null
            binding.textFieldEnterPassword.error = null
            binding.textFieldEnterRePassword.error = null
        }

        viewModel.isShowErrorEvent.observe(viewLifecycleOwner) {
            when (it) {
                SnackBarErrors.USER_NOT_EXISTS.error -> binding.textFieldEnterLogin.error =
                    "User not exist"
                SnackBarErrors.PASSWORD_MISMATCH.error -> {
                    binding.textFieldEnterPassword.error = "Passwords mismatch"
                    binding.textFieldEnterRePassword.error = "Passwords mismatch"
                }
                SnackBarErrors.EMPTY_LOGIN.error -> binding.textFieldEnterLogin.error =
                    "Your login is incorrect"
            }
        }

        viewModel.isShowSnackBar.observe(viewLifecycleOwner) {
            showSnackBar("Success")
        }

        viewModel.isShowPassword.observe(viewLifecycleOwner) {
            if (it) {
                binding.passwordForgot.transformationMethod = null
                binding.rePasswordForgot.transformationMethod = null
            } else {
                binding.passwordForgot.transformationMethod = PasswordTransformationMethod()
                binding.rePasswordForgot.transformationMethod = PasswordTransformationMethod()
            }
        }

        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)

        return binding.root
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    private fun showSnackBar(message: String) {
        val snack: Snackbar = Snackbar.make(binding.container, message, Snackbar.LENGTH_LONG)
        snack.setBackgroundTint(Color.parseColor("#0f6626"))
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
    }

}