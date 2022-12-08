package com.example.recycler

import RecyclerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler.InfoScreen.Companion.REQUEST_CODE
import com.example.recycler.CrtChgScreen.Companion.REQUEST_CODE2


class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<DBElement>()
    private val phoneDB = DBHelper(this)
    private lateinit var adapter: RecyclerAdapter
    companion object {
        const val EXTRA_KEY = "EXTRA"
        const val REQUEST_CODE4 = 5
        const val REQUEST_CODE3 = 666
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCreate = findViewById<Button>(R.id.buttonCreate)

        list.addAll(phoneDB.getAll())

        val adapter = RecyclerAdapter(list) { id ->
            val intentInfo = Intent(this, InfoScreen::class.java)
            intentInfo.putExtra(EXTRA_KEY, id)
            startActivityForResult(intentInfo, REQUEST_CODE3)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonCreate.setOnClickListener {
            val intentCrtChg = Intent(this, CrtChgScreen::class.java)
            startActivityForResult(intentCrtChg, REQUEST_CODE4)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val id = data?.getLongExtra(InfoScreen.RESULT_KEY, 0)
            val index = list.indexOfFirst { it.id == id }
            list.removeAt(index)
            adapter.notifyItemRemoved(index)
        }
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
            val id = data?.getLongExtra(InfoScreen.RESULT_KEY, 0)
            val index = list.indexOfFirst { it.id == id }
            val item = id?.let { phoneDB.getById(it) }
            if (item != null) {
                list[index] = item
            }
            adapter.notifyDataSetChanged()
        }
        if (requestCode == REQUEST_CODE2 && resultCode == Activity.RESULT_OK) {
            val id = data?.getLongExtra(CrtChgScreen.RESULT_KEY2, 0)
            val index = list.indexOfFirst { it.id == id }
            val item = id?.let { phoneDB.getById(it) }
            if (item != null) {
                list[index] = item
            }
            adapter.notifyDataSetChanged()
        }
    }
}