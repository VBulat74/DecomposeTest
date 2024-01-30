package ru.com.vbulat.decomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.consume
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.com.vbulat.decomposetest.data.RepositoryImpl
import ru.com.vbulat.decomposetest.domain.AddContactUseCase

class DefaultAddContactComponent (
    componentContext : ComponentContext
) : AddContactComponent, ComponentContext by componentContext {

    private val repository = RepositoryImpl
    private val addContactUseCase = AddContactUseCase(repository)

    init {
        stateKeeper.register(KEY) {
            model.value
        }
    }

    private val _model = MutableStateFlow(
        stateKeeper.consume(KEY) ?: AddContactComponent.Model(username = "", phone = "")
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

    companion object{
        private const val KEY = "DefaultAddContactComponent"
    }
}