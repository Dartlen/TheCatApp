package bydartlen.thecatapp.catlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bydartlen.thecatapp.R
import bydartlen.thecatapp.base.inflate
import bydartlen.thecatapp.data.network.Cat

class CatsAdapter(val function: (Cat) -> Unit, val longClick: (String) -> Unit) :
    RecyclerView.Adapter<CatViewHolder>() {

    private val currentList: MutableList<Cat> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder =
        CatViewHolder(parent.inflate(R.layout.cat_item, false))

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(function, longClick, currentList[position]) {
            currentList[position].heart = it
        }
    }

    override fun getItemCount(): Int = currentList.size

    fun updateList(cats: List<Cat>) {
        currentList.addAll(cats)
        notifyDataSetChanged()
    }

}