package bydartlen.thecatapp.data

import com.google.gson.annotations.SerializedName
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

data class Cat(
    @SerializedName("breeds") val breeds: List<Breed> = listOf(),
    @SerializedName("height") val height: Int = 0,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int = 0,
    var heart: HEART = HEART.UNLIKE
)

data class Weight(

    @SerializedName("imperial") val imperial: String,
    @SerializedName("metric") val metric: String
)

data class Breed(

    @SerializedName("adaptability") val adaptability: Int,
    @SerializedName("affection_level") val affection_level: Int,
    @SerializedName("alt_names") val alt_names: String,
    @SerializedName("child_friendly") val child_friendly: Int,
    @SerializedName("country_code") val country_code: String,
    @SerializedName("country_codes") val country_codes: String,
    @SerializedName("description") val description: String,
    @SerializedName("dog_friendly") val dog_friendly: Int,
    @SerializedName("energy_level") val energy_level: Int,
    @SerializedName("experimental") val experimental: Int,
    @SerializedName("grooming") val grooming: Int,
    @SerializedName("hairless") val hairless: Int,
    @SerializedName("health_issues") val health_issues: Int,
    @SerializedName("hypoallergenic") val hypoallergenic: Int,
    @SerializedName("id") val id: String,
    @SerializedName("indoor") val indoor: Int,
    @SerializedName("intelligence") val intelligence: Int,
    @SerializedName("life_span") val life_span: String,
    @SerializedName("name") val name: String,
    @SerializedName("natural") val natural: Int,
    @SerializedName("origin") val origin: String,
    @SerializedName("rare") val rare: Int,
    @SerializedName("rex") val rex: Int,
    @SerializedName("shedding_level") val shedding_level: Int,
    @SerializedName("short_legs") val short_legs: Int,
    @SerializedName("social_needs") val social_needs: Int,
    @SerializedName("stranger_friendly") val stranger_friendly: Int,
    @SerializedName("suppressed_tail") val suppressed_tail: Int,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("vcahospitals_url") val vcahospitals_url: String,
    @SerializedName("vocalisation") val vocalisation: Int,
    @SerializedName("weight") val weight: Weight,
    @SerializedName("wikipedia_url") val wikipedia_url: String
)