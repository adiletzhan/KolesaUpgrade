package kz.kolesateam.confapp.favorite_events.data

import android.app.Application
import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.ResponseData
import java.io.FileInputStream
import java.io.FileOutputStream

private const val FAVORITE_EVENTS_FILE_NAME = "favorite_events.json"

class DefaultFavoriteEventsRepository(
    private val context: Context,
    private val objectMapper: ObjectMapper
): FavoriteEventsRepository {

    private val favoriteEvents: MutableMap<Int, EventApiData> = mutableMapOf()

    override fun saveFavoriteEvents(eventApiData: EventApiData) {
        eventApiData.id ?: return
        favoriteEvents[eventApiData.id] = eventApiData

        saveFavoriteEventsToFile()
    }

    override fun removeFavoriteEvents(eventId: Int?) {
        favoriteEvents?.remove(eventId)
        saveFavoriteEventsToFile()
    }

    override fun getAllFavoriteEvents(): ResponseData<List<EventApiData>, Exception> {
        return ResponseData.Success(
                result = favoriteEvents.values.toList()
        )
    }

    private fun saveFavoriteEventsToFile(){
        val favoriteEventsJsonString: String = objectMapper.writeValueAsString(favoriteEvents)
        val fileOutputStream : FileOutputStream = context.openFileOutput(
            FAVORITE_EVENTS_FILE_NAME,
            Context.MODE_PRIVATE)
        fileOutputStream.write(favoriteEventsJsonString.toByteArray())
        fileOutputStream.close()
    }

    /*
    private fun getFavoriteEventsToFile(): Map<Int, EventApiData>{
        val fileInputStream: FileInputStream = context.openFileInput(FAVORITE_EVENTS_FILE_NAME)

        val favoriteEventsJsonString: String = fileInputStream.bufferedReader().readLines().joinToString()
        //val favoriteEventsMapper: Map<Int, EventApiData> = objectMapper.re
    }

     */
}