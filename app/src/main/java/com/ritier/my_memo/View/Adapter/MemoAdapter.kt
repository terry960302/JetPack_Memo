package com.ritier.my_memo.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ritier.my_memo.Model.MemoModel
import com.ritier.my_memo.R
import com.ritier.my_memo.Util.GlidePlaceHolder
import com.ritier.my_memo.Util.getBitmapFromBinary
import com.ritier.my_memo.View.Interface.OnListItemClickListener

class MemoAdapter(val context: Context) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    private val TAG = "MemoAdapter"
    private var list = mutableListOf<MemoModel>()


    lateinit var onListItemClickListener: OnListItemClickListener

    inner class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        val cv_id = itemView.findViewById<CardView>(R.id.cv_id)
        val tv_id = itemView.findViewById<TextView>(R.id.tv_id)
        val cv_thumbnail = itemView.findViewById<CardView>(R.id.cv_thumbnail)
        val iv_thumbnail = itemView.findViewById<ImageView>(R.id.iv_thumbnail)
        val tv_title = itemView.findViewById<TextView>(R.id.tv_app_bar)
        val tv_desc = itemView.findViewById<TextView>(R.id.tv_desc)

        override fun onClick(v: View?) {
            onListItemClickListener.onItemClick(itemView, adapterPosition)
        }
    }


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

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {

        val id = list[position].id
        val thumbnailList = list[position].thumbPathList
        val title = list[position].title
        val desc = list[position].desc

        //아이디
        holder.tv_id.text = id.toString()

        //제목
        holder.tv_title.text = title

        //내용
        holder.tv_desc.text = desc

        //썸네일
        if (thumbnailList != null && thumbnailList.isNotEmpty()) {
            val firstImage = thumbnailList[0]

            Glide.with(context).load(
                if (URLUtil.isValidUrl(firstImage)) firstImage else getBitmapFromBinary(
                    firstImage!!
                )
            )
                .placeholder(GlidePlaceHolder.circularPlaceHolder(context))
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(holder.iv_thumbnail)
            holder.cv_thumbnail.visibility = View.VISIBLE
        } else {
            holder.cv_thumbnail.visibility = View.GONE
        }
    }

    fun setOnMemoClickListener(onListItemClickListener: OnListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener
    }

    fun setData(memoList: MutableList<MemoModel>) {
        this.list = memoList
        notifyDataSetChanged()
        Log.d(TAG, "메모장이 업데이트 되었습니다.")
    }

    fun getData(index: Int): MemoModel = this.list[index]
}