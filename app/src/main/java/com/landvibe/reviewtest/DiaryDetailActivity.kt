package com.landvibe.reviewtest

import android.R.id.home
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.landvibe.reviewtest.common.AppDatabase
import com.landvibe.reviewtest.diary.Diary
import com.landvibe.reviewtest.promise.Promise
import kotlinx.android.synthetic.main.activity_diary_detail.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("DEPRECATION")
class DiaryDetailActivity : AppCompatActivity(), GestureDetector.OnDoubleTapListener {
    @RequiresApi(Build.VERSION_CODES.O)
    //val now: LocalDate = LocalDate.now()
    val now: Long = System.currentTimeMillis()
    private var dayMode: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    val time = Date(now)
    private val mFormat = SimpleDateFormat("yyyy年 MM月 dd日 HH:mm")
    private val date: String = mFormat.format(time)

    private var mode: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)
        setupToolbarWrite()
        setupListener()
        //createAlertDialog()

        loadDate()
        loadDiary()

        //loadPromise()
    }

    override fun onDoubleTap(event: MotionEvent?): Boolean {
        Log.d("Gestures", "onDoubleTap: $event")
        Toast.makeText(this, "더블탭", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent?): Boolean {
        Log.d("Gestures", "onDoubleTapEvent: $event")
        Toast.makeText(this, "더블탭", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDate() {
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
        edit_promise.setText(diary.promise)
        val time = Date(diary.date)
        val mFormat = SimpleDateFormat("yyyy年 MM月 dd日 HH:mm")
        val date: String = mFormat.format(time)
        text_date.text = date
    }

    //저장
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener() {
        bt_promise.setOnClickListener {
            //alertDialog.show()
            if (!mode) {
                diary_detail_promise_layout.visibility = View.VISIBLE
                bt_promise.foreground = resources.getDrawable(R.drawable.heart2)
                mode = true
            } else {
                diary_detail_promise_layout.visibility = View.GONE
                bt_promise.foreground = resources.getDrawable(R.drawable.heart)
                mode = false
            }
        }

        bt_store.setOnClickListener {
            Toast.makeText(this, "일기가 저장되었습니다", Toast.LENGTH_SHORT).show()
            val id = intent.getIntExtra("id", 0)
            val title = edit_title.text.toString()
            val contents = edit_body.text.toString()
            val promise = edit_promise.text.toString()

            if (intent.getIntExtra("id", 0) == 0) {
                val diary = Diary(id, now, title, contents, promise)
                if (promise.isNotEmpty()) {
                    val promiseDb = Promise(id, now, promise)
                    AppDatabase.instance.promiseDao().insert(promiseDb)
                }
                AppDatabase.instance.diaryDao().insert(diary)
            } else {
                val diaryDemo = AppDatabase.instance.diaryDao().get(id)
                val promiseDemo = AppDatabase.instance.promiseDao().get(id)
                val nowDate = diaryDemo.date

                val diary: Diary = Diary(id, nowDate, title, contents, promise)

                if (promise != promiseDemo?.promise) {//수정
                    val promiseDb = Promise(id, now, promise)
                    AppDatabase.instance.promiseDao().insert(promiseDb)
                }

                AppDatabase.instance.diaryDao().insert(diary)
            }

            finish()
        }
    }

    //툴바
    private fun setupToolbarWrite() {
        setSupportActionBar(main_write_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_write_top, menu)
        return true
        //return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_write_delete -> {
                val id = intent.getIntExtra("id", 0)
                val diary = AppDatabase.instance.diaryDao().get(id)
                AppDatabase.instance.diaryDao().delete(diary)
                finish()
                true
            }
            home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

//application - activity(화면) - fragment(화면) - view