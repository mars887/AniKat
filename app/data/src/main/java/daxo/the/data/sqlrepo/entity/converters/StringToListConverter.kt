package daxo.the.data.sqlrepo.entity.converters

import androidx.room.TypeConverter

object StringToListConverter {

    @TypeConverter
    fun stringToList(string: String): List<String> = string.split("~!~")

    @TypeConverter
    fun listToString(list: List<String>): String = list.joinToString("~!~")
}