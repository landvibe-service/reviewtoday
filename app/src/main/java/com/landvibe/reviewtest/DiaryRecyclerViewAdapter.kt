package com.landvibe.reviewtest

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.landvibe.reviewtest.diary.Diary
import kotlinx.android.synthetic.main.list_diary.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import com.landvibe.reviewtest.common.AppDatabase
import kotlinx.android.synthetic.main.activity_diary_detail.*
import kotlinx.android.synthetic.main.activity_diary_detail.view.*
import kotlinx.android.synthetic.main.dialog_promise.view.*

class DiaryRecyclerViewAdapter (
    var items: MutableList<Diary> = mutableListOf()
) : RecyclerView.Adapter<DiaryRecyclerViewAdapter.DiaryRecyclerViewHolder>(){

    class DiaryRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryRecyclerViewHolder {
        return DiaryRecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_diary,parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }
    //레이아웃 값 할당, 클릭할 시 어떤 일 할 지
    override fun onBindViewHolder(holder: DiaryRecyclerViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView){
            list_diary_text_title.text = item.title
            list_diary_text_date.text = item.date
            if(item.promise.isNotEmpty())
                list_diary_image.setImageResource(R.drawable.square2)
            holder.itemView.setOnClickListener {
                context.startActivity(Intent(context, DiaryDetailActivity::class.java).putExtra("id", item.id))
            }
            //길게 눌렀을때
            holder.itemView.setOnLongClickListener {
                androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("삭제하시겠습니까?")
                    .setMessage("삭제된 일기 및 다짐은 복구할 수 없습니다.")
                    .setPositiveButton("예") { _, _ ->
                        val diary = AppDatabase.instance.diaryDao().get(item.id)
                        AppDatabase.instance.diaryDao().delete(diary)
                        val diaryList = AppDatabase.instance.diaryDao().getAll()
                        items.clear()
                        items.addAll(diaryList)
                        notifyDataSetChanged()

                        Toast.makeText(context,"삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    }.setNegativeButton("아니오") { _, _ ->
                        Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show()
                    }.show()
                true
            }
        }
    }

}