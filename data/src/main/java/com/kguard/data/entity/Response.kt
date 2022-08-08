package com.kguard.data.entity

import com.kguard.data.entity.Body
import com.kguard.data.entity.Header

data class Response(
    val header: Header,
    val body: Body
)
