package com.example.bankingapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHandler(context: Context?): SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USER_NAME TEXT, " +
                "$COLUMN_USER_EMAIL TEXT, " +
                "$COLUMN_USER_BALANCE REAL)")
        val createTransferTable = ("CREATE TABLE $TABLE_TRANSFERS (" +
                "$COLUMN_TRANSFER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_SENDER_ID INTEGER, " +
                "$COLUMN_RECEIVER_ID INTEGER, " +
                "$COLUMN_TRANSFER_AMOUNT REAL)")

        db?.execSQL(createUserTable)
        db?.execSQL(createTransferTable)

        insertDummyData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade as needed
    }

    private fun insertDummyData(db: SQLiteDatabase?) {
        val users = listOf(
            User(0, "Soonha Soomro (developer)", "soonha@email.com", 9000.0),
            User(0, "Rabia", "rabia@email.com", 1500.0),
            User(0, "John Jalbani", "JJ@email.com", 2000.0),
            User(0, "Zakia", "zakia@email.com", 2500.0),
            User(0, "Sana", "sana@email.com", 3000.0),
            User(0, "Iqra", "iqra@email.com", 3500.0),
            User(0, "Yashma", "yashma@email.com", 4000.0),
            User(0, "Ahmed", "ahmed@email.com", 4500.0),
            User(0, "Hina", "hina@email.com", 5000.0),
            User(0, "Narmeen", "narmeen@email.com", 5500.0)
        )

        users.forEach {
            val values = ContentValues().apply {
                put(COLUMN_USER_NAME, it.name)
                put(COLUMN_USER_EMAIL, it.email)
                put(COLUMN_USER_BALANCE, it.balance)
            }
            db?.insert(TABLE_USERS, null, values)
        }
    }

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS", null)

        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    balance = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_USER_BALANCE))
                )
                users.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }

    fun getUserById(id: Int, closeDb: Boolean = true): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COLUMN_USER_ID = ?", arrayOf(id.toString()))
        var user: User? = null

        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                balance = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_USER_BALANCE))
            )
        }
        cursor.close()
        if (closeDb) {
            db.close()
        }
        return user
    }

    fun recordTransfer(transfer: Transfer): Boolean {
        val db = this.writableDatabase
        var success = false
        db.beginTransaction()
        try {
            val sender = getUserById(transfer.senderId, closeDb = false)
            val receiver = getUserById(transfer.receiverId, closeDb = false)

            if (sender != null && receiver != null) {
                if (sender.balance >= transfer.amount) {
                    // Deduct amount from sender's balance
                    val updatedSenderBalance = sender.balance - transfer.amount
                    val senderValues = ContentValues().apply {
                        put(COLUMN_USER_BALANCE, updatedSenderBalance)
                    }
                    db.update(TABLE_USERS, senderValues, "$COLUMN_USER_ID = ?", arrayOf(sender.id.toString()))

                    // Add amount to receiver's balance
                    val updatedReceiverBalance = receiver.balance + transfer.amount
                    val receiverValues = ContentValues().apply {
                        put(COLUMN_USER_BALANCE, updatedReceiverBalance)
                    }
                    db.update(TABLE_USERS, receiverValues, "$COLUMN_USER_ID = ?", arrayOf(receiver.id.toString()))

                    // Insert the transfer record into the Transfers table
                    val transferValues = ContentValues().apply {
                        put(COLUMN_SENDER_ID, transfer.senderId)
                        put(COLUMN_RECEIVER_ID, transfer.receiverId)
                        put(COLUMN_TRANSFER_AMOUNT, transfer.amount)
                    }
                    db.insert(TABLE_TRANSFERS, null, transferValues)

                    db.setTransactionSuccessful()
                    success = true
                } else {
                    Log.e("DBHandler", "Sender has insufficient balance.")
                }
            } else {
                Log.e("DBHandler", "Invalid sender or receiver.")
            }
        } catch (e: Exception) {
            Log.e("DBHandler", "Transfer error: ${e.message}")
        } finally {
            db.endTransaction()
            db.close()
        }
        return success
    }

    companion object {
        private const val DB_NAME = "BankingApp.db"
        private const val VERSION = 1

        private const val TABLE_USERS = "Users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USER_NAME = "name"
        private const val COLUMN_USER_EMAIL = "email"
        private const val COLUMN_USER_BALANCE = "balance"

        private const val TABLE_TRANSFERS = "Transfers"
        private const val COLUMN_TRANSFER_ID = "id"
        private const val COLUMN_SENDER_ID = "senderId"
        private const val COLUMN_RECEIVER_ID = "receiverId"
        private const val COLUMN_TRANSFER_AMOUNT = "amount"
    }
}
