package bydartlen.thecatapp.data

import bydartlen.thecatapp.catlist.adapter.HEART
import bydartlen.thecatapp.data.db.CatDatabase
import bydartlen.thecatapp.data.db.CatEntity
import bydartlen.thecatapp.data.network.Cat
import bydartlen.thecatapp.data.network.CatEndPoint
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val endPoint: CatEndPoint,
    private val catDB: CatDatabase
) {

    fun getCatList(page: Long): Single<List<Cat>> =
        endPoint.getCats(20, page)

    fun catLike(cat: Cat): Completable {
        return catDB.catDao.insert(CatEntity(cat.id, cat.url))
    }

    fun getFavoriteList(): Maybe<List<Cat>> {
        return catDB.catDao.getAll()
            .map { it.map { Cat(heart = HEART.FAVORITE, url = it.url, id = it.id) } }
    }
}