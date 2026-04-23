package com.example.hypekicks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SneakerAdapter (
    private val context: Context,
    private val sneakers: List<Sneaker>
) : BaseAdapter() {
    override fun getCount(): Int = sneakers.size

    override fun getItem(position: Int): Any = sneakers[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_sneaker_admin, parent, false)

        val sneaker = sneakers[position]

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvDetails = view.findViewById<TextView>(R.id.tvDetails)

        tvName.text = "${sneaker.brand} ${sneaker.modelName}"
        tvDetails.text = "Rok: ${sneaker.releaseYear} | ${sneaker.resellPrice} PLN"

        return view
    }
}