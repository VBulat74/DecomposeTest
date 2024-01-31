package ru.com.vbulat.decomposetest.ui.contenet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import ru.com.vbulat.decomposetest.presentation.DefaultRootComponent
import ru.com.vbulat.decomposetest.presentation.RootComponent
import ru.com.vbulat.decomposetest.ui.theme.DecomposeTestTheme

@Composable
fun RootContent(
    component : DefaultRootComponent
) {
    DecomposeTestTheme {
        Box (
            modifier = Modifier
                .fillMaxSize()
        ){
            Children(
                stack = component.stack,

            ) {
                when(val instance = it.instance){

                    is RootComponent.Child.AddContact -> {
                        AddContact(component = instance.component)
                    }
                    is RootComponent.Child.ContactList -> {
                        Contacts(component = instance.component)
                    }
                    is RootComponent.Child.EditContact -> {
                        EditContact(component = instance.component)
                    }
                }
            }
        }
    }
}