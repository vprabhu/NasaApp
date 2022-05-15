package com.obvious.nasaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.obviouc.network.model.NasaItem
import com.obvious.nasaapp.R

class NasaListAdapter(
    private val list: List<NasaItem>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<NasaListAdapter.ItemViewHolder>() {


    class OnClickListener(val clickListener: (meme: NasaItem, position: Int) -> Unit) {
        fun onClick(meme: NasaItem, position: Int) = clickListener(meme, position)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AppCompatImageView = view.findViewById(R.id.imageView_nasa_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_nasa_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        bind(list[position], position, holder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun bind(nasa: NasaItem, position: Int, holder: ItemViewHolder) {
        val url = nasa.url
        holder.imageView.load(url)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(nasa, position)
        }
    }

}