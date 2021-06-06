package com.android.core.utils

import androidx.room.TypeConverter
import com.android.core.data.source.local.entity.SourceEntity

class SourceTypeConverter {
    @TypeConverter
    fun fromSource(source: SourceEntity): String {
        return source.name
    }

    @TypeConverter
    fun toSource(source: String): SourceEntity {
        return SourceEntity(source, source)
    }
}