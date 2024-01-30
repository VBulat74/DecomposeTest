package ru.com.vbulat.decomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.com.vbulat.decomposetest.core.componentScope
import ru.com.vbulat.decomposetest.data.RepositoryImpl
import ru.com.vbulat.decomposetest.domain.Contact
import ru.com.vbulat.decomposetest.domain.GetContactsUseCase

class DefaultContactListComponent(
    componentContext : ComponentContext,
    val onEditingContactRequested : (Contact) -> Unit,
    val onAddContactRequested : () -> Unit,
) : ContactListComponent, ComponentContext by componentContext {

    // private val viewmodel = instanceKeeper.getOrCreate { FareViewModel() }

    private val repository = RepositoryImpl
    private val getContactsUseCase = GetContactsUseCase(repository)
    private val coroutineScope = componentScope()

    override val model : StateFlow<ContactListComponent.Model> = getContactsUseCase()
        .map { contactList ->
            ContactListComponent.Model(contactList)
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = ContactListComponent.Model(listOf())
        )

    override fun onContactClicked(contact : Contact) {
        onEditingContactRequested(contact)
    }

    override fun onAddContactClicked() {
        onAddContactRequested()
    }
}

//private class FareViewModel() : InstanceKeeper.Instance {
//
//    override fun onDestroy() {
//        super.onDestroy()
//    }
//}