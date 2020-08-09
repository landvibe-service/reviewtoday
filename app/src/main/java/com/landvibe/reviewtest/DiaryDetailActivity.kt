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
import kotlinx.android.synthetic.main.dialog_promise.*
import kotlinx.android.synthetic.main.dialog_promise.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Suppress("DEPRECATION")
class DiaryDetailActivity : AppCompatActivity(), GestureDetector.OnDoubleTapListener {
    @RequiresApi(Build.VERSION_CODES.O)
    val now: LocalDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    val date: String = now.format(DateTimeFormatter.ofPattern("yyyy年 MM月 dd日"))
    //private lateinit var alertDialog: AlertDialog
    //private lateinit var dialogView: View
    // val promiseMode = false
    private var mode: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)
        setupToolbarWrite()
        setupListener()
        //createAlertDialog()

        loadDiary()
        loadDate()
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

    /*
    @RequiresApi(Build.VERSION_CODES.M)
    private fun createAlertDialog() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialogView = inflater.inflate(R.layout.dialog_promise, null)
        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("확인") { _, _ ->
                val id = intent.getIntExtra("id", 0)
                val content: String = dialogView.dialog_edit.text.toString()
                val promise = Promise(id, date, content)
                AppDatabase.instance.promiseDao().insert(promise)
                if(dialogView.dialog_edit.text.isNotEmpty()) {
                    bt_promise.foreground = resources.getDrawable(R.drawable.heart2)

                    Toast.makeText(this, dialogView.dialog_edit.text.toString(), Toast.LENGTH_SHORT).show()
                }
                else{
                    //alertDialog.setCancelable(false)
                    Toast.makeText(this, "입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }.setNegativeButton("취소") { _, _ ->
                Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
            }.show()
    }
     */

    //이과는 0부터 시작한다. id를 생성할때. 0을 안쓰긴해 ,,, 0
    /*
        putExtra한 값을 가지고,
        memo를 디비에서 조회해서,
        레이아웃에서 @+id/memo_detail_title의 TextView에 memo.title을 넣어준다
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDate() {
        val now = LocalDate.now()
        val date = now.format(DateTimeFormatter.ofPattern("yyyy年 MM月 dd日"))
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
    }
    /*
    @RequiresApi(Build.VERSION_CODES.M)
    private fun loadPromise() {
        if (intent.getIntExtra("id", 0) == 0) {
            return
        }
        val id = intent.getIntExtra("id", 0)
        val promise = AppDatabase.instance.promiseDao().get(id)
        if (promise.promise.isNotEmpty()) {
           // dialogView.dialog_edit.setText(promise.promise)
            bt_promise.foreground = resources.getDrawable(R.drawable.heart2)
        }
    }

     */

    //저장
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener() {
        bt_promise.setOnClickListener {
            //alertDialog.show()
            if(!mode) {
                edit_body2.setText(edit_body.text.toString())
                detail_none_promise.visibility = View.GONE
                detail_promise.visibility = View.VISIBLE
                bt_promise.foreground = resources.getDrawable(R.drawable.heart2)
                mode = true
            }
            else{
                edit_body.setText(edit_body2.text.toString())
                detail_none_promise.visibility = View.VISIBLE
                detail_promise.visibility = View.GONE
                bt_promise.foreground = resources.getDrawable(R.drawable.heart)
                mode = false
            }
        }

        bt_store.setOnClickListener {
            if(edit_body.text.toString().length >= edit_body2.text.toString().length)
                edit_body2.setText(edit_body.text.toString())
            else
                edit_body.setText(edit_body2.text.toString())

            val id = intent.getIntExtra("id", 0)
            val title = edit_title.text.toString()
            val contents = edit_body.text.toString()
            //val now = LocalDate.now()
            //val date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
            val promise = edit_promise.text.toString()
            val diary: Diary = Diary(id, date, title, contents, promise)
            AppDatabase.instance.diaryDao().insert(diary)
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