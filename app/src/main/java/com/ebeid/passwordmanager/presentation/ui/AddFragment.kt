package com.ebeid.passwordmanager.presentation.ui

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ebeid.passwordmanager.R
import com.ebeid.passwordmanager.databinding.FragmentAddBinding
import com.ebeid.passwordmanager.presentation.viewmodel.StorePasswordViewModel
import com.ebeid.passwordmanager.utils.hide
import com.ebeid.passwordmanager.utils.show
import com.ebeid.passwordmanager.utils.toast
import com.google.android.material.snackbar.Snackbar


class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: StorePasswordViewModel
    private var clipboardManager: android.content.ClipboardManager? = null
    private val args by navArgs<AddFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fromView = args.from == 1
        viewModel = ViewModelProvider(requireActivity())[StorePasswordViewModel::class.java]
        setupScreen()
    }


    private fun setupScreen() {
        when (args.from) {
            0 -> {
                binding.randomCopyPasswordBtn.setOnClickListener {
                    binding.passwordField.setText(
                        viewModel.generateRandomPassword()
                    )
                }
                binding.addBtn.setOnClickListener {
                    binding.loading.show()
                    viewModel.savePassword(
                        title = binding.titleField.text.toString(),
                        account = binding.accountNameField.text.toString(),
                        username = binding.userNameField.text.toString(),
                        password = binding.passwordField.text.toString(),
                        {
                            toast("Password Saved Successfully")
                            binding.loading.hide()
                            emptyFields()
                        },
                        {
                            toast(it)
                            binding.loading.hide()
                        }
                    )
                }
            }

            1 -> {
                val item = viewModel.getItemByIndex(args.index)
                if (item != null) binding.pass = viewModel.getItemByIndex(args.index)
                setupButtons()
            }

            2 -> {
                val item = viewModel.getItemByIndex(args.index)
                if (item != null) binding.pass = viewModel.getItemByIndex(args.index)
                binding.addBtn.text = getString(R.string.update)
                binding.addBtn.setOnClickListener {
                    if (item != null) {
                        item.apply {
                            title = binding.titleField.text.toString().trim()
                            account = binding.accountNameField.text.toString().trim()
                            username = binding.userNameField.text.toString().trim()
                            password = binding.passwordField.text.toString()
                        }
                        viewModel.updatePassword(item, {
                            Snackbar.make(
                                requireView(),
                                "Password Updated !",
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        }, {
                            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT)
                                .show()
                        })
                    }
                }
                binding.randomCopyPasswordBtn.setOnClickListener {
                    binding.passwordField.setText(
                        viewModel.generateRandomPassword()
                    )
                }
            }
        }
    }

    private fun copyText(et: EditText, label: String) {
        if (clipboardManager == null) {
            clipboardManager = requireContext()
                .getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        }
        val clipData = ClipData.newPlainText(label, et.text.toString())
        clipboardManager!!.setPrimaryClip(clipData)
        Snackbar.make(requireView(), "$label is copied", Snackbar.LENGTH_SHORT).show()
    }

    private fun setupButtons() {
        binding.apply {
            copyAccount.setOnClickListener { copyText(accountNameField, "Account") }
            copyUserName.setOnClickListener { copyText(userNameField, "Username") }
            randomCopyPasswordBtn.setOnClickListener {
                copyText(
                    passwordField,
                    "Password"
                )
            }
            accountNameField.setOnClickListener { copyText(accountNameField, "Account") }
            userNameField.setOnClickListener { copyText(userNameField, "Username") }
            passwordField.setOnClickListener {
                copyText(
                    passwordField,
                    "Password"
                )
            }
        }
    }

    private fun emptyFields() {
        binding.titleField.setText("")
        binding.accountNameField.setText("")
        binding.userNameField.setText("")
        binding.passwordField.setText("")
        binding.titleField.requestFocus()
    }
}