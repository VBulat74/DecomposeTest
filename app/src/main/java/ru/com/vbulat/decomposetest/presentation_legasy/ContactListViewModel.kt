package ru.com.vbulat.decomposetest.presentation_legasy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.com.vbulat.decomposetest.data.RepositoryImpl
import ru.com.vbulat.decomposetest.domain.GetContactsUseCase

class ContactListViewModel : ViewModel() {

    private val repository = RepositoryImpl

    private val getContactsUseCase = GetContactsUseCase(repository)

    val contacts = getContactsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )
}