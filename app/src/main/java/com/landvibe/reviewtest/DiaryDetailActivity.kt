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

        loadDiary()
        store()
    }

    /*
        putExtra한 값을 가지고,
        memo를 디비에서 조회해서,
        레이아웃에서 @+id/memo_detail_title의 TextView에 memo.title을 넣어준다
     */
    private fun loadDiary() {
        //val id = intent.getIntExtra("id", -1)
        //val memo = AppDatabase.instance.memoDao().get(id)
        //edit_title.text = .title
    }

    private fun store(){
        bt_store.setOnClickListener {
            val id  = intent.getIntExtra("id", -1)
            val title = edit_title.text.toString()
            val contents = edit_body.text.toString()
            val date = "2000.10.14"
            val diary :Diary = Diary(id, date, title, contents)
            AppDatabase.instance.diaryDao().insert(diary)
        }
    }
}