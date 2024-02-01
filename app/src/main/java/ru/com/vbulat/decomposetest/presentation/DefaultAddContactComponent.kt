package ru.com.vbulat.decomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.com.vbulat.decomposetest.core.componentScope

class DefaultAddContactComponent(
    componentContext : ComponentContext,
    private val onContactSaved : () -> Unit,
) : AddContactComponent, ComponentContext by componentContext {

    private var store : AddContactStore = instanceKeeper.getStore {
        val storeFactory = AddContactStoreFactory()
        storeFactory.create()
    }


    init {
        componentScope().launch{
            store.labels.collect {
                when (it) {
                    AddContactStore.Label.ContactSaved -> {onContactSaved()}
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model : StateFlow<AddContactStore.State>
        get() = store.stateFlow

    override fun onUsernameChanged(username : String) {
        store.accept(AddContactStore.Intent.ChangeUsername(username))
    }

    override fun onPhoneChanged(phone : String) {
        store.accept(AddContactStore.Intent.ChangePhone(phone))
    }

    override fun onSaveContactClicked() {
        store.accept(AddContactStore.Intent.SaveContact)
    }
}