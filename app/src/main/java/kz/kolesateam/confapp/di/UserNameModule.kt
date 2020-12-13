package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.domain.UserNameDataSource
import kz.kolesateam.confapp.events.data.datasource.UserNameMemoryDataSource
import kz.kolesateam.confapp.events.data.datasource.UserNameSharedPrefsDataSource
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SHARED_PREFS_DATA_SOURCE = "shared_prefs_data_source"

val userNameModule : Module = module {
    factory{
        UserNameSharedPrefsDataSource(
                sharedPreferences = get()
        ) as UserNameDataSource
    }
}