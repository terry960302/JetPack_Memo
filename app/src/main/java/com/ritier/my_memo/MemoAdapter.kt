package com.ritier.my_memo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ritier.my_memo.Model.MemoModel

class MemoAdapter(val context: Context, val list: MutableList<MemoModel>) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_main_item, parent, false)
        return MemoViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail = itemView.findViewById<ImageView>(R.id.iv_thumbnail)
        val time = itemView.findViewById<TextView>(R.id.tv_time)
        val desc = itemView.findViewById<TextView>(R.id.tv_desc)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {

        holder.time.text = list[position].time
        holder.desc.text = list[position].desc

        if (list[position].thumbnailPath != null) {
            Glide.with(context).load(list[position].thumbnailPath).into(holder.thumbnail)
        } else {
            holder.thumbnail.setImageResource(R.drawable.ic_color_palette)
        }
    }
}