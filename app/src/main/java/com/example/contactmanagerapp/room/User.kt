package com.example.contactmanagerapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

// ROOM é o ORM do android, onde usaremos para conseguir acessar e modificar nossos dados.
// A classe User é nossa classe model que usaremos para especificar quais dados o usuario tem

@Entity(tableName = "user")
data class User(

    // para gerarmos ids aleatorios usando o room, usaremos a anotacao primarykey
    // e para especificaros o nome da tabela usaremos a coluna columninfo
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private var id: UUID,

    @ColumnInfo(name = "name")
    private var name: String,

    @ColumnInfo(name = "email")
    private var email: String
) {

    fun getId(): UUID {
        return this.id;
    }

    fun getName(): String {
        return this.name
    }

    fun getEmail(): String {
        return this.email
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setEmail(email: String) {
        this.email = email
    }
}