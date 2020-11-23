package com.gohar_amin.whishly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gohar_amin.whishly.R
import com.gohar_amin.whishly.callback.ActionCallback
import com.gohar_amin.whishly.utils.Utils

class CategoryAdapter(private  val context: Context,private val callback:ActionCallback): RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.category_list_item, parent, false);
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Utils.loadImage(context,"",holder.imageView)
        holder.root.setOnClickListener {
            callback.onSuccess()
        }
    }

    override fun getItemCount(): Int {
        return  6;
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var root: CardView
        init {
            imageView=itemView.findViewById(R.id.imagview)
            root=itemView.findViewById(R.id.root)
        }
    }

}