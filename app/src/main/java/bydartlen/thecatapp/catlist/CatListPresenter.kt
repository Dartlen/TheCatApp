package bydartlen.thecatapp.catlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import bydartlen.thecatapp.R
import bydartlen.thecatapp.data.Cat
import bydartlen.thecatapp.data.CatRepository
import bydartlen.thecatapp.data.FileRepository
import bydartlen.thecatapp.data.HEART
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.cat_item.view.*
import moxy.MvpPresenter
import javax.inject.Inject


class CatListPresenter @Inject constructor(
    private val catRepository: CatRepository,
    private val fileRepository: FileRepository
) :
    MvpPresenter<CatListView>() {

    fun load(page: Long) {
        catRepository.getCatList(page)
            .withDefaultSchedulers()
            .subscribe { t1, t2 ->
                viewState.addCats(t1.map { it.copy(heart = HEART.UNLIKE) })
            }
    }

    fun onCatLiked(cat: Cat) {
        catRepository.catLike(cat)
            .withDefaultSchedulers()
            .subscribe {

            }
    }

    fun downloadImage(url: String) {
        fileRepository.downloadImage(url)
    }
}

fun <T> Single<T>.withDefaultSchedulers():
        Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.withDefaultSchedulers():
        Maybe<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.withDefaultSchedulers():
        Completable = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

class CatsAdapter(val function: (Cat) -> Unit, val longClick: (String) -> Unit) :
    RecyclerView.Adapter<CatViewHolder>() {

    val currentList: MutableList<Cat> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder =
        CatViewHolder(parent.inflate(R.layout.cat_item, false))

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
        Glide.with(holder.itemView).load(currentList[position].url).apply(options)
            .into(holder.itemView.imageView)
        if (currentList[position].heart == HEART.FAVORITE) {
            holder.itemView.heart.visibility = View.GONE
        }
        holder.itemView.heart.setOnClickListener {
            when (currentList[position].heart) {
                HEART.LIKE -> {
                    it.background = ResourcesCompat.getDrawable(
                        holder.itemView.resources,
                        R.drawable.heart_off,
                        null
                    )
                    currentList[position] = currentList[position].copy(heart = HEART.UNLIKE)
                }
                HEART.UNLIKE -> {
                    it.background = ResourcesCompat.getDrawable(
                        holder.itemView.resources,
                        R.drawable.heart_on,
                        null
                    )
                    currentList[position] = currentList[position].copy(heart = HEART.LIKE)
                }
            }
            function.invoke(currentList[position])
        }

        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            longClick.invoke(currentList[position].url)
            true
        })

    }

    override fun getItemCount(): Int = currentList.size

}

class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}