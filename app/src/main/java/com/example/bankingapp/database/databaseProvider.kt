package com.example.bankingapp.database

import android.content.Context

object DatabaseProvider {
    private var instance: DBHandler? = null

    fun getInstance(context: Context): DBHandler {
        if (instance == null) {
            instance = DBHandler(context.applicationContext)
        }
        return instance!!
    }
}
