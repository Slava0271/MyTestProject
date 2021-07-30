package com.example.mytestproject.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mytestproject.R
import com.example.mytestproject.database.DataBase
import com.example.mytestproject.databinding.FragmentForgotPasswordBinding
import com.example.mytestproject.databinding.FragmentLoadDataBinding
import com.example.mytestproject.ui.factory.LoadDataFragmentFactory
import com.example.mytestproject.ui.factory.LoginFragmentFactory
import com.example.mytestproject.viewmodel.LoadDataFragmentViewModel
import com.example.mytestproject.viewmodel.LoginFragmentViewModel
import com.google.android.material.snackbar.Snackbar

class LoadDataFragment : Fragment() {
    private lateinit var binding: FragmentLoadDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_load_data, container, false
        )
        val application = requireNotNull(this.activity).application

        val dataSourceUsers = DataBase.getInstance(application).usersDao

        val args = arguments
        val userId = LoadDataFragmentArgs.fromBundle(args!!).id

        val viewModelFactory = LoadDataFragmentFactory(application, dataSourceUsers, userId)

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(LoadDataFragmentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.eventGetURL.observe(viewLifecycleOwner) {
            val url = it.replace(".jpl", "").replace("http", "https")
            loadPhoto(url)
        }

        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner) {
            createSnackBar("No connection")
        }

        return binding.root
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    private fun loadPhoto(url: String) {
        Glide
            .with(this.requireActivity())
            .load(url)
            .centerCrop()
            .into(binding.imageView);
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