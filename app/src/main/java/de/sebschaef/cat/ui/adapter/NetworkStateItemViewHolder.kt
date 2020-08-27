package de.sebschaef.cat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import de.sebschaef.cat.R

class NetworkStateItemViewHolder(
    parent: ViewGroup,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
) {
    private val progressBar: ProgressBar
        get() = itemView.findViewById(R.id.pb_progress)

    private val errorMsg: TextView
        get() = itemView.findViewById(R.id.tv_error)

    private val retry: Button
        get() = itemView.findViewById(R.id.b_retry)

    init {
        retry.setOnClickListener { retryCallback() }
    }

    fun bindTo(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMsg.text = (loadState as? LoadState.Error)?.error?.message
    }
}