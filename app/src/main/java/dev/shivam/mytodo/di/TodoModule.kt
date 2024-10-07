package dev.shivam.mytodo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.shivam.mytodo.data.TodoDatabase


@InstallIn(SingletonComponent::class)
@Module
class TodoModule {

    @Provides
    fun getTodoRoomDb(
        @ApplicationContext context : Context
    ) : TodoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TodoDatabase::class.java,
            name = "todoDb"
        ).build()
    }
}