package com.example.hypekicks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hypekicks.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val brand = intent.getStringExtra("brand") ?: ""
        val model = intent.getStringExtra("model") ?: ""
        val image = intent.getStringExtra("image") ?: ""
        val price = intent.getIntExtra("price", 0)

        binding.tvTitle.text = "$brand $model"
        binding.tvPrice.text = "$price PLN"

        Glide.with(this)
            .load(image ?: "")
            .into(binding.ivDetail)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}