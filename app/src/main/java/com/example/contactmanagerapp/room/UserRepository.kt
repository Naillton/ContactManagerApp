package com.example.contactmanagerapp.room

class UserRepository(private val dao: UserDAO) {

    val users = this.dao.getAllUsersInDB()

    suspend fun insert(user: User): Long {
        return this.dao.insertUser(user)
    }

    suspend fun update(user: User) {
        return this.dao.upodateUser(user)
    }

    suspend fun delete(user: User) {
        return this.dao.deleteuser(user)
    }

    suspend fun deleteAll() {
        return this.dao.deleteAll()
    }
}