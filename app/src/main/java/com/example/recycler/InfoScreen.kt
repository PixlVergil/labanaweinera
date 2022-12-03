package com.example.recycler

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.recycler.CrtChgScreen.Companion.REQUEST_CODE2

class InfoScreen : AppCompatActivity() {

    private val phoneDB = DBHelper(this)

    companion object {
        val REQUEST_CODE = 1
        const val EXTRA_KEY2 = "EXTRA"
        const val RESULT_KEY = "result"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_screen)

        val id = intent.getLongExtra(MainActivity.EXTRA_KEY, 0)
        val intentCrtChg = Intent(this, CrtChgScreen::class.java)
        val name = findViewById<TextView>(R.id.nameView)
        val surname = findViewById<TextView>(R.id.surnameView)
        val date = findViewById<TextView>(R.id.dateView)
        val phone = findViewById<TextView>(R.id.phoneView)
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        val buttonChange = findViewById<Button>(R.id.buttonChange)
        val buttonDelete = findViewById<Button>(R.id.buttonDel)

        val item = phoneDB.getById(id)
        name.text = item?.name
        surname.text = item?.surname
        date.text = item?.birthday
        phone.text = item?.phone

        buttonChange.setOnClickListener {
            val intent = Intent(this, CrtChgScreen::class.java)
            intent.putExtra(EXTRA_KEY2, id)
            startActivityForResult(intent, REQUEST_CODE2)
        }

        buttonDelete.setOnClickListener {
            item?.id?.let { it1 -> phoneDB.remove(it1) }
            val returnIntent = Intent()
            returnIntent.putExtra(RESULT_KEY, id)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        buttonBack.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra(RESULT_KEY,  id)
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE2 && resultCode == Activity.RESULT_OK) {
            val id = data?.getLongExtra(CrtChgScreen.RESULT_KEY2 ,0)
            val name = findViewById<TextView>(R.id.nameView)
            val surname = findViewById<TextView>(R.id.surnameView)
            val date = findViewById<TextView>(R.id.dateView)
            val phone = findViewById<TextView>(R.id.phoneView)
            val item = id?.let { phoneDB.getById(it) }
            name.text = item?.name
            surname.text = item?.surname
            date.text = item?.birthday
            phone.text = item?.phone
        }
    }

}