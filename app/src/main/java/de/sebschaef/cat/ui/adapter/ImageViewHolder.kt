package de.sebschaef.cat.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.sebschaef.cat.R
import de.sebschaef.cat.model.persistence.Image

class ImageViewHolder(
    itemView: View,
    private val onFavClicked: (adapterPos: Int, image: Image, isFavoured: Boolean) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val catImageView: ImageView
        get() = itemView.findViewById(R.id.iv_cat_image)

    private val favIcon: ImageView
        get() = itemView.findViewById<ImageView>(R.id.iv_fav)

    fun reset() {
        catImageView.setImageDrawable(null)
        favIcon.apply {
            setImageResource(R.drawable.ic_star_outline)
            setOnClickListener(null)
        }
    }

    fun bindTo(image: Image) {
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
                onFavClicked(bindingAdapterPosition, image, setFavoured)
            }
        }
    }

}