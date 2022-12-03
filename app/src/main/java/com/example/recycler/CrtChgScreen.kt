package com.example.recycler

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrtChgScreen : AppCompatActivity() {
    private val phoneDB = DBHelper(this)
    companion object {
        val REQUEST_CODE2 = 2
        const val RESULT_KEY2 = "result"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crt_chg_screen)

        val id = intent.getLongExtra(InfoScreen.EXTRA_KEY2,0)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)
        val name = findViewById<EditText>(R.id.editTextName)
        val surname = findViewById<EditText>(R.id.editTextSurname)
        val date = findViewById<EditText>(R.id.editTextDate)
        val phone = findViewById<EditText>(R.id.editTextPhone)
        val item = phoneDB.getById(id)

        name.setText(item?.name)
        surname.setText(item?.surname)
        date.setText(item?.birthday)
        phone.setText(item?.phone)

        buttonCancel.setOnClickListener {
            finish()
        }

        buttonSave.setOnClickListener {
            if(id!=0L){
                phoneDB.update(id,
                    name.text.toString(),
                    surname.text.toString(),
                    phone.text.toString(),
                    date.text.toString())
            }
            else{
                phoneDB.add(name.text.toString(),
                    surname.text.toString(),
                    phone.text.toString(),
                    date.text.toString())
            }

            val returnIntent = Intent()
            returnIntent.putExtra(RESULT_KEY2, id)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}