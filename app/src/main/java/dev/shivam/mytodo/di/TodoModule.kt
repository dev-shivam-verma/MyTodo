package dev.shivam.mytodo.di

import android.content.Context
import androidx.room.Room
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.shivam.mytodo.auth.GoogleSignInHelper
import dev.shivam.mytodo.data.TodoDatabase
import javax.inject.Singleton


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

    @Provides
    fun getOneTapClient(
        @ActivityContext context: Context
    ) = Identity.getSignInClient(context)



    @Provides
    fun getFirebaseAuth(
    ) : FirebaseAuth {
        return Firebase.auth
    }
}