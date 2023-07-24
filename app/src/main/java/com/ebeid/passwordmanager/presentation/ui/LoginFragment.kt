package com.ebeid.passwordmanager.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ebeid.passwordmanager.databinding.FragmentLoginBinding
import com.ebeid.passwordmanager.presentation.viewmodel.StorePasswordViewModel
import com.ebeid.passwordmanager.utils.toast

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: StorePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[StorePasswordViewModel::class.java]

        binding!!.loginBtn.setOnClickListener {
            viewModel.login(
                binding!!.passwordField.text.toString(),
                { navigateToSplash() },
                { toast(it) }
            )
        }
    }
    private fun navigateToSplash() {
        val action = LoginFragmentDirections.actionLoginFragmentToSplashFragment()
        action.fromLogin = true
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}