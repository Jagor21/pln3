package com.info_turrim.polandnews.news_feed.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.info_turrim.polandnews.MainActivity
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.*
import com.info_turrim.polandnews.databinding.FragmentNewsFeedSectionBinding
import com.info_turrim.polandnews.news_feed.ui.controller.NewsFeedController
import com.info_turrim.polandnews.news_feed.ui.view_model.NewsFeedSectionViewModel
import timber.log.Timber
import com.info_turrim.polandnews.base.ModelViewEvent.NewsEvent.*
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetForYouNewsRequestParam
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.utils.extension.EMPTY
import com.info_turrim.polandnews.utils.extension.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

private const val POSITION = "position"
private const val FILTER_TYPE = "FILTER_TYPE"
private const val CATEGORY_ID = "CATEGORY_ID"
private const val FOR_YOU_POSITION = 0
private const val TRENDING_POSITION = 1
private const val LOCAL_POSITION = 2
private const val BREAKING_POSITION = 3
private const val THE_LATEST_POSITION = 4

class NewsFeedSectionFragment :
    BaseFragment<FragmentNewsFeedSectionBinding>(R.layout.fragment_news_feed_section) {

    override val viewModel by viewModels<NewsFeedSectionViewModel> { viewModelFactory }

    private var filterType = String.EMPTY

    private var getNewsRequestParam: GetNewsRequestParam? = null
    private lateinit var newsFeedController: NewsFeedController

    private var needToShowExitWarning: Boolean = true

    private var categoryId = -1

    private var newsList = emptyList<News>()
    private var forYouNewsList = emptyList<News>()

    private var savedNewsPosition = -1

    companion object {
        fun getInstance(
            position: Int,
            filterType: String,
            categoryId: Int
        ): NewsFeedSectionFragment {
            val fragment = NewsFeedSectionFragment()
            fragment.arguments =
                bundleOf(POSITION to position, FILTER_TYPE to filterType, CATEGORY_ID to categoryId)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (needToShowExitWarning) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.exit_warning_message),
                        Toast.LENGTH_SHORT
                    ).show()
                    needToShowExitWarning = false
                } else {
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            filterType = it.getString(FILTER_TYPE).orEmpty()
//            filterType = when (it.getInt(POSITION)) {
//                FOR_YOU_POSITION -> NewsFilterType.FOR_YOU
//                TRENDING_POSITION -> NewsFilterType.TRENDS
//                LOCAL_POSITION -> NewsFilterType.GEO_BASED
//                BREAKING_POSITION -> NewsFilterType.BREAKING
//                else -> NewsFilterType.LATEST
//            }
        }
        arguments?.getInt(CATEGORY_ID)?.let { categoryId = it }

        if (categoryId != -1) {
            if (newsList.isEmpty()) {
                newsFeedController = NewsFeedController(requireContext())
                newsFeedController.listener = ::onEventClick
                newsFeedController.isUserReal = prefs.getIsUserReal()
                binding.rvNews.setController(newsFeedController)

                if (categoryId != -1) {
                    getNewsRequestParam =
                        GetNewsRequestParam(
                            newsFilterType = null,
                            country = null,
                            region = null,
                            city = null,
                            page = 1,
                            categoryId = arguments?.getInt(CATEGORY_ID)
                        )
                    getNewsRequestParam?.let {
                        viewModel.loadNews(it)
                    }

                    viewModel.news.observe(viewLifecycleOwner) {
                        it.fold(
                            onSuccess = {
                                val news = mutableListOf<News>()
                                news.addAll(it)
                                val needToShowAd = Firebase.remoteConfig.getBoolean("show_content")
                                val adList = (activity as MainActivity).adList
                                if (needToShowAd && adList.isNotEmpty()) {
                                    val newsCount = it.size / 5
                                    var insertAdIndex = 5
                                    var adIndex = 0

                                    repeat(newsCount) {
                                        if (adIndex >= adList.size - 1) {
                                            adIndex = 0
                                        }
                                        news.add(insertAdIndex, adList[adIndex])
                                        insertAdIndex += 6
                                        adIndex++
                                    }
                                }
                                newsFeedController.newsList = news
                                newsList = news
                            },
                            onFailure = {
                                it as Result.ErrorResult.NetworkErrorResponse
                                Timber.tag("MY_TAG").d("${it.code} | ${it.errorMessage}")
                            }
                        )
                    }
                }
            } else {
                newsFeedController = NewsFeedController(requireContext())
                newsFeedController.listener = ::onEventClick
                newsFeedController.isUserReal = prefs.getIsUserReal()
                binding.rvNews.setController(newsFeedController)
                newsFeedController.newsList = newsList
                binding.rvNews.postDelayed(Runnable {
                    binding.rvNews.scrollToPosition(savedNewsPosition)
                }, 500L)
            }
        } else {
            if (forYouNewsList.isEmpty()) {
                newsFeedController = NewsFeedController(requireContext())
                newsFeedController.listener = ::onEventClick
                newsFeedController.isUserReal = prefs.getIsUserReal()
                binding.rvNews.setController(newsFeedController)
                viewModel.forYouNews.observe(viewLifecycleOwner) {
                    it.fold(
                        onSuccess = {
                            val news = mutableListOf<News>()
                            news.addAll(it)
                            val needToShowAd = Firebase.remoteConfig.getBoolean("show_content")
                            val adList = (activity as MainActivity).adList
                            if (needToShowAd && adList.isNotEmpty()) {
                                val newsCount = it.size / 5
                                var insertAdIndex = 5
                                var adIndex = 0

                                repeat(newsCount) {
                                    if (adIndex >= adList.size - 1) {
                                        adIndex = 0
                                    }
                                    news.add(insertAdIndex, adList[adIndex])
                                    insertAdIndex += 6
                                    adIndex++
                                }
                            }
                            newsFeedController.newsList = news
                            newsList = news
                        },
                        onFailure = {

                        }
                    )
                }
                viewModel.loadForYouNews(
                    GetForYouNewsRequestParam(
                        userId = prefs.getUserId(),
                        pageIndex = 1,
                        pageSize = null
                    )
                )
            } else {
                newsFeedController = NewsFeedController(requireContext())
                newsFeedController.listener = ::onEventClick
                newsFeedController.isUserReal = prefs.getIsUserReal()
                binding.rvNews.setController(newsFeedController)
                newsFeedController.newsList = forYouNewsList
            }
        }
        setupScrollListener()
        viewModel.sharedResult.observe(viewLifecycleOwner) {
            showSnackbarMessage(it)
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager
        binding.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                viewModel.listScrolled(
                    visibleItemCount,
                    lastVisibleItem,
                    totalItemCount,
                    categoryId == -1
                )
            }
        })
    }

    private fun onEventClick(event: ModelViewEvent) {
        when (event) {
            is AdClickEvent -> {
                requireContext().openCustomTab("https:${event.adUrl}")
            }
            is NewsLikeClickEvent -> {
                if (prefs.getIsUserReal()) {
                    viewModel.likeNews(event.id)
                } else {
                    showInformationDialog(
                        R.string.need_to_be_registered_or_logged_in_like,
                        R.string.error
                    ) {
                        navController.saveStateAndNavigate(R.id.action_newsFeedFragment_to_profileGraph) {
                            saveNewsFeedAndPosition(event.position)
                        }
                    }
                }
            }
            is CommentsClickEvent -> {
                navController.saveStateAndNavigate(
                    R.id.action_newsFeedFragment_to_newsCommentsFragment,
                    bundleOf("newsId" to event.id, "commentsAmount" to event.commentsAmount)
                ) {
                    saveNewsFeedAndPosition(event.position)
                }
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
                if (prefs.getIsUserReal()) {
                    viewModel.addToFavorite(FavoriteRequest(news = event.newsId))
                } else {
                    showInformationDialog(
                        R.string.need_to_be_registered_or_logged_in_bookmark_news,
                        R.string.error
                    ) {
                        navController.saveStateAndNavigate(R.id.action_newsFeedFragment_to_profileGraph) {
                            saveNewsFeedAndPosition(event.position)
                        }
                    }
                }
            }
            is RemoveFromFavouritesClickEvent -> {
                viewModel.removeFromFavourite(event.id)
            }
            is NewsClickEvent -> {
                navController.saveStateAndNavigate(
                    R.id.action_newsFeedFragment_to_newsDetailsFragment,
                    bundleOf(
                        "newsId" to event.id,
                        "filterType" to filterType,
                        "sourceId" to event.sourceId
                    )
                ) {
                    saveNewsFeedAndPosition(event.position)
                }
            }

            is NewsSourceClickEvent -> {
                navController.saveStateAndNavigate(
                    R.id.action_newsFeedFragment_to_sourceFragment,
                    bundleOf("sourceId" to event.sourceId)
                ) {
                    saveNewsFeedAndPosition(event.position)
                }
            }
            else -> {}
        }
    }

    private fun saveNewsFeedAndPosition(position: Int) {
        savedNewsPosition = position
        if (categoryId != -1) {
            newsList = newsFeedController.newsList
        } else {
            forYouNewsList = newsFeedController.newsList
        }
    }
}