package com.coroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.coroutines.learn.errorhandling.exceptionhandler.ExceptionHandlerActivity
import com.coroutines.learn.retrofit.parallel.ParallelNetworkCallsActivity
import com.coroutines.learn.retrofit.series.SeriesNetworkCallsActivity
import com.coroutines.learn.retrofit.single.SingleNetworkCallActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun startSingleNetworkCallActivity(view: View) {
        startActivity(Intent(this@MainActivity, SingleNetworkCallActivity::class.java))
    }

    fun startSeriesNetworkCallsActivity(view: View){
        startActivity(Intent(this@MainActivity, SeriesNetworkCallsActivity::class.java))
    }

    fun startParallelNetworkCallsActivity(view: View) {
        startActivity(Intent(this@MainActivity, ParallelNetworkCallsActivity::class.java))
    }

    fun startRoomDatabaseActivity(view: View) {

    }

    fun startTimeoutActivity(view: View) {

    }

    fun startTryCatchActivity(view: View) {

    }

    fun startExceptionHandlerActivity(view: View) {
        startActivity(Intent(this@MainActivity, ExceptionHandlerActivity::class.java))
    }

    fun startIgnoreErrorAndContinueActivity(view: View){

    }

    fun startLongRunningTaskActivity(view: View) {

    }

    fun startTwoLongRunningTasksActivity(view: View){

    }


}
