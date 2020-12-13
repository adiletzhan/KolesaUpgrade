package kz.kolesateam.confapp.events.di

import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.allevents.data.repository.DefaultAllEventsRepository
import kz.kolesateam.confapp.allevents.di.BASE_URL
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.allevents.presentation.AllEventsViewModel
import kz.kolesateam.confapp.domain.UserNameDataSource
import kz.kolesateam.confapp.events.data.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.UpcomingEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

const val UPCOMING_EVENTS_VIEW_MODEL = "all_events_view_model"
const val UPCOMING_EVENTS_DATA_SOURCE = "upcoming_events_data_source"

val upcomingEventsScreenModule: Module = module {

    single(named(UPCOMING_EVENTS_DATA_SOURCE)) {
        val retrofit: Retrofit = get()
        retrofit.create(UpcomingEventsDataSource::class.java)
    }

    factory{
        DefaultUpcomingEventsRepository(
                upcomingEventsDataSource = get(named(UPCOMING_EVENTS_DATA_SOURCE)),
                userNameDataSource = get()
        ) as UpcomingEventsRepository
    }

    viewModel(named(UPCOMING_EVENTS_VIEW_MODEL)) {
        UpcomingEventsViewModel(
                upcomingEventsRepository = get(),
                favoriteEventsRepository = get()
        )
    }

}