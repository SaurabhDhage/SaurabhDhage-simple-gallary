package com.gallary.viewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RcViewAdaptor(val items: MutableList<Image>,val listener:RCViewListener) :RecyclerView.Adapter<ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.items_post,parent,false)
        val viewHolder=ViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentItem=items[position]

       // holder.imageThumbnail.setImageBitmap(currentItem.thumbnail)
        Picasso.get()
            .load(currentItem.uri)
            .resize(200, 200)
            .centerCrop()
            .into(holder.imageThumbnail)

    }

    override fun getItemCount(): Int {
    return items.size
    }

}
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{

    val imageThumbnail=itemView.findViewById<ImageView>(R.id.thumbnail)

}
interface RCViewListener
{
    fun onItemClicked(item: Image)

}