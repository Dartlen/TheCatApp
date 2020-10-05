package bydartlen.thecatapp.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bydartlen.thecatapp.R
import bydartlen.thecatapp.catlist.CatsAdapter
import bydartlen.thecatapp.data.Cat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cat_favorite.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class CatFavoriteFragment : MvpAppCompatFragment(), CatFavoriteView {

    @Inject
    lateinit var presenterProvider: Provider<CatFavoritePresenter>

    private val presenter: CatFavoritePresenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_cat_favorite, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.load()

        recyclerFavorite.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL,
            false
        )
        recyclerFavorite.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.HORIZONTAL
            )
        )
        recyclerFavorite.adapter = CatsAdapter({ cat: Cat -> },{})
    }

    override fun addCats(list: List<Cat>) {
        (recyclerFavorite.adapter as CatsAdapter).currentList.addAll(list)
        (recyclerFavorite.adapter as CatsAdapter).notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        presenter.disposable.dispose()
    }
}