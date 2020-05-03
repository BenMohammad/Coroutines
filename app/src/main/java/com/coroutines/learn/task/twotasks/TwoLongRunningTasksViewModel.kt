package com.coroutines.learn.task.twotasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.data.api.ApiHelper
import com.coroutines.data.local.DatabaseHelper
import com.coroutines.utils.Resource
import kotlinx.coroutines.*
import java.lang.Exception

class TwoLongRunningTasksViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    fun startLongRunningTask() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            try {
                val resultOneDeferred = async { doLongRunningTaskOne() }
                val resultTwoDeferred = async { doLongRunningTaskTwo() }
                val combinedResult = resultOneDeferred.await() + resultTwoDeferred.await()

                status.postValue(Resource.success("Task Completed: $combinedResult"))
            } catch (e: Exception) {
                status.postValue(Resource.error("Something went wrong", null))
            }
        }
    }
    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

    private suspend fun doLongRunningTaskOne(): String {
        return withContext(Dispatchers.Default) {
            delay(5000)
            return@withContext "One"

        }
    }

    private suspend fun doLongRunningTaskTwo(): String {
        return withContext(Dispatchers.Default) {
            delay(5000)
            return@withContext "Two"
        }

    }}