package ru.com.vbulat.decomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.com.vbulat.decomposetest.core.componentScope
import ru.com.vbulat.decomposetest.domain.Contact

class DefaultContactListComponent(
    componentContext : ComponentContext,
    val onEditingContactRequested : (Contact) -> Unit,
    val onAddContactRequested : () -> Unit,
) : ContactListComponent, ComponentContext by componentContext {

    private lateinit var store : ContactListStore

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    ContactListStore.Label.AddContact -> {
                        onAddContactRequested()
                    }

                    is ContactListStore.Label.EditContact -> {
                        onEditingContactRequested(it.contact)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model : StateFlow<ContactListStore.State>
        get() = store.stateFlow

    override fun onContactClicked(contact : Contact) {
        store.accept(ContactListStore.Intent.SelectContact(contact))
    }

    override fun onAddContactClicked() {
        store.accept(ContactListStore.Intent.AddContact)
    }
}

//private class FareViewModel() : InstanceKeeper.Instance {
//
//    override fun onDestroy() {
//        super.onDestroy()
//    }
//}