package com.ebeid.passwordmanager.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebeid.passwordmanager.databinding.PasswordItemLayoutBinding
import com.ebeid.passwordmanager.domain.model.PasswordModel
import javax.inject.Inject

class PasswordsAdapter(private val onClick: (index:Int) -> Unit) :
    ListAdapter<PasswordModel, PasswordsAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var binding =
            PasswordItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = getItem(position)
        holder.bind(currentItem,position)

    }

    inner class ViewHolder(private var binding: PasswordItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(password: PasswordModel,index: Int) {
            binding.root.setOnClickListener {
                onClick(index)
            }
            Log.d("CURRPASSWORD", password.id.toString())
            binding.pass = password
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<PasswordModel>() {
        override fun areItemsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
            return oldItem == newItem
        }

    }

}