package com.android.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String = ""
): Parcelable