package de.sebschaef.cat.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import de.sebschaef.cat.R
import de.sebschaef.cat.ui.adapter.RandomCatImagesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExploreFragment : Fragment() {

    private lateinit var exploreViewModel: ExploreViewModel

    private val randomCatImagesAdapter = RandomCatImagesAdapter()

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
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_random_cat_images)
        recyclerView.adapter = randomCatImagesAdapter
        lifecycleScope.launch {
            exploreViewModel.catImagesFlow.collectLatest {
                randomCatImagesAdapter.submitData(it)
            }
        }
    }
}