package bydartlen.thecatapp.catlist

import bydartlen.thecatapp.data.network.Cat
import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface CatListView : MvpView {
    @Skip
    fun addCats(list: List<Cat>)
}