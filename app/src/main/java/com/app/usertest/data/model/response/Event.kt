package com.app.prodiatest.data.model.response


import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("event_id")
    val eventId: Int,
    @SerializedName("provider")
    val provider: String
)