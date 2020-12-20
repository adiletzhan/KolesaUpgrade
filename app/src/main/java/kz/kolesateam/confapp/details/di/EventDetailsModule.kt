package kz.kolesateam.confapp.details.di

import kz.kolesateam.confapp.details.EventDetailsRouter
import kz.kolesateam.confapp.details.data.DefaultEventDetailsRepository
import kz.kolesateam.confapp.details.data.EventDetailsDataSource
import kz.kolesateam.confapp.details.domain.EventDetailsRepository
import kz.kolesateam.confapp.details.presentation.EventDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val EVENT_DETAILS_VIEW_MODEL = "event_details_view_model"

val eventDetailsModule: Module = module {
    factory {
        EventDetailsRouter()
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(EventDetailsDataSource::class.java)
    }

    factory {
        DefaultEventDetailsRepository(eventDetailsDataSource = get()) as EventDetailsRepository
    }

    viewModel(named(EVENT_DETAILS_VIEW_MODEL)) {
        EventDetailsViewModel(
                eventDetailsRepository = get()
        )
    }
}