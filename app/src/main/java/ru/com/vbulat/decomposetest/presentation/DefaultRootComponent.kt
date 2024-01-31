package ru.com.vbulat.decomposetest.presentation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import kotlinx.parcelize.Parcelize
import ru.com.vbulat.decomposetest.domain.Contact

class DefaultRootComponent (
    componentContext : ComponentContext
) : RootComponent, ComponentContext by componentContext {

    val navigation = StackNavigation<Config>()

    fun child(
        componentContext : ComponentContext,
        config : Config
    ) : ComponentContext {

        return when (config) {
            Config.AddContact -> {
                DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigation.pop()
                    },
                )
            }
            Config.ContactList -> {
                DefaultContactListComponent(
                    componentContext = componentContext,
                    onEditingContactRequested = {
                        navigation.push(Config.EditContact(contact = it))
                    },
                    onAddContactRequested = {
                        navigation.push(Config.AddContact)
                    },
                )
            }
            is Config.EditContact -> {
                DefaultEditContactComponent(
                    componentContext = componentContext,
                    config.contact,
                    onContactSaved = {
                        navigation.pop()
                    },
                )
            }
        }

    }

    sealed interface Config : Parcelable {

        @Parcelize
        object ContactList : Config

        @Parcelize
        object AddContact : Config

        @Parcelize
        data class EditContact (val contact : Contact) : Config
    }
}