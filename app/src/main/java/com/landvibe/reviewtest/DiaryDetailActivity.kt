package com.landvibe.reviewtest

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.landvibe.reviewtest.common.AppDatabase
import com.landvibe.reviewtest.diary.Diary
import kotlinx.android.synthetic.main.activity_diary_detail.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DiaryDetailActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)

        loadDate()
        loadDiary()
        setupListener()
        backListener()
        deleteListener()
    }
//이과는 0부터 시작한다. id를 생성할때. 0을 안쓰긴해 ,,, 0
    /*
        putExtra한 값을 가지고,
        memo를 디비에서 조회해서,
        레이아웃에서 @+id/memo_detail_title의 TextView에 memo.title을 넣어준다
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDate(){
        val now = LocalDate.now()
        val date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
        text_date.text = date
    }
    private fun loadDiary() {
        if (intent.getIntExtra("id", 0) == 0) {
            return
        }
        val id = intent.getIntExtra("id", 0)
        val diary = AppDatabase.instance.diaryDao().get(id)
        edit_title.setText(diary.title)
        edit_body.setText(diary.contents)
    }
    //뒤로가기
    private fun backListener(){
        bt_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", 0)
            startActivity(intent)
        }
    }
    private fun deleteListener(){
        bt_delete.setOnClickListener {
            val id = intent.getIntExtra("id", 0)
            val diary = AppDatabase.instance.diaryDao().get(id)
            AppDatabase.instance.diaryDao().delete(diary)
            finish()
        }
    }
    //저장
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener(){
        bt_store.setOnClickListener {
            val id  = intent.getIntExtra("id", 0)
            val title = edit_title.text.toString()
            val contents = edit_body.text.toString()
            val now = LocalDate.now()
            val date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
            val diary :Diary = Diary(id, date, title, contents)
            AppDatabase.instance.diaryDao().insert(diary)
            finish()
        }
    }
}

//application - activity(화면) - fragment(화면) - view

