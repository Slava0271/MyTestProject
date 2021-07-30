package com.example.mytestproject.ui

import android.os.Bundle
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
import com.example.mytestproject.databinding.FragmentProfileBinding
import com.example.mytestproject.ui.factory.ProfileFragmentFactory
import com.example.mytestproject.viewmodel.ProfileFragmentViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )

        val args = arguments
        val userId = ProfileFragmentArgs.fromBundle(args!!).id

        val application = requireNotNull(this.activity).application

        val dataSourceUsers = DataBase.getInstance(application).usersDao

        val viewModelFactory = ProfileFragmentFactory(application, dataSourceUsers, userId)

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(ProfileFragmentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)

        return binding.root
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }
}
