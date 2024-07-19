package com.app.prodiatest.data.model.response


import com.google.gson.annotations.SerializedName

data class Launche(
    @SerializedName("launch_id")
    val launchId: String,
    @SerializedName("provider")
    val provider: String
)