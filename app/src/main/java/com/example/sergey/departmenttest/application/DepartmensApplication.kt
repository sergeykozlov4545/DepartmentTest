package com.example.sergey.departmenttest.application

import android.app.Application
import com.example.sergey.departmenttest.data.local.PreferenceManagerImpl
import com.example.sergey.departmenttest.data.remote.ServiceManagerImpl
import com.example.sergey.departmenttest.data.repository.EmployeesRepositoryImpl
import com.example.sergey.departmenttest.domain.interactor.AuthorizeInteractor
import com.example.sergey.departmenttest.domain.interactor.AuthorizeInteractorImpl


interface DepartmentsApplication {
    val authorizeInteractor: AuthorizeInteractor
}

class DepartmentsApplicationImpl : Application(), DepartmentsApplication {

    private val serviceManager by lazy { ServiceManagerImpl() }
    private val preferenceManager by lazy { PreferenceManagerImpl(applicationContext) }
    private val employeesRepository by lazy {
        EmployeesRepositoryImpl(serviceManager.serviceApi, preferenceManager)
    }

    override val authorizeInteractor: AuthorizeInteractor
        get() = AuthorizeInteractorImpl(employeesRepository)

    override fun onCreate() {
        super.onCreate()
    }
}