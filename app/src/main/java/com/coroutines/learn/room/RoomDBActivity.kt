package com.coroutines.learn.room

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.coroutines.R
import com.coroutines.data.api.ApiHelperImpl
import com.coroutines.data.api.RetrofitBuilder
import com.coroutines.data.local.DatabaseBuilder
import com.coroutines.data.local.DatabaseHelperImpl
import com.coroutines.data.local.entity.User
import com.coroutines.learn.base.ApiUserAdapter
import com.coroutines.learn.base.UserAdapter
import com.coroutines.utils.Status
import com.coroutines.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RoomDBActivity: AppCompatActivity() {

    private lateinit var viewModel : RoomDBViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(
            arrayListOf()
        )

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = userAdapter
    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users)}
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun renderList(list: List<User>) {
        userAdapter.addData(list)
        userAdapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        ).get(RoomDBViewModel::class.java)
    }
}