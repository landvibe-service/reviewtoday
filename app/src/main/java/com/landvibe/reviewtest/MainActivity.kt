package com.landvibe.reviewtest

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.landvibe.reviewtest.common.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*
//import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: DiaryRecyclerViewAdapter
    private var backBtnTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupRecyclerView()
        insertPlusDiary()
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

    override fun onBackPressed() {
        val curTime = System.currentTimeMillis()
        val gapTime = curTime - backBtnTime
        if(gapTime in 0..2000)
            super.onBackPressed()
        else{
            backBtnTime = curTime
            Toast.makeText(this, "앱을 종료하려면 한번 더 누르세요", Toast.LENGTH_SHORT).show()

        }
    }


    //툴바
    private fun setupToolbar(){
        setSupportActionBar(main_toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)
        return true
        //return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            R.id.menu_promise_list->{
                val intent = Intent(this, PromiseListActivity::class.java)
                intent.putExtra("id", 0)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    //추가버튼
    private fun insertPlusDiary() {
        main_button_add.setOnClickListener {
            val intent = Intent(this, DiaryDetailActivity::class.java)
            intent.putExtra("id", 0)
            startActivity(intent)
        }
    }


    //상태 불러오기
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