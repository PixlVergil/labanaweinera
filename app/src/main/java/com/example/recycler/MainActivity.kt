package com.example.recycler

import RecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentInfo = Intent(this, InfoScreen::class.java)
        val intentCrtChg = Intent(this, CrtChgScreen::class.java)
        val list = mutableListOf<DBElement>()
        val buttonCreate = findViewById<Button>(R.id.buttonCreate)
        val adapter = RecyclerAdapter(list) {
            startActivity(intentInfo)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val phoneDB = DBHelper(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonCreate.setOnClickListener {
            startActivity(intentCrtChg)
        }
    }
}