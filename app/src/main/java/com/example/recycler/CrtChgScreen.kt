package com.example.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CrtChgScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crt_chg_screen)

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)

        buttonCancel.setOnClickListener {
            finish()
        }
        buttonSave.setOnClickListener {
            finish()
        }
    }
}