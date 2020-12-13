package kz.kolesateam.confapp.favorite_events.data

import android.app.Application
import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.MapType
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
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

    init {
        val favoriteEventsFromFile = getFavoriteEventsFromFile()
        favoriteEvents.putAll(favoriteEventsFromFile)
    }

    override fun saveFavoriteEvent(eventApiData: EventApiData) {
        eventApiData.id ?: return
        favoriteEvents[eventApiData.id] = eventApiData

        saveFavoriteEventsToFile()
    }

    override fun removeFavoriteEvent(eventId: Int?) {
        favoriteEvents.remove(eventId)
        saveFavoriteEventsToFile()
    }

    override fun getAllFavoriteEvents(): List<EventApiData> {
        return favoriteEvents.values.toList()
    }

    override fun isFavorite(id: Int): Boolean = favoriteEvents.containsKey(id)

    private fun saveFavoriteEventsToFile(){
        val favoriteEventsJsonString: String = objectMapper.writeValueAsString(favoriteEvents)
        val fileOutputStream : FileOutputStream = context.openFileOutput(
            FAVORITE_EVENTS_FILE_NAME,
            Context.MODE_PRIVATE)
        fileOutputStream.write(favoriteEventsJsonString.toByteArray())
        fileOutputStream.close()
    }

    private fun getFavoriteEventsFromFile(): Map<Int, EventApiData> {
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = context.openFileInput(FAVORITE_EVENTS_FILE_NAME)
        }catch (exception: Exception) {
            fileInputStream?.close()
            return emptyMap()
        }
        val favoriteEventsJsonString: String =
                fileInputStream?.bufferedReader()?.readLines()?.joinToString().orEmpty()

        val mapType: MapType = objectMapper.typeFactory.constructMapType(
            Map::class.java,
            Int::class.java,
            EventApiData::class.java
        )
        return objectMapper.readValue(favoriteEventsJsonString, mapType)
    }

    private fun prepareUpcomingEventsList(
        upcomingEvents: List<UpcomingEventsListItem>
    ){
        upcomingEvents.forEach{
            val upcomingEvent: EventApiData = it.data as? EventApiData ?: return@forEach

        }
    }
}