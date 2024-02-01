package ru.com.vbulat.decomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.com.vbulat.decomposetest.domain.Contact
import ru.com.vbulat.decomposetest.domain.EditContactUseCase

class EditContactStoreFactory(
    storeFactory : StoreFactory,
    private val editContactUseCase : EditContactUseCase
) {

    private val store : Store<EditContactStore.Intent, EditContactStore.State, EditContactStore.Label> =
        storeFactory.create(
            name = "EditContactStore",
            initialState = EditContactStore.State(username = "", phone = ""),
            reducer = ReducerImpl,
            executorFactory = :: ExecutorImpl
        )

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUsername(val username : String) : Msg

        data class ChangePhone(val phone : String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<
            EditContactStore.Intent,
            Action,
            EditContactStore.State,
            Msg,
            EditContactStore.Label
            >(){
        override fun executeIntent(
            intent : EditContactStore.Intent,
            getState : () -> EditContactStore.State
        ) {
            when (intent) {
                is EditContactStore.Intent.ChangePhone -> {
                    dispatch(Msg.ChangePhone(phone = intent.phone))
                }
                is EditContactStore.Intent.ChangeUsername -> {
                    dispatch(Msg.ChangeUsername(username = intent.username))
                }
                EditContactStore.Intent.SaveContact -> {
                    val state = getState()
                    val contact = Contact(username = state.username, phone = state.phone)
                    editContactUseCase(contact = contact)
                    publish(EditContactStore.Label.ContactSaved)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<EditContactStore.State, Msg> {
        override fun EditContactStore.State.reduce(msg : Msg) = when (msg) {
            is Msg.ChangePhone -> {
                copy(phone = msg.phone)
            }

            is Msg.ChangeUsername -> {
                copy(username = msg.username)
            }
        }

    }

}