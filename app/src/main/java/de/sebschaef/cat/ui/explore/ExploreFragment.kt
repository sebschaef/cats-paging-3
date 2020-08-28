package de.sebschaef.cat.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import de.sebschaef.cat.R
import de.sebschaef.cat.model.event.ExploreEvent
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.model.state.ExploreState
import de.sebschaef.cat.model.state.ExploreState.*
import de.sebschaef.cat.ui.adapter.CatImagesAdapter
import de.sebschaef.cat.ui.adapter.LoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExploreFragment : Fragment(), ExploreContract.View {

    private lateinit var exploreViewModel: ExploreViewModel

    private val catImagesAdapter = CatImagesAdapter(this::onImageFavouriteClicked)

    private val coordinatorLayout: CoordinatorLayout?
        get() = view?.findViewById(R.id.cl_coordinator)

    private val recyclerView: RecyclerView?
        get() = view?.findViewById(R.id.rv_random_cat_images)

    private val swipeRefresh: SwipeRefreshLayout?
        get() = view?.findViewById(R.id.swl_refresh)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        exploreViewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSwipeRefreshLayout()

        exploreViewModel.viewState.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onResume() {
        super.onResume()
        exploreViewModel.onViewEvent(ExploreEvent.Refresh)
    }

    private fun initRecyclerView() {
        recyclerView?.adapter = catImagesAdapter.withLoadStateFooter(
            footer = LoadStateAdapter(catImagesAdapter)
        )

        lifecycleScope.launch {
            exploreViewModel.catImagesFlow.collectLatest {
                catImagesAdapter.submitData(it)
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
        swipeRefresh?.isRefreshing = false
        swipeRefresh?.setOnRefreshListener {
            exploreViewModel.onViewEvent(ExploreEvent.Refresh)
        }
    }

    override fun render(exploreState: ExploreState) = when (exploreState) {
        is Load -> refresh(exploreState.position)
        is Error -> displayErrorMessage(exploreState.message)
    }

    private fun refresh(position: Int?) = position?.let {
        catImagesAdapter.notifyItemChanged(it)
    } ?: run {
        swipeRefresh?.isRefreshing = true
        catImagesAdapter.refresh()
    }

    private fun displayErrorMessage(message: String?) {
        coordinatorLayout?.let {
            val msg = message ?: getString(R.string.error_message_generic)
            Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun onImageFavouriteClicked(adapterPos: Int, image: Image, isFavoured: Boolean) {
        exploreViewModel.onViewEvent(
            ExploreEvent.ImageFavouredChanged(
                position = adapterPos,
                image = image,
                isFavoured = isFavoured
            )
        )
    }
}