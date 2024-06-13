package com.laurentvrevin.draganddropwithcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.laurentvrevin.draganddropwithcompose.domain.model.Task

@Database(entities = [Task::class], version =1)
abstract class TaskDataBase:RoomDatabase(){
    abstract fun taskDao():TaskDao
}