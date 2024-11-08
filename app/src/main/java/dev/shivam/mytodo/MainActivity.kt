package dev.shivam.mytodo

import android.app.LauncherActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import dev.shivam.mytodo.auth.GoogleSignInHelper
import dev.shivam.mytodo.ui.theme.MyTodoTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var googleSignInHelper: GoogleSignInHelper

    val googleSignInLauncher = registerForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->

        if (result.resultCode == LauncherActivity.RESULT_OK) {
            Log.d(LOG_TAGS.AUTH, "Google sign in successful")
            googleSignInHelper.firebaseSignInWithIntent(result.data?: return@registerForActivityResult)
        } else {
            Log.d(LOG_TAGS.AUTH, "Google sign in failed with resultCode: ${result.resultCode}")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            MyTodoTheme {

            }
        }
    }
}



