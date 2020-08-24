package de.sebschaef.cat.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sebschaef.cat.repository.CatRepository
import de.sebschaef.cat.repository.CatRepositoryImpl
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    // TODO dependency injection
    private val catRepository: CatRepository by lazy {
        CatRepositoryImpl()
    }

    init {
        viewModelScope.launch {
            catRepository.getRandomCats(0)
        }
    }

}