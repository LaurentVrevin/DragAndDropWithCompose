package com.laurentvrevin.draganddropwithcompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.laurentvrevin.draganddropwithcompose.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(tasks: Task)

    @Update
    suspend fun updateTasksList(tasks: List<Task>)

    @Delete
    suspend fun deleteTask(task: Task)
}