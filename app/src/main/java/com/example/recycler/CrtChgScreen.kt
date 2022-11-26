package com.example.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrtChgScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crt_chg_screen)

        val phoneDB = DBHelper(this)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)
        val name = findViewById<EditText>(R.id.editTextName)
        val surname = findViewById<EditText>(R.id.editTextSurname)
        val date = findViewById<EditText>(R.id.editTextDate)
        val phone = findViewById<EditText>(R.id.editTextPhone)

        buttonCancel.setOnClickListener {
            finish()
        }
        buttonSave.setOnClickListener {
            val id = phoneDB.add(name.text.toString(), surname.text.toString(), phone.text.toString(), date.text.toString())
            finish()
        }
    }
}