package com.example.hypekicks

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.Intent
import com.example.hypekicks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore

    private val sneakerList = mutableListOf<Sneaker>()
    private lateinit var adapter: SneakerGridAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGoToAdmin.setOnClickListener {
            val intent = Intent(this, AdminPanelActivity::class.java)
            startActivity(intent)
        }

        adapter = SneakerGridAdapter(this, sneakerList)
        binding.gridView.adapter = adapter

        loadSneakers()
    }

    private fun loadSneakers() {
        db.collection("sneakers")
            .get()
            .addOnSuccessListener { result ->

                sneakerList.clear()

                for (doc in result) {
                    val sneaker = doc.toObject(Sneaker::class.java)
                    sneaker.id = doc.id
                    sneakerList.add(sneaker)
                }

                adapter.notifyDataSetChanged()
            }
    }
}
