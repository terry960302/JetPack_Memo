package com.ritier.my_memo.UI.Adapter

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ritier.my_memo.R

class ImageAdapter(val context: Context) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    val imageList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = imageList.size

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_image = itemView.findViewById<ImageView>(R.id.iv_image)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri= imageList[position]

        Glide.with(context)
            .load(imageUri)
            .error(R.drawable.broken)
            .into(holder.iv_image)

        holder.itemView.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("이미지 삭제").setMessage("정말로 삭제하시겠습니까?")
            dialogBuilder.setPositiveButton("네") { dialog, i ->
                deleteImage(position)
                dialog.cancel()
            }.setNegativeButton("아니오") { dialog, i ->
                dialog.cancel()
            }
            val alertDialog = dialogBuilder.create()
            alertDialog.show()
        }
    }

    fun setImageData(imagePath : String){
        imageList.add(imagePath)
        notifyDataSetChanged()
    }

    fun deleteImage(i : Int){
        imageList.removeAt(i)
        notifyDataSetChanged()
        Toast.makeText(context, "이미지가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }

    fun getImages() : MutableList<String>{
        return imageList
    }
}