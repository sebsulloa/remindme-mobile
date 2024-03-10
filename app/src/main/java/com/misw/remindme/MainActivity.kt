package com.misw.remindme

import com.misw.remindme.ui.navigation.RemindMeNavigation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.misw.remindme.ui.theme.RemindMeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemindMeApp()
        }
    }
}

@Composable
fun RemindMeApp() {
    RemindMeTheme {
        RemindMeNavigation()
    }
}
