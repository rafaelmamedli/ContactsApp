package com.rafael.contactsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import com.rafael.contactsapp.data.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repo: ContactsRepositoryImp) : ViewModel() {


    init {
    }

    private val _getContacts = MutableLiveData<UiState<List<Contacts>>>()
    private val _deleteContact = MutableLiveData<UiState<List<Contacts>>>()


    val getContacts: LiveData<UiState<List<Contacts>>>
        get() = _getContacts


    fun getContacts() = viewModelScope.launch(Dispatchers.Main) {
        _getContacts.value = UiState.Loading
        repo.getAllResults() { result ->
                _getContacts.value = result }
    }

    fun deleteContact(contact_id: Int) = viewModelScope.launch(Dispatchers.Main){
        _deleteContact.value = UiState.Loading
        repo.deleteContact(contact_id) { result ->
            _deleteContact.value = result

        }
        getContacts()
    }
}