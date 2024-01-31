package ru.com.vbulat.decomposetest.presentation

import kotlinx.coroutines.flow.StateFlow
import ru.com.vbulat.decomposetest.domain.Contact

interface ContactListComponent {

    val model : StateFlow<ContactListStore.State>

    fun onContactClicked(contact : Contact)

    fun onAddContactClicked()
}