package com.geektech.taskmanager

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geektech.taskmanager.data.local.room.AppDataBase
import com.geektech.taskmanager.data.local.room.TaskDao
import com.geektech.taskmanager.model.Task

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "database-name"
        ).allowMainThreadQueries().build()
    }
    companion object{
        lateinit var db: AppDataBase

    }
}