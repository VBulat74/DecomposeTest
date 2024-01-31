package ru.com.vbulat.decomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory

class EditContactStoreFactory(
    storeFactory : StoreFactory,
) {

    private val store : Store<EditContactStore.Intent, EditContactStore.State, EditContactStore.Label> =
        storeFactory.create(
            name = "AddContactStore",
            initialState = EditContactStore.State(username = "", phone = ""),

        )

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUsername (val username : String) : Msg

        data class ChangePhone (val phone : String) : Msg
    }

    private object ReduserImpl : Reducer<EditContactStore.State, Msg> {
        override fun EditContactStore.State.reduce(msg : Msg) = when(msg) {
            is Msg.ChangePhone -> {
                copy(phone = msg.phone)
            }
            is Msg.ChangeUsername -> {
                copy(username = msg.username)
            }
        }

    }

}