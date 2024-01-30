package ru.com.vbulat.decomposetest.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.com.vbulat.decomposetest.data.RepositoryImpl
import ru.com.vbulat.decomposetest.domain.Contact
import ru.com.vbulat.decomposetest.domain.GetContactsUseCase

class DefaultContactListComponent (
    val onEditingContactRequested : (Contact) -> Unit,
    val onAddContactRequested : () -> Unit,
) : ContactListComponent {

    private val repository = RepositoryImpl
    private val getContactsUseCase = GetContactsUseCase(repository)
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

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