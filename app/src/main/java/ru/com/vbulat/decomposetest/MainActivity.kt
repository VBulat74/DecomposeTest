package ru.com.vbulat.decomposetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import ru.com.vbulat.decomposetest.presentation.DefaultRootComponent
import ru.com.vbulat.decomposetest.ui.contenet.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        val componentContext = defaultComponentContext()
        setContent {
            RootContent(component = DefaultRootComponent(componentContext))
        }
    }
}