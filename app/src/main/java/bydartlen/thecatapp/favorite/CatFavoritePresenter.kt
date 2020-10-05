package bydartlen.thecatapp.favorite

import bydartlen.thecatapp.catlist.withDefaultSchedulers
import bydartlen.thecatapp.data.CatRepository
import moxy.MvpPresenter
import javax.inject.Inject

class CatFavoritePresenter @Inject constructor(private val catRepository: CatRepository) :
    MvpPresenter<CatFavoriteView>() {

    fun load() {
        catRepository.getFavoriteList()
            .withDefaultSchedulers()
            .subscribe {
                viewState.addCats(it)
            }
    }
}
