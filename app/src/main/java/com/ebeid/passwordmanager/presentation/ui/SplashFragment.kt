package com.ebeid.passwordmanager.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ebeid.passwordmanager.R
import com.ebeid.passwordmanager.presentation.viewmodel.StorePasswordViewModel
import com.ebeid.passwordmanager.utils.DataStatus
import com.ebeid.passwordmanager.utils.toast

class SplashFragment : Fragment() {
    private lateinit var viewModel: StorePasswordViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[StorePasswordViewModel::class.java]

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args by navArgs<SplashFragmentArgs>()
        if (args.fromLogin) {
            viewModel.getAllPasswords()
            viewModel.passwordsList.observe(viewLifecycleOwner){
                when(it.status){
                    DataStatus.Status.LOADING -> {

                    }
                    DataStatus.Status.SUCCESS -> {
                        Log.i("DATAAAAAA", it.toString())
                        navigateToHome()
                    }
                    DataStatus.Status.FAIL -> {
                        toast(it.message)
                    }
                }
            }
        } else {
            viewModel.getPassword(
                { navigateToLogin() },
                { toast(it) },
                { navigateToStart() }
            )
        }
    }
    private fun navigateToLogin() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }

    private fun navigateToStart() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToStartFragment())
    }

    private fun navigateToHome() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
    }

}