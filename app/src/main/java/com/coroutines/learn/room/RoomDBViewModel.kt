package com.coroutines.learn.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.data.api.ApiHelper
import com.coroutines.data.local.DatabaseHelper
import com.coroutines.data.local.entity.User
import com.coroutines.utils.Resource
import kotlinx.coroutines.launch

class RoomDBViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                val usersFromDB = dbHelper.getUsers()
                if(usersFromDB.isEmpty()) {
                    val usersFromApi = apiHelper.getUsers()
                    val usersToInsertInDB = mutableListOf<User>()

                    for(apiUser in usersFromApi) {
                        val user = User(
                            apiUser.id,
                            apiUser.name,
                            apiUser.email,
                            apiUser.avatar
                        )
                        usersToInsertInDB.add(user)
                    }
                    dbHelper.insertAll(usersToInsertInDB)

                    users.postValue(Resource.success(usersToInsertInDB))
                } else {
                    users.postValue(Resource.success(usersFromDB))
                }
            } catch (e: Exception) {
                users.postValue(Resource.error("Something went wrong", null))
            }
        }

    }
    fun getUsers(): LiveData<Resource<List<User>>> {
        return users
    }
}