package com.example.contactmanagerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// definindo base de dados local, passando nossa entidade no array de entidades e a versao da base de dados
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDAO

    // criando singleton, um padrao de design em java usado principalmente em bancos de dados
    // que nos permite criar apenas um objeto por vez durante o tempo de execução de uma classe.
    // em kotlin nos criamos singleton com companion objects, que sao objetos instanciados
    // assim que a classe que os cotem e inciada
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                // verificando se a instancia e igual a null se sim que dizer que nao estamos conectados
                // a base de dados e devemos intanciar a base de dados
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "users_db"
                    ).build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}