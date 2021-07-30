package com.example.mytestproject.ui

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.mytestproject.R
import com.example.mytestproject.database.DataBase
import com.example.mytestproject.databinding.FragmentRegistrationBinding
import com.example.mytestproject.enums.SnackBarErrors
import com.example.mytestproject.ui.factory.RegistrationFragmentFactory
import com.example.mytestproject.viewmodel.RegistrationFragmentViewModel


class Registration : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_registration, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSourceUsers = DataBase.getInstance(application).usersDao

        val viewModelFactory = RegistrationFragmentFactory(application, dataSourceUsers)

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistrationFragmentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.clearErrors.observe(viewLifecycleOwner) {
            binding.textFieldEnterName.error = null
            binding.textFieldEnterFemale.error = null
            binding.textFieldCreateLogin.error = null
            binding.textFieldCreatePassword.error = null
        }

        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)

        viewModel.showError.observe(viewLifecycleOwner) {
            when (it) {
                SnackBarErrors.EMPTY_NAME.error -> binding.textFieldEnterName.error = "Error name"
                SnackBarErrors.EMPTY_LASTNAME.error -> binding.textFieldEnterFemale.error =
                    "Error Last Name"
                SnackBarErrors.EMPTY_LOGIN.error -> binding.textFieldCreateLogin.error =
                    "Error login"
                SnackBarErrors.EMPTY_PASSWORD.error -> binding.textFieldCreatePassword.error =
                    "Error password"
            }
        }
        viewModel.isShowPassword.observe(viewLifecycleOwner) {
            if (it) binding.inputPassword.transformationMethod = null
            else binding.inputPassword.transformationMethod = PasswordTransformationMethod()
        }
        return binding.root
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

}