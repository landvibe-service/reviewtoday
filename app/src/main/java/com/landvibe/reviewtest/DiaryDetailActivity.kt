package com.landvibe.reviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.landvibe.reviewtest.common.AppDatabase
import com.landvibe.reviewtest.diary.Diary
import kotlinx.android.synthetic.main.activity_diary_detail.*

class DiaryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)

        setupListener()
        loadDiary()
    }
//이과는 0부터 시작한다. id를 생성할때. 0을 안쓰긴해 ,,, 0
    /*
        putExtra한 값을 가지고,
        memo를 디비에서 조회해서,
        레이아웃에서 @+id/memo_detail_title의 TextView에 memo.title을 넣어준다
     */
    private fun loadDiary() {
        if (intent.getIntExtra("id", 0) == 0) {
            return
        }
        val id = intent.getIntExtra("id", 0)
        val memo = AppDatabase.instance.diaryDao().get(id)
        edit_title.setText(memo.title)
        edit_body.setText(memo.contents)
    }

    private fun setupListener(){
        bt_store.setOnClickListener {
            val id  = intent.getIntExtra("id", 0)
            val title = edit_title.text.toString()
            val contents = edit_body.text.toString()
            val date = "2000.10.14"
            val diary :Diary = Diary(id, date, title, contents)
            AppDatabase.instance.diaryDao().insert(diary)
            finish()
        }
    }
}

//application - activity(화면) - fragment(화면) - view

