package com.info_turrim.polandnews.profile.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.databinding.FragmentFavoritesBinding
import com.info_turrim.polandnews.news_feed.ui.controller.FavouritesNewsController
import com.info_turrim.polandnews.profile.ui.view_model.FavoritesViewModel
import com.info_turrim.polandnews.utils.extension.getIsUserLoggedIn
import com.info_turrim.polandnews.utils.extension.getIsUserReal

class FavouritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    override val viewModel by viewModels<FavoritesViewModel> { viewModelFactory }

    private lateinit var favouritesNewsController: FavouritesNewsController

    companion object {
        fun getInstance(): FavouritesFragment {
            val fragment = FavouritesFragment()
//            fragment.arguments = bundleOf(POSITION to position, USER_DATA to userData)
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initController()
        viewModel.loadNews()
        setupScrollListener()
        observeViewModel()
        bindClick()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun initController() {
        favouritesNewsController = FavouritesNewsController(requireContext())
        favouritesNewsController.listener = ::onEventClick
        favouritesNewsController.isUserReal = prefs.getIsUserReal()
        binding.rvFavorites.setController(favouritesNewsController)
    }

    private fun bindClick() {
        binding.apply {
            onFindNewsClick = View.OnClickListener {
                navController.navigate(R.id.news_graph)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            news.observe(viewLifecycleOwner) {
                it.fold(
                    onSuccess = { newsList ->
                        binding.apply {
                            if (newsList.isNotEmpty()) {
                                favoritesTCount = newsList.size.toString()
                                tvNoNews.isGone = true
                                btnFindNews.isGone = true
                                rvFavorites.isGone = false
                                favouritesNewsController.newsList = newsList
                            } else {
                                tvNoNews.isGone = false
                                btnFindNews.isGone = false
                                rvFavorites.isGone = true
                            }
                        }
                    },
                    onFailure = {

                    }
                )
            }
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.rvFavorites.layoutManager as LinearLayoutManager
        binding.rvFavorites.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (dy > 0) {
                    viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
                }
            }
        })
    }

    private fun onEventClick(event: ModelViewEvent) {
        when (event) {
            is ModelViewEvent.NewsEvent.NewsLikeClickEvent -> {
                if (prefs.getIsUserLoggedIn()) {
                    viewModel.likeNews(event.id)
                } else {
                    showInformationDialog(
                        R.string.need_to_be_registered_or_logged_in_like,
                        R.string.error
                    )
                }
            }
            is ModelViewEvent.NewsEvent.CommentsClickEvent -> {
                navController.navigate(
                    R.id.action_profileFragment_to_commentsFragment,
                    bundleOf("newsId" to event.id, "commentsAmount" to event.commentsAmount)
                )
            }
            is ModelViewEvent.NewsEvent.ShareClickEvent -> {
                val shareIntent = Intent(Intent.ACTION_SEND).also {
                    it.type = "text/plain"
                    it.putExtra(Intent.EXTRA_SUBJECT, "Poland News")
                }
                val shareMessage = "${event.newsHeader}\n\n" +
                        "\nCheck it out!\n\n" +
                        event.newsLink

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "Choose one"))

                viewModel.onShareNews(event.id)
            }
            is ModelViewEvent.NewsEvent.AddToFavouritesClickEvent -> {
//                if (prefs.getIsUserReal()) {
//                    viewModel.addToFavorite(FavoriteRequest(news = event.newsId))
//                } else {
//                    showInformationDialog(
//                        R.string.need_to_be_registered_or_logged_in_bookmark_news,
//                        R.string.error
//                    ) {
//                        navController.navigate(R.id.action_global_to_startAuthFragment)
//                    }
//                }
            }
            is ModelViewEvent.NewsEvent.RemoveFromFavouritesClickEvent -> {
                viewModel.removeFromFavourite(event.id)
            }
            is ModelViewEvent.NewsEvent.NewsClickEvent -> {
                navController.navigate(
                    R.id.action_profileFragment_to_newsGraph,
                    bundleOf(
                        "newsId" to event.id,
                        "sourceId" to event.sourceId
                    )
                )
            }

            is ModelViewEvent.NewsEvent.NewsSourceClickEvent -> {
                navController.navigate(
                    R.id.action_profileFragment_to_sourceFragment,
                    bundleOf("sourceId" to event.sourceId)
                )
            }
            else -> {}
        }
    }
}