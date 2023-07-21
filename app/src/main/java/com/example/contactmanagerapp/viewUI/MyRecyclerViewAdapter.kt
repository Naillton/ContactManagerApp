package com.example.contactmanagerapp.viewUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactmanagerapp.R
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.databinding.CardItemBinding

class MyRecyclerViewAdapter(
    private val userList: List<User>,
    private val clickListing: (User) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.card_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position], clickListing)
    }
}

class MyViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User, clickListing: (User) -> Unit) {
        binding.contactNTV.text = user.getName()
        binding.contactETV.text = user.getEmail()

        binding.linearId.setOnClickListener {
            clickListing(user)
        }
    }
}