package com.laurentvrevin.draganddropwithcompose.di

import android.content.Context
import androidx.room.Room
import com.laurentvrevin.draganddropwithcompose.data.local.TaskDao
import com.laurentvrevin.draganddropwithcompose.data.local.TaskDataBase
import com.laurentvrevin.draganddropwithcompose.data.repository.TaskRepositoryImpl
import com.laurentvrevin.draganddropwithcompose.domain.repository.TaskRepository
import com.laurentvrevin.draganddropwithcompose.domain.usecase.AddTaskUseCase
import com.laurentvrevin.draganddropwithcompose.domain.usecase.DeleteTaskUseCase
import com.laurentvrevin.draganddropwithcompose.domain.usecase.GetTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDataBase(@ApplicationContext appContext: Context): TaskDataBase {
        return Room.databaseBuilder(
            appContext,
            TaskDataBase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(taskDataBase: TaskDataBase) = taskDataBase.taskDao()

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository{
        return TaskRepositoryImpl(taskDao)
    }
    @Provides
    @Singleton
    fun provideGetTasksUseCase(repository: TaskRepository): GetTaskUseCase {
        // Fournir l'instance du cas d'utilisation GetTasksUseCase
        return GetTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(repository: TaskRepository): AddTaskUseCase {
        // Fournir l'instance du cas d'utilisation AddTaskUseCase
        return AddTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase {
        // Fournir l'instance du cas d'utilisation DeleteTaskUseCase
        return DeleteTaskUseCase(repository)
    }
}