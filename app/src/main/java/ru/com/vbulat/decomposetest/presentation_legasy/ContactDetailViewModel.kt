package ru.com.vbulat.decomposetest.presentation_legasy

import androidx.lifecycle.ViewModel
import ru.com.vbulat.decomposetest.data.RepositoryImpl
import ru.com.vbulat.decomposetest.domain.AddContactUseCase
import ru.com.vbulat.decomposetest.domain.Contact
import ru.com.vbulat.decomposetest.domain.EditContactUseCase

class ContactDetailViewModel : ViewModel() {

    private val repository = RepositoryImpl

    private val addContactUseCase = AddContactUseCase(repository)
    private val editContactUseCase = EditContactUseCase(repository)

    fun addContact(username: String, phone: String) {
        addContactUseCase(username, phone)
    }

    fun editContact(contact: Contact) {
        editContactUseCase(contact)
    }
}