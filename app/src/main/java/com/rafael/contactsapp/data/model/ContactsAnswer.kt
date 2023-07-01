package com.rafael.contactsapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContactsAnswer(
    @SerializedName("kisiler") var contacts: List<Contacts>,
    @SerializedName("success") var success: Int
) : Serializable {
}