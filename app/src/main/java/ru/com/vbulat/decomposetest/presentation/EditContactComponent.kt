package ru.com.vbulat.decomposetest.presentation

import kotlinx.coroutines.flow.StateFlow

interface EditContactComponent {

    val model : StateFlow<EditContactStore.State>

    fun onUsernameChanged(username : String)

    fun onPhoneChanged(phone : String)

    fun onSaveContactClicked()
}