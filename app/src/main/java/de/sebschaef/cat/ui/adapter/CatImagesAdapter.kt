package de.sebschaef.cat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import de.sebschaef.cat.R
import de.sebschaef.cat.model.persistence.Image

class CatImagesAdapter(
    private val onFavClicked: (adapterPos: Int, image: Image, isFavoured: Boolean) -> Unit
) : PagingDataAdapter<Image, ImageViewHolder>(ImageDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cat_image,
            parent,
            false
        )
        return ImageViewHolder(itemView, onFavClicked)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) } ?: holder.reset()
    }
}

object ImageDiffer : DiffUtil.ItemCallback<Image>() {

    override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem

}