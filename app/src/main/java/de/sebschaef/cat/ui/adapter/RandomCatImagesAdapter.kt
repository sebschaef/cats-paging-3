package de.sebschaef.cat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import de.sebschaef.cat.R
import de.sebschaef.cat.model.persistence.Image

class RandomCatImagesAdapter :
    PagingDataAdapter<Image, ImageViewHolder>(ImageDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cat_image, parent, false)
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val catImageView = holder.itemView.findViewById<ImageView>(R.id.iv_cat_image)

        getItem(position)?.let {
            Glide.with(catImageView)
                .load(it.url)
                .into(catImageView)
        }
    }
}

object ImageDiffer : DiffUtil.ItemCallback<Image>() {

    override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem

}