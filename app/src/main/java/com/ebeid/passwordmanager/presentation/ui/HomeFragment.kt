package com.ebeid.passwordmanager.presentation.ui


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.ebeid.passwordmanager.R
import com.ebeid.passwordmanager.databinding.DeleteDialogLayoutBinding
import com.ebeid.passwordmanager.databinding.FragmentHomeBinding
import com.ebeid.passwordmanager.presentation.adapters.PasswordsAdapter
import com.ebeid.passwordmanager.presentation.viewmodel.StorePasswordViewModel
import com.ebeid.passwordmanager.utils.DataStatus
import com.ebeid.passwordmanager.utils.DefaultSwipe
import com.ebeid.passwordmanager.utils.hide
import com.ebeid.passwordmanager.utils.show
import com.ebeid.passwordmanager.utils.toast
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: StorePasswordViewModel
    private lateinit var adapter: PasswordsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[StorePasswordViewModel::class.java]
        if (viewModel.passwordsList.value!!.isEmpty!!) {
            binding.noData.show()
        }
        binding.addBtn.setOnClickListener { navigateToAdd() }
        setUpAdapter()
        setUpItemTouchHelper()
        observeData()
    }
    private fun setUpAdapter(){
        adapter = PasswordsAdapter { navigateToShow(it) }
        adapter.submitList(viewModel.passwordsList.value!!.data)
        binding.passwordsRecyclerView.adapter = adapter
    }
    private fun setUpItemTouchHelper(){
        val itemTouchHelper = ItemTouchHelper(setUpSwipe())
        itemTouchHelper.attachToRecyclerView(binding.passwordsRecyclerView)
    }
    private fun setUpSwipe():DefaultSwipe{
        return DefaultSwipe(requireContext(), { navigateToUpdate(it) }, { index ->
            viewModel.deletePassword(index, {
                Snackbar.make(requireView(), "Password Deleted !", Snackbar.LENGTH_SHORT).show()
            }, {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            })
        })
    }
    private fun observeData() {
        viewModel.passwordsList.observe(viewLifecycleOwner) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                    binding.apply {
                        noData.hide()
                        passwordsRecyclerView.hide()
                        progressBar.show()
                    }
                }

                DataStatus.Status.SUCCESS -> {
                    binding.apply {
                        noData.hide()
                        progressBar.hide()
                        passwordsRecyclerView.show()
                    }
                    adapter.submitList(it.data)
                    if (it.isEmpty!!) {
                        binding.noData.show()
                        binding.progressBar.hide()
                    } else {
                        binding.noData.hide()
                        binding.progressBar.hide()
                    }
                }

                DataStatus.Status.FAIL -> {
                    toast(it.message)
                }
            }
        }
    }

    private fun navigateToShow(index: Int) {
        try {
            val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
            action.from = 1
            action.index = index
            findNavController().navigate(action)
        } catch (_: Exception) {
        }
    }

    private fun navigateToUpdate(index: Int) {
        try {
            val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
            action.from = 2
            action.index = index
            findNavController().navigate(action)
        } catch (_: Exception) {
        }
    }

    private fun navigateToAdd() {
        try {
            val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
            action.from = 0
            findNavController().navigate(action)
        } catch (_: Exception) {
        }
    }

    private fun showDeleteAllDialog() {
        val dialog = Dialog(requireContext(), R.style.dialog)
        val dialogBinding = DeleteDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.deleteBtn.setOnClickListener {
            viewModel.deleteAll { toast(it) }
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText)
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.loginFragment) {
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
            return true
        }
        if (item.itemId == R.id.delete) {
            showDeleteAllDialog()
        }
        return super.onOptionsItemSelected(item)
    }
}