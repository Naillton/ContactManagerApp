package com.example.contactmanagerapp.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.room.UserRepository
import kotlinx.coroutines.launch
import java.util.UUID

// criando viewModel do usuario que recebera como parametro o Userepository responsavel por chamar
// os metodos da nossa interface DAO
@SuppressLint("StaticFieldLeak")
class UserViewModel(private val repository: UserRepository, private  val context: Context) : ViewModel(), Observable {

    val users = repository.users
    private var isUpdatedOrDeleted = false
    private lateinit var userToUpdatedOrDeleted: User

    // usando o Bindable que deve ser aplicado a toda classe Observable
    // para aplicar anotacoes e identificar campos que foram mudados
    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    // criando funcao para salvar os dados ou atualizar
    fun save() {
        val name = inputName.value?.length
        val email = inputEmail.value?.length
        if (name != null && email != null) {
            if ((name < 4) || (email < 11)) {
                Toast.makeText(
                    context,
                    "Nome ou Email invalidos revise os campos",
                    Toast.LENGTH_LONG
                ).show()
            } else if (isUpdatedOrDeleted) {
                // atualizando valores
                userToUpdatedOrDeleted.setName(inputName.value!!)
                userToUpdatedOrDeleted.setEmail(inputEmail.value!!)

                update(userToUpdatedOrDeleted)
                inputName.value = null
                inputEmail.value = null
                isUpdatedOrDeleted = false

            } else {
                // inserindo valores
                // usando exclamacoes duplas para dizer que os campos nao podem ser nulos
                val inputN = inputName.value!!
                val inputE = inputEmail.value!!

                insert(User(UUID.randomUUID(), inputN, inputE))

                inputName.value = null
                inputEmail.value = null
            }
        } else {
            Toast.makeText(
                context,
                "Informe valores nos campos",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // criando funcao para deletar todos os dados ou um unico dado
    fun clearAllorDelete() {
        if (isUpdatedOrDeleted) {
            deleteuser(userToUpdatedOrDeleted)
            inputName.value = null
            inputEmail.value = null
            isUpdatedOrDeleted = false
        } else {
            deleteAll()
        }
    }

    // criando funcao privada para inserir os dados usando o viewModelScope para coroutines
    // passando a funcao suspend na nossa classe repository
    private fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    private fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    private fun update(user: User) = viewModelScope.launch {
        repository.update(user)
    }

    private fun deleteuser(user: User) = viewModelScope.launch {
        repository.delete(user)
    }

    fun deletar(selectedItem: User) {
        inputName.value = selectedItem.getName()
        inputEmail.value = selectedItem.getEmail()
        isUpdatedOrDeleted = true
        userToUpdatedOrDeleted = selectedItem
    }

    private fun validCamps(name: String, email: String) {
        if (name.length < 4 || email.length < 11) {
            Toast.makeText(
                context,
                "Nome ou Email invalidos revise os campos",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}