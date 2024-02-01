package ru.com.vbulat.decomposetest.presentation

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.com.vbulat.decomposetest.data.RepositoryImpl
import ru.com.vbulat.decomposetest.domain.AddContactUseCase

class AddContactStoreFactory {

    private val storeFactory : StoreFactory = LoggingStoreFactory(DefaultStoreFactory())
    private val repository = RepositoryImpl
    private val addContactUseCase : AddContactUseCase = AddContactUseCase(repository)

    private val store : Store<AddContactStore.Intent, AddContactStore.State, AddContactStore.Label> =
        storeFactory.create(
            name = "AddContactStore",
            initialState = AddContactStore.State(username = "", phone = ""),
            reducer = ReduserImpl,
            executorFactory = ::ExecutorImpl
        )

    fun create () : AddContactStore = object : AddContactStore,
        Store<AddContactStore.Intent, AddContactStore.State, AddContactStore.Label> by store {}.apply {
            Log.d("AAA", "CREATE AddContactStore")
    }

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUsername (val username : String) : Msg

        data class ChangePhone (val phone : String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<
            AddContactStore.Intent,
            Action,
            AddContactStore.State,
            Msg,
            AddContactStore.Label
            >() {

        override fun executeIntent(
            intent : AddContactStore.Intent,
            getState : () -> AddContactStore.State
        ) {
            when (intent) {
                is AddContactStore.Intent.ChangePhone -> {
                    dispatch(Msg.ChangePhone(phone = intent.phone))
                }
                is AddContactStore.Intent.ChangeUsername -> {
                    dispatch(Msg.ChangeUsername(username = intent.username))
                }
                AddContactStore.Intent.SaveContact -> {
                    val state = getState()
                    addContactUseCase(username = state.username, phone = state.phone)
                    publish(AddContactStore.Label.ContactSaved)
                }
            }
        }
    }

    private object ReduserImpl : Reducer<AddContactStore.State, Msg> {
        override fun AddContactStore.State.reduce(msg : Msg) = when(msg) {
            is Msg.ChangePhone -> {
                copy(phone = msg.phone)
            }
            is Msg.ChangeUsername -> {
                copy(username = msg.username)
            }
        }

    }

}