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


    override fun onBindViewHolder(holder: PromiseRecyclerViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView){
            list_promise_text_date.text = item.date

            list_promise_text.text = item.promise

            val todayCal : Calendar = Calendar.getInstance()
            val today : Long = todayCal.timeInMillis/86400000

            val count = today - item.time + 1

            val pDay = count.toString() + "日"
            list_promise_date_gap.text = pDay
        }
    }

}