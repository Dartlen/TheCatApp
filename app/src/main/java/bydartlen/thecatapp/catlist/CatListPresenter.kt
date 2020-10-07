package bydartlen.thecatapp.catlist

import bydartlen.thecatapp.base.AutoDisposable
import bydartlen.thecatapp.base.addTo
import bydartlen.thecatapp.base.withDefaultSchedulers
import bydartlen.thecatapp.catlist.adapter.HEART
import bydartlen.thecatapp.data.CatRepository
import bydartlen.thecatapp.data.FileRepository
import bydartlen.thecatapp.data.network.Cat
import moxy.MvpPresenter
import javax.inject.Inject

class CatListPresenter @Inject constructor(
    private val catRepository: CatRepository,
    private val fileRepository: FileRepository
) :
    MvpPresenter<CatListView>() {

    val disposable: AutoDisposable by lazy { AutoDisposable() }

    fun load(page: Long) {
        catRepository.getCatList(page)
            .withDefaultSchedulers()
            .subscribe { t1, t2 ->
                viewState.addCats(t1.map { it.copy(heart = HEART.UNLIKE) })
            }
            .addTo(disposable)
    }

    fun onCatLiked(cat: Cat) {
        catRepository.catLike(cat)
            .withDefaultSchedulers()
            .subscribe {
            }.addTo(disposable)
    }

    fun downloadImage(url: String) {
        fileRepository.downloadImage(url)
    }
}