package com.obviouc.network.model

import java.util.*
import kotlin.collections.ArrayList

data class NasaItem(
    val copyright: String,
    val date: Date,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)