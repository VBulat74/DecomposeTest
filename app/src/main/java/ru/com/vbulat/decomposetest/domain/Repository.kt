package ru.com.vbulat.decomposetest.domain

import kotlinx.coroutines.flow.Flow

interface Repository {

    val contacts : Flow<List<Contact>>

    fun saveContact(contact : Contact)
}
