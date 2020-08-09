package com.landvibe.reviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.landvibe.reviewtest.common.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*

class PromiseListActivity : AppCompatActivity() {
    private lateinit var adapter: PromiseRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promise_list)

        setupRecyclerView()
    }
    //앱이 화면에 다시 보여질때(홈버튼을 눌렀다가 다시 보여지거나 다른 화면 갔다 온 경우)
    override fun onResume() {
        super.onResume()
        loadListAndApplyToRecyclerView()
    }

    //상태 불러오기
    private fun loadListAndApplyToRecyclerView() {
        val promiseList = AppDatabase.instance.promiseDao().getAll()
        adapter.items.clear()
        adapter.items.addAll(promiseList)
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        adapter = PromiseRecyclerViewAdapter()
        main_recycler.adapter = adapter
        main_recycler.layoutManager = LinearLayoutManager(this)
    }


}
