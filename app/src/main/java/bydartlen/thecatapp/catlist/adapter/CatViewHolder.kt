package bydartlen.thecatapp.catlist.adapter

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import bydartlen.thecatapp.R
import bydartlen.thecatapp.data.network.Cat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.cat_item.view.*

class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        click: (Cat) -> Unit,
        longClick: (String) -> Unit,
        item: Cat,
        callbackChangeState: (HEART) -> Unit
    ) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
        Glide.with(itemView).load(item.url).apply(options)
            .into(itemView.imageView)
        if (item.heart == HEART.FAVORITE) {
            itemView.heart.visibility = View.GONE
        }
        when (item.heart) {
            HEART.LIKE -> {
                itemView.heart.background = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.heart_on,
                    null
                )
            }
            HEART.UNLIKE -> {
                itemView.heart.background = ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.heart_off,
                    null
                )
            }
        }
        itemView.heart.setOnClickListener {
            when (item.heart) {
                HEART.LIKE -> {
                    it.background = ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.heart_off,
                        null
                    )
                    callbackChangeState.invoke(HEART.UNLIKE)
                }
                HEART.UNLIKE -> {
                    it.background = ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.heart_on,
                        null
                    )
                    callbackChangeState.invoke(HEART.LIKE)
                }
            }
            click.invoke(item)
        }

        itemView.setOnLongClickListener {
            longClick.invoke(item.url)
            true
        }
    }
}
