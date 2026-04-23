package com.example.hypekicks

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val db = Firebase.firestore
        val testData = hashMapOf("status" to "Działa!")

        db.collection("testy").add(testData)
            .addOnSuccessListener {
                Log.d("HypeKicks", "SUKCES: Dane w chmurze!")
            }
            .addOnFailureListener { e ->
                Log.w("HypeKicks", "BŁĄD: Coś nie pykło", e)
            }

    }

}
