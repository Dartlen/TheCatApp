package bydartlen.thecatapp.catlist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bydartlen.thecatapp.R
import bydartlen.thecatapp.data.Cat
import bydartlen.thecatapp.favorite.CatFavoriteFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cat_list.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class CatListFragment : MvpAppCompatFragment(), CatListView {

    @Inject
    lateinit var presenterProvider: Provider<CatListPresenter>

    private val presenter: CatListPresenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_cat_list, container, false);
    }

    private val lastVisibleItemPosition: Int
        get() = (recycler.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.load(1)

        recycler.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL,
            false
        )
        recycler.addItemDecoration(DividerItemDecoration(activity, HORIZONTAL))
        recycler.adapter =
            CatsAdapter({ cat: Cat -> presenter.onCatLiked(cat) }, { url ->
                if (verifyPermissions()) {
                    presenter.downloadImage(url)
                }
            })

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    presenter.load((recycler.adapter as CatsAdapter).itemCount.div(20L))
                }
            }
        }
        recycler.addOnScrollListener(scrollListener)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.container,
                CatFavoriteFragment()
            )?.commit()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun addCats(list: List<Cat>) {
        (recycler.adapter as CatsAdapter).currentList.addAll(list)
        (recycler.adapter as CatsAdapter).notifyDataSetChanged()
    }

    private fun verifyPermissions(): Boolean {
        val permissionExternalMemory =
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
            return false
        }
        return true
    }

    override fun onStop() {
        super.onStop()
        presenter.disposable.dispose()
    }
}