package de.sebschaef.cat.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import de.sebschaef.cat.R
import de.sebschaef.cat.model.event.ExploreEvent
import de.sebschaef.cat.model.event.FavouriteEvent
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.model.state.FavouriteState
import de.sebschaef.cat.model.state.FavouriteState.*
import de.sebschaef.cat.ui.adapter.CatImagesAdapter
import de.sebschaef.cat.ui.adapter.LoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouritesFragment : Fragment(), FavouriteContract.View {

    private lateinit var favouritesViewModel: FavouritesViewModel

    private val catImagesAdapter = CatImagesAdapter(this::onImageFavouriteClicked)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        favouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        favouritesViewModel.viewState.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun initRecyclerView() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_favourite_cat_images)
        recyclerView?.adapter = catImagesAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(catImagesAdapter),
            footer = LoadStateAdapter(catImagesAdapter)
        )
        
        lifecycleScope.launch {
            favouritesViewModel.catImagesFlow.collectLatest {
                catImagesAdapter.submitData(it)
            }
        }
    }

    override fun render(favouriteState: FavouriteState) = when (favouriteState) {
        is Refresh -> catImagesAdapter.refresh()
    }

    private fun onImageFavouriteClicked(image: Image, isFavoured: Boolean) {
        favouritesViewModel.onViewEvent(
            FavouriteEvent.ImageFavouredChanged(
                image = image,
                isFavoured = isFavoured
            )
        )
    }
}