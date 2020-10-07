package bydartlen.thecatapp.favorite

import bydartlen.thecatapp.base.AutoDisposable
import bydartlen.thecatapp.base.addTo
import bydartlen.thecatapp.base.withDefaultSchedulers
import bydartlen.thecatapp.data.CatRepository
import moxy.MvpPresenter
import javax.inject.Inject

class CatFavoritePresenter @Inject constructor(private val catRepository: CatRepository) :
    MvpPresenter<CatFavoriteView>() {

    val disposable: AutoDisposable by lazy { AutoDisposable() }

    fun load() {
        catRepository.getFavoriteList()
            .withDefaultSchedulers()
            .subscribe {
                viewState.addCats(it)
            }
            .addTo(disposable)
    }
}
