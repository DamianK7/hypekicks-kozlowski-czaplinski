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
    private val filteredList = mutableListOf<Sneaker>()
    private lateinit var adapter: SneakerGridAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGoToAdmin.setOnClickListener {
            val intent = Intent(this, AdminPanelActivity::class.java)
            startActivity(intent)
        }

        adapter = SneakerGridAdapter(this, filteredList)
        binding.gridView.adapter = adapter

        loadSneakers()

        setupSearch()

        binding.gridView.setOnItemClickListener { _, _, position, _ ->

            val sneaker = filteredList[position]

            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("brand", sneaker.brand)
            intent.putExtra("model", sneaker.modelName)
            intent.putExtra("price", sneaker.resellPrice)
            intent.putExtra("image", sneaker.imageUrl)

            startActivity(intent)

        }
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

                filteredList.clear()
                filteredList.addAll(sneakerList)

                adapter.notifyDataSetChanged()
            }
    }

    private fun setupSearch() {

        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {

                val text = newText!!.lowercase()

                filteredList.clear()

                filteredList.addAll(
                    sneakerList.filter {
                        it.modelName.lowercase().contains(text) ||
                                it.brand.lowercase().contains(text)
                    }
                )

                adapter.notifyDataSetChanged()
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
    }
}
