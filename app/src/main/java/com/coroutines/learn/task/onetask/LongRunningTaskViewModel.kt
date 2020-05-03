package com.coroutines.learn.task.onetask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.data.api.ApiHelper
import com.coroutines.data.local.DatabaseHelper
import com.coroutines.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LongRunningTaskViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
): ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    fun startLongRunningTask() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            try {
                doLongRunningTasks()
                status.postValue(Resource.success("Task Completed!!"))
            } catch (e: Exception) {
                status.postValue(Resource.error("Something went wrong!!", null))
            }
        }
    }

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

    private suspend fun doLongRunningTasks() {
        withContext(Dispatchers.Default) {
            delay(5000)
        }
    }
}