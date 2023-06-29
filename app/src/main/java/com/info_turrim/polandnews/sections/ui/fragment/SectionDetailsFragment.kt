package com.info_turrim.polandnews.sections.ui.fragment

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
import com.info_turrim.polandnews.databinding.FragmentSectionDetailsBinding
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.sections.ui.controller.SectionDetailsController
import com.info_turrim.polandnews.sections.ui.view_model.SectionDetailsViewModel
import timber.log.Timber
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.data.model.GetAdRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.utils.extension.getIsUserReal
import com.info_turrim.polandnews.utils.extension.getUUID
import com.info_turrim.polandnews.utils.extension.saveStateAndNavigate
import com.info_turrim.polandnews.utils.extension.setUUID

class SectionDetailsFragment :
    BaseFragment<FragmentSectionDetailsBinding>(R.layout.fragment_section_details) {

    override val viewModel by viewModels<SectionDetailsViewModel> { viewModelFactory }

    private lateinit var sectionDetailsController: SectionDetailsController

    private val args by navArgs<SectionDetailsFragmentArgs>()

    private var currentSectionNewsPosition = -1

    private var currentSectionNewsList = listOf<News>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initController()
        bindViewModel()
        bindView()
        setupScrollListener()
        if (currentSectionNewsList.isEmpty()) {
            viewModel.loadNews(GetNewsRequestParam(categoryId = args.category.id, page = 1))
        } else {
            sectionDetailsController.sectionNewsList = currentSectionNewsList
            binding.rvSectionNews.postDelayed(Runnable {
                binding.rvSectionNews.scrollToPosition(currentSectionNewsPosition)
            }, 500L)
        }
        var uuid = prefs.getUUID()
        if (uuid.isEmpty()) {
            uuid = java.util.UUID.randomUUID().toString()
            prefs.setUUID(uuid)
        }
        viewModel.getAd(
            GetAdRequestParam(
                uuid = uuid,
                adsQuantity = 10
            )
        )
    }

    private fun initController() {
        sectionDetailsController = SectionDetailsController()
        sectionDetailsController.listener = ::onEventClick
        binding.rvSectionNews.setController(sectionDetailsController)
        sectionDetailsController.isUserReal = prefs.getIsUserReal()
    }


    private fun onEventClick(event: ModelViewEvent) {
        when (event) {
            is ModelViewEvent.NewsEvent.NewsLikeClickEvent -> {
                if (prefs.getIsUserReal()) {
                    viewModel.likeNews(event.id)
                } else {
                    showInformationDialog(
                        R.string.need_to_be_registered_or_logged_in_like,
                        R.string.error
                    ) {
                        navController.navigate(R.id.action_sectionsDetailsFragment_to_profileGraph)
                    }
                }
            }

            is ModelViewEvent.NewsEvent.CommentsClickEvent -> {
                navController.saveStateAndNavigate(
                    R.id.action_sectionNewsDetailsFragment_to_newsCommentsFragment,
                    bundleOf("newsId" to event.id, "commentsAmount" to event.commentsAmount)
                ) {
                    currentSectionNewsPosition = event.position
                }
//                navController.navigate(
//                    R.id.action_sectionNewsDetailsFragment_to_newsCommentsFragment,
//                    bundleOf("newsId" to event.id, "commentsAmount" to event.commentsAmount)
//                )
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
                if (prefs.getIsUserReal()) {
                    viewModel.addToFavorite(FavoriteRequest(event.newsId))
                } else {
                    showInformationDialog(
                        R.string.need_to_be_registered_or_logged_in_bookmark_news,
                        R.string.error
                    ) {
                        navController.navigate(R.id.action_sectionsDetailsFragment_to_profileGraph)
                    }
                }
            }

            is ModelViewEvent.NewsEvent.RemoveFromFavouritesClickEvent -> {
                viewModel.removeFromFavourite(event.id)
            }

            is ModelViewEvent.NewsEvent.NewsClickEvent -> {
                navController.saveStateAndNavigate(
                    R.id.action_sectionDetailsFragment_to_newsDetailsFragment,
                    bundleOf(
                        "newsId" to event.id,
                        "filterType" to "",
                        "sourceId" to event.sourceId
                    )
                ) {
                    currentSectionNewsPosition = event.position
                }
            }

            is ModelViewEvent.NewsEvent.NewsSourceClickEvent -> {
                navController.saveStateAndNavigate(
                    R.id.action_sectionDetailsFragment_to_sourceFragment,
                    bundleOf("sourceId" to event.sourceId)
                ) {
                    currentSectionNewsPosition = event.position
                }
            }

            else -> {}
        }
    }

    private fun bindViewModel() {
        viewModel.news.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = {
                    val news = sectionDetailsController.sectionNewsList.toMutableList()
                    news.addAll(it)
                    currentSectionNewsList = news
                    sectionDetailsController.sectionNewsList = currentSectionNewsList
                },
                onFailure = {
                    it as Result.ErrorResult.NetworkErrorResponse
                    Timber.tag("ERROR").d("${it.code} | ${it.errorMessage}")
                }
            )
        }
    }

    private fun bindView() {
        binding.apply {
            sectionName = args.sectionName
            ivIconBack.setOnClickListener {
                navController.navigateUp()
            }
            btnFollow.setOnClickListener {
                viewModel.subscribeForCategory(args.category.id)
            }
            btnFollow.isChecked = args.category.followedByUser
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.rvSectionNews.layoutManager as LinearLayoutManager
        binding.rvSectionNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
}