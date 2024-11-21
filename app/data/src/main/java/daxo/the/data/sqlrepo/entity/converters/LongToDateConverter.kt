package daxo.the.data.sqlrepo.entity.converters

import androidx.room.TypeConverter
import java.util.Date

object LongToDateConverter {

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timestamp: Long): Date = Date(timestamp)
}