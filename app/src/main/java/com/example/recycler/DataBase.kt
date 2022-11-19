package com.example.recycler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "phonebook"
        const val TABLE_NAME = "contacts"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_SURNAME = "surname"
        const val KEY_PHONE = "phone"
        const val KEY_BIRTH = "birthday"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_NAME TEXT NOT NULL,
                $KEY_SURNAME TEXT NOT NULL,
                $KEY_PHONE TEXT NOT NULL,
                $KEY_BIRTH TEXT NOT NULL
            )""")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAll(): List<DBElement> {
        val result = mutableListOf<DBElement>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val nameIndex: Int = cursor.getColumnIndex(KEY_NAME)
            val surnameIndex: Int = cursor.getColumnIndex(KEY_SURNAME)
            val phoneIndex: Int = cursor.getColumnIndex(KEY_PHONE)
            val birthIndex: Int = cursor.getColumnIndex(KEY_BIRTH)
            do {
                val todo = DBElement(
                    cursor.getLong(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(surnameIndex),
                    cursor.getString(phoneIndex),
                    cursor.getString(birthIndex),
                )
                result.add(todo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun getById(id: Long): DBElement? {
        var result: DBElement? = null
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, "$KEY_ID = ?", arrayOf(id.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val nameIndex: Int = cursor.getColumnIndex(KEY_NAME)
            val surnameIndex: Int = cursor.getColumnIndex(KEY_SURNAME)
            val phoneIndex: Int = cursor.getColumnIndex(KEY_PHONE)
            val birthIndex: Int = cursor.getColumnIndex(KEY_BIRTH)
            result = DBElement(
                cursor.getLong(idIndex),
                cursor.getString(nameIndex),
                cursor.getString(surnameIndex),
                cursor.getString(phoneIndex),
                cursor.getString(birthIndex),
            )
        }
        cursor.close()
        return result
    }

    fun add(name: String, surname: String, phone: String, birthday: String): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_SURNAME, surname)
        contentValues.put(KEY_PHONE, phone)
        contentValues.put(KEY_BIRTH, birthday)
        val id = database.insert(TABLE_NAME, null, contentValues)
        close()
        return id
    }

    fun update(id: Long, name: String, surname: String, phone: String, birthday: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_SURNAME, surname)
        contentValues.put(KEY_PHONE, phone)
        contentValues.put(KEY_BIRTH, birthday)
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun remove(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun removeAll() {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, null, null)
        close()
    }
}