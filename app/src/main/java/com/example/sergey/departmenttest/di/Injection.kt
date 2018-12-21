package com.example.sergey.departmenttest.di

import android.app.Application
import com.example.sergey.departmenttest.BuildConfig
import com.example.sergey.departmenttest.data.local.PreferenceManager
import com.example.sergey.departmenttest.data.local.PreferenceManagerImpl
import com.example.sergey.departmenttest.data.remote.ServiceFactory
import com.example.sergey.departmenttest.data.repository.AuthRepository
import com.example.sergey.departmenttest.data.repository.AuthRepositoryImpl
import com.example.sergey.departmenttest.data.repository.DepartmentsRepository
import com.example.sergey.departmenttest.data.repository.DepartmentsRepositoryImpl
import com.example.sergey.departmenttest.feature.departmentList.DepartmentListPresenter
import com.example.sergey.departmenttest.feature.departmentList.DepartmentListPresenterImpl
import com.example.sergey.departmenttest.feature.departmentList.DepartmentListView
import com.example.sergey.departmenttest.feature.employeeDetails.DetailsPresenter
import com.example.sergey.departmenttest.feature.employeeDetails.DetailsPresenterImpl
import com.example.sergey.departmenttest.feature.employeeDetails.DetailsView
import com.example.sergey.departmenttest.feature.login.LoginPresenter
import com.example.sergey.departmenttest.feature.login.LoginPresenterImpl
import com.example.sergey.departmenttest.feature.login.LoginView
import com.example.sergey.departmenttest.feature.logout.LogoutPresenter
import com.example.sergey.departmenttest.feature.logout.LogoutPresenterImpl
import com.example.sergey.departmenttest.feature.logout.LogoutView
import com.example.sergey.departmenttest.feature.splash.SplashPresenter
import com.example.sergey.departmenttest.feature.splash.SplashPresenterImpl
import com.example.sergey.departmenttest.feature.splash.SplashView
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.dsl.context.ModuleDefinition
import org.koin.dsl.module.module
import org.koin.log.EmptyLogger
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext

object Injection : KoinComponent {

    private val rootModule = module {
        single<PreferenceManager> { PreferenceManagerImpl(androidContext()) }
        single { ServiceFactory.create() }

        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
        single<DepartmentsRepository> { DepartmentsRepositoryImpl(get()) }
    }

    private val featureModule = module("feature") {
        authFeatures()
        departmentsFeature()
    }

    private fun ModuleDefinition.authFeatures() {
        factory<SplashPresenter> { (view: SplashView) -> SplashPresenterImpl(view, get(), get()) }
        factory<LoginPresenter> { (view: LoginView) -> LoginPresenterImpl(view, get(), get()) }
        factory<LogoutPresenter> { (view: LogoutView) -> LogoutPresenterImpl(view, get(), get()) }
    }

    private fun ModuleDefinition.departmentsFeature() {
        factory<DepartmentListPresenter> { (view: DepartmentListView) ->
            DepartmentListPresenterImpl(view, get())
        }
        factory<DetailsPresenter> { (view: DetailsView) -> DetailsPresenterImpl(view, get()) }
    }

    fun start(application: Application) {
        val logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger()
        StandAloneContext.stopKoin()
        application.startKoin(application, listOf(rootModule, featureModule), logger = logger)
    }
}