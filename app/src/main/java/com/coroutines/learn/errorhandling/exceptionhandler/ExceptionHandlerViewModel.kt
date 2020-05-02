package com.coroutines.learn.errorhandling.exceptionhandler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.data.api.ApiHelper
import com.coroutines.data.local.DatabaseHelper
import com.coroutines.data.model.ApiUser
import com.coroutines.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ExceptionHandlerViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper): ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    private val exceptionhandler = CoroutineExceptionHandler{_, exception ->
        users.postValue(Resource.error("Something went wrong!!", null))
    }

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(exceptionhandler) {
            users.postValue(Resource.loading(null))
            val usersFromApi = apiHelper.getUsers()
            users.postValue(Resource.success(usersFromApi))
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users

    }
}