package com.ebeid.passwordmanager.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ebeid.passwordmanager.R
import com.ebeid.passwordmanager.databinding.FragmentStartBinding
import com.ebeid.passwordmanager.presentation.viewmodel.StorePasswordViewModel
import com.ebeid.passwordmanager.utils.DataStatus
import com.ebeid.passwordmanager.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {
    private var binding: FragmentStartBinding? = null
    private lateinit var viewModel: StorePasswordViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)


        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[StorePasswordViewModel::class.java]
        binding!!.joinBtn.setOnClickListener {
            viewModel.joinApp(
                binding!!.passwordField.text.toString(),
                binding!!.confirmPasswordField.text.toString(),
                { navigateToHome() },
                { toast(it) }
            )
        }
    }
    private fun navigateToHome() {
        val action = StartFragmentDirections.actionStartFragmentToSplashFragment()
        action.fromLogin = true
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}