package com.landvibe.reviewtest

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.landvibe.reviewtest.diary.Diary
import kotlinx.android.synthetic.main.list_diary.view.*
import android.widget.TextView

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
            holder.itemView.setOnClickListener {
                context.startActivity(Intent(context, DiaryDetailActivity::class.java).putExtra("id", item.id))
            }
            //길게 눌렀을때
            holder.itemView.setOnLongClickListener {
                //showSettingPopup()
                true
            }
        }
    }

}