package ru.com.vbulat.decomposetest.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.com.vbulat.decomposetest.data.RepositoryImpl
import ru.com.vbulat.decomposetest.domain.AddContactUseCase

class DefaultAddContactComponent : AddContactComponent {

    private val repository = RepositoryImpl
    private val addContactUseCase = AddContactUseCase(repository)

    private val _model = MutableStateFlow(
        AddContactComponent.Model(username = "", phone = "")
    )

    override val model : StateFlow<AddContactComponent.Model>
        get() = _model.asStateFlow()

    override fun onUsernameChanged(username : String) {
        _model.value = model.value.copy(username = username)
    }

    override fun onPhoneChanged(phone : String) {
        _model.value = model.value.copy(phone = phone)
    }

    override fun onSaveContactClicked() {
        val (username, phone) = model.value
        addContactUseCase(
            username = username,
            phone = phone
        )
    }
}