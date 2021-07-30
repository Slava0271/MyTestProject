package com.example.mytestproject.ui

import android.graphics.Color
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
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
import com.example.mytestproject.databinding.FragmentLoginBinding
import com.example.mytestproject.enums.SnackBarErrors
import com.example.mytestproject.ui.factory.LoginFragmentFactory
import com.example.mytestproject.viewmodel.LoginFragmentViewModel
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSourceUsers = DataBase.getInstance(application).usersDao

        val viewModelFactory = LoginFragmentFactory(application, dataSourceUsers)

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(LoginFragmentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)
        viewModel.showSnackBar.observe(viewLifecycleOwner){

           when(it){
               SnackBarErrors.USER_NOT_EXISTS.error -> createSnackBar("User not exists")
               SnackBarErrors.PASSWORD_WRONG.error -> createSnackBar("Password wrong")
           }
        }

        viewModel.isShowPassword.observe(viewLifecycleOwner) {
            if (it) binding.password.transformationMethod = null
            else binding.password.transformationMethod = PasswordTransformationMethod()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val isShowSnackBar = LoginFragmentArgs.fromBundle(args!!).isShowSnackbar
        if (isShowSnackBar) createSnackBar("Your account was created")
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    private fun createSnackBar(message: String) {
        val snack: Snackbar = Snackbar.make(binding.container, message, Snackbar.LENGTH_LONG)
        snack.setBackgroundTint(Color.parseColor("#0f6626"))
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
    }
}