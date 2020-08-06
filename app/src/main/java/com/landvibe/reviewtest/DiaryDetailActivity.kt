package com.landvibe.reviewtest

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.landvibe.reviewtest.common.AppDatabase
import com.landvibe.reviewtest.diary.Diary
import com.landvibe.reviewtest.promise.Promise
import kotlinx.android.synthetic.main.activity_diary_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_promise.*
import kotlinx.android.synthetic.main.dialog_promise.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DiaryDetailActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    val now: LocalDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val date: String = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)

        loadDate()
        loadDiary()
        loadPromise()
        setupToolbarWrite()
        setupListener()
        setupAlertDialog()
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
    private fun loadPromise() {
        if (intent.getIntExtra("id", 0) == 0) {
            return
        }
        val id  = intent.getIntExtra("id", 0)
        val promise = AppDatabase.instance.promiseDao().get(id)
        if(promise.promise.isNotEmpty()){
            dialog_edit.setText(promise.promise)
            bt_promise.setBackgroundResource(R.drawable.star2)
        }
    }
    /*
    //뒤로가기
    private fun backListener(){
        bt_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", 0)
            startActivity(intent)
        }
    }
    //삭제
    private fun deleteListener(){
        bt_delete.setOnClickListener {
            val id = intent.getIntExtra("id", 0)
            val diary = AppDatabase.instance.diaryDao().get(id)
            AppDatabase.instance.diaryDao().delete(diary)
            finish()
        }
    }
    */

    //저장
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener(){
        bt_store.setOnClickListener {
            val id  = intent.getIntExtra("id", 0)
            val title = edit_title.text.toString()
            val contents = edit_body.text.toString()
            //val now = LocalDate.now()
            //val date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
            val diary :Diary = Diary(id, date, title, contents)
            AppDatabase.instance.diaryDao().insert(diary)
            finish()
        }
    }

    //툴바
    private fun setupToolbarWrite(){
        setSupportActionBar(main_write_toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_write_top, menu)
        return true
        //return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            R.id.menu_write_delete->{
                val id = intent.getIntExtra("id", 0)
                val diary = AppDatabase.instance.diaryDao().get(id)
                AppDatabase.instance.diaryDao().delete(diary)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setupAlertDialog(){
        bt_promise.setOnClickListener {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_promise, null)
            AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("확인") { _, _ ->
                    val id  = intent.getIntExtra("id", 0)
                    val content: String = dialog_edit.text.toString()
                    val promise = Promise(id, date, content)
                    AppDatabase.instance.promiseDao().insert(promise)

                    bt_promise.setBackgroundResource(R.drawable.star2)
                    Toast.makeText(this,view.dialog_edit.text.toString(), Toast.LENGTH_SHORT).show()
                }.setNegativeButton("취소") { _, _ ->
                    Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
                }.show()
        }
    }
}

//application - activity(화면) - fragment(화면) - view

