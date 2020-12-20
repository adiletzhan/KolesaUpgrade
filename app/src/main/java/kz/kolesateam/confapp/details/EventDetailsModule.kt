package kz.kolesateam.confapp.details

import org.koin.core.module.Module
import org.koin.dsl.module

val eventDetailsModule: Module = module {
    factory {
        EventDetailsRouter()
    }
}