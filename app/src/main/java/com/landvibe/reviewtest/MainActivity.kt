package com.landvibe.reviewtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.landvibe.reviewtest.common.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: DiaryRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertTestMemo()
        setupRecyclerView()
    }

    //앱이 화면에 다시 보여질때(홈버튼을 눌렀다가 다시 보여지거나 다른 화면 갔다 온 경우)
    override fun onResume() {
        super.onResume()
        loadListAndApplyToRecyclerView()
    }

    //화면이 아예 사라질 때(finish)
    override fun onDestroy() {
        super.onDestroy()
    }

    //화면이 홈버튼 같은거로 백그라운드로 될때
    override fun onPause() {
        super.onPause()
    }

    private fun insertTestMemo() {
        button_list_add.setOnClickListener {
            val intent = Intent(this, DiaryDetailActivity::class.java)
            intent.putExtra("id", 0)
            startActivity(intent)
        }
    }

    private fun loadListAndApplyToRecyclerView() {
        val diaryList = AppDatabase.instance.diaryDao().getAll()
        adapter.items.clear()
        adapter.items.addAll(diaryList)
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        adapter = DiaryRecyclerViewAdapter()
        main_recycler.adapter = adapter
        main_recycler.layoutManager = LinearLayoutManager(this)
    }
}

//detail / modify / create