package com.fred.app.data.datasource.base

import com.fred.app.data.datasource.entity.UserDTO
import com.fred.app.util.State

interface RegisterDataSource {

    suspend fun register(
        userId: String,
        username: String?,
        name: String?,
        phone: String?,
        mail: String?,
        address: String?,
        gender: Boolean?,
    ): State<UserDTO>
}