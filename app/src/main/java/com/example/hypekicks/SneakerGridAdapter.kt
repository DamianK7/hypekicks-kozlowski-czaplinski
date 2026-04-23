package com.example.hypekicks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class SneakerGridAdapter(
    private val context: Context,
    private val sneakers: List<Sneaker>
) : BaseAdapter() {

    override fun getCount(): Int = sneakers.size

    override fun getItem(position: Int): Any = sneakers[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_sneaker, parent, false)

        val sneaker = sneakers[position]

        val image = view.findViewById<ImageView>(R.id.ivSneaker)
        val brand = view.findViewById<TextView>(R.id.tvBrand)
        val model = view.findViewById<TextView>(R.id.tvModel)

        brand.text = sneaker.brand
        model.text = sneaker.modelName

        Glide.with(context)
            .load(sneaker.imageUrl)
            .into(image)

        return view
    }
}