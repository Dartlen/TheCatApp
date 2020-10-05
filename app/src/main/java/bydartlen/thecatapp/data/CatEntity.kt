package bydartlen.thecatapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
class CatEntity(
    @PrimaryKey val id: String,
    val url: String,
)
