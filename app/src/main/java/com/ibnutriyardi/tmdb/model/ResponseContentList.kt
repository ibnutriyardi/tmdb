package com.ibnutriyardi.tmdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseContentList(
    @SerializedName("results") val data: List<ResponseContent>
) : Parcelable
