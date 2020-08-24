package de.sebschaef.cat.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.sebschaef.cat.ui.adapter.RandomCatImagesPagingSource

class ExploreViewModel : ViewModel() {

    val catImagesFlow = Pager(config = PagingConfig(pageSize = 10)) {
        RandomCatImagesPagingSource()
    }.flow.cachedIn(viewModelScope)

}