package com.rafael.contactsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.repository.ContactsRepository
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import com.rafael.contactsapp.data.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val repo: ContactsRepositoryImp) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _addContacts = MutableLiveData<UiState<String>>()

    val addContacts: LiveData<UiState<String>>
        get() = _addContacts

    fun addContact(contact_name: String, contact_number: String) {
        _addContacts.value = UiState.Loading
        val disposable = repo.addContact(contact_name, contact_number)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                _addContacts.value = result
            }, { error ->
                _addContacts.value = UiState.Failure(error.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

