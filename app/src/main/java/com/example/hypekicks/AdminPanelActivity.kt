package com.example.hypekicks

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hypekicks.databinding.ActivityAdminPanelBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminPanelBinding

    private val db = Firebase.firestore

    private val sneakerList = mutableListOf<Sneaker>()
    private lateinit var adapter: SneakerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SneakerAdapter(this, sneakerList)
        binding.lvSneakers.adapter = adapter

        binding.btnAddToBase.setOnClickListener {
            saveSneakerToDatabase()
        }

        binding.lvSneakers.setOnItemLongClickListener { _, _, position, _ ->
            val sneaker = sneakerList[position]

            db.collection("sneakers")
                .document(sneaker.id)
                .delete()
                .addOnSuccessListener {
                    loadSneakers()
                }

            true
        }

        loadSneakers()

    }

    private fun loadSneakers() {
        db.collection("sneakers")
            .addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener
                sneakerList.clear()

                for (document in value!!) {
                    val sneaker = document.toObject(Sneaker::class.java)
                    sneaker.id = document.id
                    sneakerList.add(sneaker)
                }

                adapter.notifyDataSetChanged()
            }
    }


    private fun saveSneakerToDatabase() {

        val brand = binding.etBrand.text.toString().trim()
        val modelName = binding.etModelName.text.toString().trim()
        val releaseYearStr = binding.etReleaseYear.text.toString().trim()
        val resellPriceStr = binding.etResellPrice.text.toString().trim()
        val imageUrl = binding.etImageUrl.text.toString().trim()


        if (brand.isEmpty() || modelName.isEmpty() || releaseYearStr.isEmpty() || resellPriceStr.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show()
            return
        }

        val sneaker = Sneaker(
            id = "",
            brand = brand,
            modelName = modelName,
            releaseYear = releaseYearStr.toInt(),
            resellPrice = resellPriceStr.toInt(),
            imageUrl = imageUrl
        )

        db.collection("sneakers")
            .add(sneaker)
            .addOnSuccessListener {
                Toast.makeText(this, "Buty dodane do magazynu!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Błąd zapisu: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }


    private fun clearFields() {
        binding.etBrand.text.clear()
        binding.etModelName.text.clear()
        binding.etReleaseYear.text.clear()
        binding.etResellPrice.text.clear()
        binding.etImageUrl.text.clear()

    }
}