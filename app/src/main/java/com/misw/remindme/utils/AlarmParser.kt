package com.misw.remindme.utils

import android.content.Context
import androidx.annotation.RawRes
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class AlarmItem(
    val name: String,
    val time: String,
    val location: String,
    val category: String
)

object AlarmParser {
    fun parseJson(context: Context, @RawRes resourceId: Int): List<AlarmItem> {
        val json = context.resources.openRawResource(resourceId)
            .bufferedReader().use { it.readText() }
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, AlarmItem::class.java)
        val adapter: JsonAdapter<List<AlarmItem>> = moshi.adapter(listType)
        return adapter.fromJson(json) ?: emptyList()
    }
}
