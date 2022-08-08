package com.kguard.domain.repository

import android.location.Location

interface LocationRepository {
    suspend fun getLocation(): Location?
}