package com.coroutines.learn.retrofit.parallel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.data.api.ApiHelper
import com.coroutines.data.local.DatabaseHelper
import com.coroutines.data.model.ApiUser
import com.coroutines.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ParallelNetworkCallsViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper): ViewModel() {

    private var users = MutableLiveData<Resource<List<ApiUser>>>()

    init{
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val usersFromApiDeferred = async {apiHelper.getUsers()}
                    val moreUsersFromApiDeferred = async { apiHelper.getMoreUsers() }

                    val usersFromApi = usersFromApiDeferred.await()
                    val moreUsersFromApi = moreUsersFromApiDeferred.await()

                    val allUsersFromApi = mutableListOf<ApiUser>()
                    allUsersFromApi.addAll(usersFromApi)
                    allUsersFromApi.addAll(moreUsersFromApi)

                    users.postValue(Resource.success(allUsersFromApi))
                }
            } catch (e: Exception) {
                users.postValue(Resource.error("Something went wrong", null))

            }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users

    }
}