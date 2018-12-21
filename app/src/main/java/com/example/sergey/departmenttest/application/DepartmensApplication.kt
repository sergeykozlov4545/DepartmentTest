package com.example.sergey.departmenttest.application

import android.app.Application
import com.example.sergey.departmenttest.di.Injection

class DepartmentsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injection.start(this)
    }
}