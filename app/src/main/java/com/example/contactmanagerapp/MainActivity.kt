package com.example.contactmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanagerapp.viewModel.UserViewModel
import com.example.contactmanagerapp.viewModel.UserViewModelFactory
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.room.UserDatabase
import com.example.contactmanagerapp.room.UserRepository
import com.example.contactmanagerapp.viewUI.MyRecyclerViewAdapter
import com.example.contactmanagerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Room database
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository, this)


        userViewModel = ViewModelProvider(
            this,
            factory
        )[UserViewModel::class.java]

        binding.userViewModel = userViewModel

        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerId.layoutManager = LinearLayoutManager(this)
        DisplayUsersList()
    }

    private fun DisplayUsersList() {
        userViewModel.users.observe(this, Observer {
            binding.recyclerId.adapter = MyRecyclerViewAdapter(
                it
            ) { selectedItem: User -> listItemClicked(selectedItem) }
        })
    }

    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(
            this,
            "id: ${selectedItem.getId()} name: ${selectedItem.getName()}",
            Toast.LENGTH_LONG
        ).show()

        userViewModel.deletar(selectedItem)
    }

}