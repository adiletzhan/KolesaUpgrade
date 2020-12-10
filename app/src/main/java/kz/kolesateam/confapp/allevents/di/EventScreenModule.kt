package kz.kolesateam.confapp.allevents.di


import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.allevents.data.repository.DefaultAllEventsRepository
import kz.kolesateam.confapp.allevents.presentation.AllEventsViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

const val BASE_URL = "http://37.143.8.68:2020"
const val ALL_EVENTS_VIEW_MODEL = "all_events_view_model"

val eventScreenModule: Module = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    single() {
        val retrofit: Retrofit = get()

        retrofit.create(AllEventsDataSource::class.java)
    }

    factory{
        DefaultAllEventsRepository(allEventsDataSource = get())
    }

    viewModel(named(ALL_EVENTS_VIEW_MODEL)) {
        AllEventsViewModel(
            allEventsRepository = get()
        )
    }

}