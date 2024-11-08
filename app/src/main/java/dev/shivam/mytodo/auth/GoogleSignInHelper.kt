package dev.shivam.mytodo.auth

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.credentials.provider.utils.BeginGetCredentialUtil
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dev.shivam.mytodo.BuildConfig
import dev.shivam.mytodo.LOG_TAGS
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleSignInHelper @Inject constructor(
    val oneTapClient: SignInClient,
    val auth: FirebaseAuth
) {
    suspend fun beginSignIn(): PendingIntent {
        val result = oneTapClient.beginSignIn(SignInRequestBuilder.getSignInOption()).await()
        return result.pendingIntent
    }

    fun firebaseSignInWithIntent(intent: Intent) {
        val googleCredential = oneTapClient.getSignInCredentialFromIntent(intent)
        val idToken = googleCredential.googleIdToken
        if (idToken != null) {

            // Got an ID token from Google. Use it to authenticate with Firebase.
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

            auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(LOG_TAGS.AUTH, "signInWithCredential:success")
                        val user = auth.currentUser
                        // Update UI with user information if needed
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(LOG_TAGS.AUTH, "signInWithCredential:failure", task.exception)
                        // Handle failure if needed
                    }
                }

        } else {
            // Shouldn't happen.
            Log.d(LOG_TAGS.AUTH, "No Google ID token!")
        }
    }
}

object SignInRequestBuilder {
    fun getSignInOption(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}

