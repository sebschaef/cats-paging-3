package de.sebschaef.cat.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import de.sebschaef.cat.R
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

    private val coordinatorLayout: CoordinatorLayout?
        get() = view?.findViewById(R.id.cl_coordinator)

    private val recyclerView: RecyclerView?
        get() = view?.findViewById(R.id.rv_favourite_cat_images)

    private val swipeRefresh: SwipeRefreshLayout?
        get() = view?.findViewById(R.id.swl_refresh)

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
        initSwipeRefreshLayout()

        favouritesViewModel.viewState.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onResume() {
        super.onResume()
        favouritesViewModel.onViewEvent(FavouriteEvent.Refresh)
    }

    private fun initRecyclerView() {
        recyclerView?.adapter = catImagesAdapter.withLoadStateFooter(
            footer = LoadStateAdapter(catImagesAdapter)
        )

        lifecycleScope.launch {
            favouritesViewModel.catImagesFlow.collectLatest {
                catImagesAdapter.submitData(it)
                //swipeRefresh?.isRefreshing = false
            }
        }

        lifecycleScope.launch {
            catImagesAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Error -> displayErrorMessage(getString(R.string.error_message_fetch))
                    is LoadState.NotLoading -> swipeRefresh?.isRefreshing = false
                }
            }
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefresh?.setOnRefreshListener {
            favouritesViewModel.onViewEvent(FavouriteEvent.Refresh)
        }
    }

    override fun render(favouriteState: FavouriteState) = when (favouriteState) {
        is Load -> refresh(favouriteState.position)
        is Error -> displayErrorMessage(favouriteState.message)
    }

    private fun refresh(position: Int?) = position?.let {
        catImagesAdapter.notifyItemChanged(position)
    } ?: run {
        swipeRefresh?.isRefreshing = true
        catImagesAdapter.refresh()
    }

    private fun displayErrorMessage(message: String?) {
        swipeRefresh?.isRefreshing = false
        coordinatorLayout?.let {
            val msg = message ?: getString(R.string.error_message_generic)
            Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun onImageFavouriteClicked(adapterPos: Int, image: Image, isFavoured: Boolean) {
        favouritesViewModel.onViewEvent(
            FavouriteEvent.ImageFavouredChanged(
                position = adapterPos,
                image = image,
                isFavoured = isFavoured
            )
        )
    }
}