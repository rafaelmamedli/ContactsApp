package com.rafael.contactsapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contacts(
    @SerializedName("kisi_id") var contact_id: Int,
    @SerializedName("kisi_ad") var contact_name: String,
    @SerializedName("kisi_tel") var contact_number: String
) : Parcelable {
}