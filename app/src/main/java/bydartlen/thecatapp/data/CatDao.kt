package bydartlen.thecatapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cat: CatEntity): Completable

    @Query("SELECT * FROM cat")
    fun getAll(): Maybe<List<CatEntity>>
}