package bydartlen.thecatapp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AutoDisposable {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}