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

    private val _getcontacts = MutableLiveData<UiState<List<Contacts>>>()
    val _deleteContact = MutableLiveData<UiState<List<Contacts>>>()




    init {
        getContacts()
    }
    val getContacts: LiveData<UiState<List<Contacts>>>
        get() = _getcontacts

    val deleteContact: LiveData<UiState<List<Contacts>>>
        get() = _deleteContact



    fun getContacts() = viewModelScope.launch {
        _getcontacts.value = UiState.Loading
        repo.getAllResults() { result ->
            _getcontacts.value = result }
    }

    fun deleteContact(contact_id: Int) = viewModelScope.launch{
        _deleteContact.value = UiState.Loading
        repo.deleteContact(contact_id) { result ->
            _deleteContact.value = result

        }
    }
}