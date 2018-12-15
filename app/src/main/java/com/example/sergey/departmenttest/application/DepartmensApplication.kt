package com.example.sergey.departmenttest.application

import android.app.Application
import com.example.sergey.departmenttest.data.local.PreferenceManagerImpl
import com.example.sergey.departmenttest.data.remote.ServiceManagerImpl
import com.example.sergey.departmenttest.data.repository.AuthRepository
import com.example.sergey.departmenttest.data.repository.AuthRepositoryImpl
import com.example.sergey.departmenttest.data.repository.DepartmentsRepository
import com.example.sergey.departmenttest.data.repository.DepartmentsRepositoryImpl


interface DepartmentsApplication {
    val authRepository: AuthRepository
    val departmentsRepository: DepartmentsRepository
}

class DepartmentsApplicationImpl : Application(), DepartmentsApplication {

    private val serviceApi by lazy { ServiceManagerImpl().serviceApi }
    private val preferenceManager by lazy { PreferenceManagerImpl(applicationContext) }

    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(serviceApi, preferenceManager)
    }
    override val departmentsRepository: DepartmentsRepository by lazy {
        DepartmentsRepositoryImpl(serviceApi)
    }
}