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

    companion object {
        const val EXTRA_KEY = "EXTRA"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val intentInfo = Intent(this, InfoScreen::class.java)
        val intentCrtChg = Intent(this, CrtChgScreen::class.java)
        val buttonCreate = findViewById<Button>(R.id.buttonCreate)

        list.addAll(phoneDB.getAll())

        val adapter = RecyclerAdapter(list) { id  ->
            intentInfo.putExtra(EXTRA_KEY, id)
            startActivityForResult(intentInfo, REQUEST_CODE)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonCreate.setOnClickListener {
            startActivityForResult(intentCrtChg, REQUEST_CODE2)
        }

    }

    private lateinit var adapter: RecyclerAdapter

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