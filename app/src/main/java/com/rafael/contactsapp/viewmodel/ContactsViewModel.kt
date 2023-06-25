package com.rafael.contactsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import com.rafael.contactsapp.data.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repo: ContactsRepositoryImp) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _getContacts = MutableLiveData<UiState<List<Contacts>>>()
    private val _deleteContact = MutableLiveData<UiState<List<Contacts>>>()

    val getContacts: LiveData<UiState<List<Contacts>>>
        get() = _getContacts

    fun getContacts() {
        _getContacts.value = UiState.Loading
        compositeDisposable.add(
            repo.getAllResults()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    _getContacts.value = result
                }, { error ->
                    _getContacts.value = UiState.Failure(error.localizedMessage)
                })
        )
    }

    fun deleteContact(contact_id: Int) {
        _deleteContact.value = UiState.Loading
        compositeDisposable.add(
            repo.deleteContact(contact_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    _deleteContact.value = result
                    getContacts()
                }, { error ->
                    _deleteContact.value = UiState.Failure(error.localizedMessage)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
