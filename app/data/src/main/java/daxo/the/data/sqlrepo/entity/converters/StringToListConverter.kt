package daxo.the.data.sqlrepo.entity.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters

class StringToListConverter {

    @TypeConverter
    fun stringToList(string: String): List<String> = string.split("~:~")

    @TypeConverter
    fun ListToString(list: List<String>): String = list.joinToString("~:~")
}