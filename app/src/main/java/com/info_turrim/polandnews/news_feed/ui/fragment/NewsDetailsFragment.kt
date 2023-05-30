package com.info_turrim.polandnews.news_feed.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.*
import com.info_turrim.polandnews.databinding.FragmentNewsDetailsBinding
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.ui.controller.NewsDetailsController
import com.info_turrim.polandnews.news_feed.ui.view_model.NewsDetailsViewModel
import com.info_turrim.polandnews.utils.extension.getIsUserLoggedIn
import timber.log.Timber
import com.info_turrim.polandnews.base.ModelViewEvent.NewsDetailsEvent.*
import com.info_turrim.polandnews.base.ModelViewEvent.NewsEvent.*
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.utils.extension.getIsUserReal
import com.info_turrim.polandnews.utils.extension.openCustomTab

class NewsDetailsFragment :
    BaseFragment<FragmentNewsDetailsBinding>(R.layout.fragment_news_details) {

    override val viewModel by viewModels<NewsDetailsViewModel> { viewModelFactory }

    private val args by navArgs<NewsDetailsFragmentArgs>()

    private lateinit var newsDetailsController: NewsDetailsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSourceDetails(
            args.newsId,
            GetNewsRequestParam(
                newsFilterType = args.filterType,
                page = 1,
                sourceId = args.sourceId
            )
        )

        initController()
        observeViewModel()
        bindViews()
        setupScrollListener()
    }

    private fun bindViews() {
        binding.apply {
//            tbNewsDetails.setNavigationOnClickListener {
//                navController.navigateUp()
//            }

            setOnContinueReadingClick { }

            setOnLikeClick { }

            setOnShareClick { }

            setOnBookmarkClick { }

            setOnCommentsClick { }

            setOnLeaveCommentClick { }

        }
    }

    private fun initController() {
        newsDetailsController = NewsDetailsController(requireContext())
        newsDetailsController.listener = ::onEventClick
        newsDetailsController.isUserReal = prefs.getIsUserReal()
        binding.rvNewsDetails.setController(newsDetailsController)
    }

    private fun onEventClick(event: ModelViewEvent) {

        when (event) {
            is ModelViewEvent.NewsDetailsEvent -> {
                when (event) {
                    is NewsDetailsSourceClickEvent -> {
                        navController.navigate(
                            R.id.action_newsDetailsFragment_to_sourceFragment,
                            bundleOf("sourceId" to event.sourceId)
                        )
                    }
                    is NewsDetailsContinueReadingClickEvent -> {
                        event.link?.let { requireContext().openCustomTab(it) }
                    }
                    is NewsDetailsShareClickEvent -> {
                        val shareIntent = Intent(Intent.ACTION_SEND).also {
                            it.type = "text/plain"
                            it.putExtra(Intent.EXTRA_SUBJECT, "Poland News")
                        }
                        val shareMessage = "${event.newsHeader}\n\n" +
                                "\nCheck it out!\n\n" +
                                event.newsLink

                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "Choose one"))

//                        viewModel.onShareNews(event.newsId)
                    }
                    is NewsDetailsLikeClickEvent -> {
                        if (prefs.getIsUserReal()) {
                            viewModel.likeNews(event.newsId)
                        } else {
                            showInformationDialog(
                                R.string.need_to_be_registered_or_logged_in_like,
                                R.string.error
                            ) {
                                navController.navigate(R.id.action_newsDetailsFragment_to_profileGraph)
                            }
                        }
                    }
                    is NewsDetailsCommentsClickEvent -> {
                        navController.navigate(
                            R.id.action_newsDetailsFragment_to_newsCommentsFragment, bundleOf(
                                "newsId" to event.newsId, "commentsAmount" to event.commentsAmount
                            )
                        )
                    }
                    is NewsDetailsCommentLikeClickEvent -> {
                        if (prefs.getIsUserReal()) {
                            viewModel.onCommentLike(event.commentId)
                        } else {
                            showInformationDialog(
                                R.string.need_to_be_registered_or_logged_in_like_comment,
                                R.string.error
                            )
                        }
                    }
                    is NewsDetailsAddToFavouriteClickEvent -> {
                        if (event.newsId == -1) {
                            showInformationDialog(
                                R.string.need_to_be_registered_or_logged_in_bookmark_news,
                                R.string.error
                            ) {
                                navController.navigate(R.id.action_newsDetailsFragment_to_profileGraph)
                            }
                        } else {
                            viewModel.addToFavorite(FavoriteRequest(news = event.newsId))
                        }
//                        if (prefs.getIsUserReal()) {
//                            viewModel.addToFavorite(FavoriteRequest(news = event.newsId))
//                        } else {

//                        }
                    }
                    BackClickEvent -> {navController.navigateUp()}
                    //NewsDetailsLeaveCommentClickEvent
                    else -> {
                        navController.navigate(
                            R.id.action_newsDetailsFragment_to_leaveCommentFragment,
                            bundleOf("newsId" to args.newsId)
                        )
                    }
                }
            }

            else -> {
                event as ModelViewEvent.NewsEvent
                when (event) {
                    is NewsLikeClickEvent -> {
                        if (prefs.getIsUserLoggedIn()) {
                            viewModel.likeNews(event.id)
                        } else {
                            showInformationDialog(
                                R.string.need_to_be_registered_or_logged_in_like,
                                R.string.error
                            )
                        }
                    }
                    is CommentsClickEvent -> {
                        navController.navigate(
                            R.id.action_newsDetailsFragment_to_newsCommentsFragment,
                            bundleOf("newsId" to event.id, "commentsAmount" to event.commentsAmount)
                        )
                    }
                    is ShareClickEvent -> {
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
                    is AddToFavouritesClickEvent -> {
                        viewModel.addToFavorite(FavoriteRequest(event.newsId))
                    }
                    is RemoveFromFavouritesClickEvent -> {
                        viewModel.removeFromFavourite(event.id)
                    }
                    is NewsClickEvent -> {
                        navController.navigate(
                            R.id.action_newsDetailsFragment_to_newsDetailsFragment,
                            bundleOf(
                                "newsId" to event.id,
                                "filterType" to args.filterType,
                                "sourceId" to event.sourceId
                            )
                        )
//                        val fragment = NewsDetailsFragment()
//                        val args = bundleOf(
//                            "newsId" to event.id,
//                            "filterType" to args.filterType,
//                            "sourceId" to event.sourceId
//                        )
//                        fragment.arguments = args
//                        requireActivity().supportFragmentManager.beginTransaction()
//                            .replace(R.id.nav_host_container, fragment)
//                            .addToBackStack(null)
//                            .commit()

                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            newsDetails.observe(viewLifecycleOwner) {
                newsDetailsController.newsDetails = it
                binding.newsDetails = it
            }

            newsComment.observe(viewLifecycleOwner) {
                binding.apply {
                    if (it.isNotEmpty()) {
                        newsDetailsController.comment = it[0]
                    }
                }
            }

            news.observe(viewLifecycleOwner) {
                it.fold(
                    onSuccess = {
                        newsDetailsController.newsList = it.toList()
                    },
                    onFailure = {
                        it as Result.ErrorResult.NetworkErrorResponse
                        Timber.tag("MY_TAG").d("${it.code} | ${it.errorMessage}")
                    }
                )
            }

            sourceDetails.observe(viewLifecycleOwner) {
                newsDetailsController.sourceDetails = it
            }
        }


    }

    private fun setupScrollListener() {
        val layoutManager = binding.rvNewsDetails.layoutManager as LinearLayoutManager
        binding.rvNewsDetails.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount - 4
                val visibleItemCount = layoutManager.childCount - 4
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition() - 4
                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
}