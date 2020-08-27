package de.sebschaef.cat.ui.explore

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

        exploreViewModel.viewState.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun initRecyclerView() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_random_cat_images)
        recyclerView?.adapter = catImagesAdapter.withLoadStateFooter(
            footer = LoadStateAdapter(catImagesAdapter)
        )

        lifecycleScope.launch {
            exploreViewModel.catImagesFlow.collectLatest {
                catImagesAdapter.submitData(it)
            }
        }
    }

    override fun render(exploreState: ExploreState) = when (exploreState) {
        is Refresh -> catImagesAdapter.refresh()
    }

    private fun onImageFavouriteClicked(image: Image, isFavoured: Boolean) {
        exploreViewModel.onViewEvent(
            ExploreEvent.ImageFavouredChanged(
                image = image,
                isFavoured = isFavoured
            )
        )
    }
}