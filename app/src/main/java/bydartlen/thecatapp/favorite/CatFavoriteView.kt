package bydartlen.thecatapp.favorite

import bydartlen.thecatapp.data.Cat
import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface CatFavoriteView : MvpView {
    @Skip
    fun addCats(list: List<Cat>)
}