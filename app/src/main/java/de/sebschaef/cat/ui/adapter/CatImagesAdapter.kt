package de.sebschaef.cat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import de.sebschaef.cat.R
import de.sebschaef.cat.model.persistence.Image

class CatImagesAdapter(val onFavClicked: (adapterPos: Int, image: Image, isFavoured: Boolean) -> Unit) :
    PagingDataAdapter<Image, ImageViewHolder>(ImageDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cat_image, parent, false)
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val catImageView = holder.itemView.findViewById<ImageView>(R.id.iv_cat_image)
        val favIcon = holder.itemView.findViewById<ImageView>(R.id.iv_fav)

        getItem(position)?.let { image ->
            Glide.with(catImageView)
                .load(image.url)
                .placeholder(R.drawable.ic_cat_half_transparent)
                .into(catImageView)

            favIcon.apply {
                setImageResource(
                    image.favId?.let { R.drawable.ic_star_full } ?: R.drawable.ic_star_outline
                )
                setOnClickListener {
                    val setFavoured = image.favId == null
                    onFavClicked(position, image, setFavoured)
                }
            }
        }
    }
}

object ImageDiffer : DiffUtil.ItemCallback<Image>() {

    override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem

}