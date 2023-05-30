package com.info_turrim.polandnews.source.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.databinding.FragmentSourceBinding
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.source.ui.controller.SourceController
import com.info_turrim.polandnews.source.ui.view_model.SourceViewModel
import com.info_turrim.polandnews.utils.extension.getIsUserLoggedIn
import timber.log.Timber
import com.info_turrim.polandnews.base.Result

class SourceFragment : BaseFragment<FragmentSourceBinding>(R.layout.fragment_source) {

    override val viewModel by viewModels<SourceViewModel> { viewModelFactory }

    private lateinit var sourceController: SourceController

    private val args by navArgs<SourceFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.tbSource.setNavigationOnClickListener { navController.navigateUp() }
        initController()
        bindViewModel()
        setupScrollListener()
        if (args.sourceId != -1) {
            viewModel.getSourcesDetails(GetNewsRequestParam(sourceId = args.sourceId, page = 1))
        }
    }

    private fun initController() {
        sourceController = SourceController()
        sourceController.listener = ::onEventClick
        binding.rvSource.setController(sourceController)

    }

    private fun bindViewModel() {
        viewModel.sourcesDetails.observe(viewLifecycleOwner) {
            sourceController.sourceDetails = it
        }

        viewModel.sharedResult.observe(viewLifecycleOwner) {
            showSnackbarMessage(it)
        }

        viewModel.news.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = {
                    sourceController.newsList = it.toList()
                },
                onFailure = {
                    it as Result.ErrorResult.NetworkErrorResponse
                    Timber.tag("MY_TAG").d("${it.code} | ${it.errorMessage}")
                }
            )
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.rvSource.layoutManager as LinearLayoutManager
        binding.rvSource.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
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
                    R.id.action_sourceFragment_to_newsCommentsFragment,
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
                viewModel.addToFavorite(FavoriteRequest(event.newsId))
            }
            is ModelViewEvent.NewsEvent.RemoveFromFavouritesClickEvent -> {
                viewModel.removeFromFavourite(event.id)
            }
            is ModelViewEvent.NewsEvent.NewsClickEvent -> {
                navController.navigate(
                    R.id.action_sourceFragment_to_newsDetailsFragment,
                    bundleOf(
                        "newsId" to event.id,
//                        "filterType" to NewsFilterType.TRENDS,
                        "sourceId" to event.sourceId
                    )
                )
            }
            else -> {}
        }
    }
}