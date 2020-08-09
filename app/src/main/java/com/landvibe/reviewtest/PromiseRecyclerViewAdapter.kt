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
import com.landvibe.reviewtest.promise.Promise
import kotlinx.android.synthetic.main.activity_diary_detail.*
import kotlinx.android.synthetic.main.activity_diary_detail.view.*
import kotlinx.android.synthetic.main.dialog_promise.view.*
import kotlinx.android.synthetic.main.list_promise.view.*
import java.text.SimpleDateFormat
import java.util.*

class PromiseRecyclerViewAdapter (
    var items: MutableList<Promise> = mutableListOf()
) : RecyclerView.Adapter<PromiseRecyclerViewAdapter.PromiseRecyclerViewHolder>(){

    class PromiseRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val sec : Long = 60
    private val min : Long = 60
    private val hour: Long= 24
    //val day = 30

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromiseRecyclerViewHolder {
        return PromiseRecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_promise,parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }
    //레이아웃 값 할당, 클릭할 시 어떤 일 할 지
    override fun onBindViewHolder(holder: PromiseRecyclerViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView){
            val time = Date(item.date)
            val mFormat = SimpleDateFormat("yyyy年 MM月 dd日")
            val date: String = mFormat.format(time)
            list_promise_text.text = item.promise
            list_promise_text_date.text = date

            val current = System.currentTimeMillis()
            var gap = (current - item.date)/1000
            var pDay = "1日"

            if(gap < sec)
                pDay = "1日"
            else {
                gap /= sec
                if(gap < min)
                    pDay = "1日"
                else {
                    gap /= min
                    if(gap < hour)
                        pDay = "1日"
                    else{
                        gap /= hour
                        pDay = gap.toString()+"日"
                    }
                }
            }
            list_promise_date_gap.text = pDay

            holder.itemView.setOnClickListener {
                context.startActivity(Intent(context, DiaryDetailActivity::class.java).putExtra("id", item.id))
            }
        }
    }

}