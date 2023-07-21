package com.example.contactmanagerapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {

    // uma funcao suspend, é uma funcao de longa operacao que so vai ser terminada quando todas
    // as suas operacoes acabarem, essa funcao pode ser inciada, pausada e retomada
    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun upodateUser(user: User)

    // deletando um usuario por vez
    @Delete
    suspend fun deleteuser(user: User)

    // deleteando todos os usuarios
    @Query("DELETE FROM user")
    suspend fun deleteAll()

    // para retornamos todos os usuarios da nossa tabela user, nao usaremos o suspend
    @Query("SELECT * FROM user")
    fun getAllUsersInDB(): LiveData<List<User>>
}