package com.example.recycler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InfoScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_screen)

        val intentCrtChg = Intent(this, CrtChgScreen::class.java)
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        val buttonChange = findViewById<Button>(R.id.buttonChange)
        val buttonDelete = findViewById<Button>(R.id.buttonDel)

        buttonChange.setOnClickListener {
            startActivity(intentCrtChg)
        }
        buttonDelete.setOnClickListener {
            finish()
        }
        buttonBack.setOnClickListener {
            finish()
        }
    }
}