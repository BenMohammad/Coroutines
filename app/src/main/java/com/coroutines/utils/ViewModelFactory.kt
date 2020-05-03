package com.coroutines.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coroutines.data.api.ApiHelper
import com.coroutines.data.api.ApiHelperImpl
import com.coroutines.data.local.DatabaseBuilder
import com.coroutines.data.local.DatabaseHelper
import com.coroutines.learn.errorhandling.exceptionhandler.ExceptionHandlerViewModel
import com.coroutines.learn.errorhandling.supervisor.IgnoreErrorAndContinueViewModel
import com.coroutines.learn.errorhandling.trycatch.TryCatchViewModel
import com.coroutines.learn.retrofit.parallel.ParallelNetworkCallsViewModel
import com.coroutines.learn.retrofit.series.SeriesNetworkCallsViewModel
import com.coroutines.learn.retrofit.single.SingleNetworkCallViewModel
import com.coroutines.learn.room.RoomDBViewModel
import com.coroutines.learn.task.onetask.LongRunningTaskViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ExceptionHandlerViewModel::class.java)) {
            return ExceptionHandlerViewModel(apiHelper, dbHelper) as T
        }
        if(modelClass.isAssignableFrom(ParallelNetworkCallsViewModel::class.java)) {
            return ParallelNetworkCallsViewModel(apiHelper, dbHelper) as T
        }
        if(modelClass.isAssignableFrom(SeriesNetworkCallsViewModel::class.java)) {
            return SeriesNetworkCallsViewModel(apiHelper, dbHelper) as T
        }

        if(modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java)) {
            return SingleNetworkCallViewModel(apiHelper, dbHelper) as T
        }

        if(modelClass.isAssignableFrom(IgnoreErrorAndContinueViewModel::class.java)) {
            return IgnoreErrorAndContinueViewModel(apiHelper, dbHelper) as T
        }
        if(modelClass.isAssignableFrom(TryCatchViewModel::class.java)) {
            return TryCatchViewModel(apiHelper, dbHelper) as T
        }
        if(modelClass.isAssignableFrom(RoomDBViewModel::class.java)) {
            return RoomDBViewModel(apiHelper, dbHelper) as T
        }
        if(modelClass.isAssignableFrom(LongRunningTaskViewModel::class.java)) {
            return LongRunningTaskViewModel(apiHelper, dbHelper) as T

        }
        throw IllegalArgumentException("Unknown class name")
    }
}