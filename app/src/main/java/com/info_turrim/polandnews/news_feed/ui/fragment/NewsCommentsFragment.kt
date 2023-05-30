package com.info_turrim.polandnews.news_feed.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.databinding.FragmentNewsCommentsBinding
import com.info_turrim.polandnews.news_feed.data.model.GetCommentsRequestParam
import com.info_turrim.polandnews.news_feed.ui.controller.NewsCommentsController
import com.info_turrim.polandnews.news_feed.ui.view_model.NewsCommentsViewModel
import com.info_turrim.polandnews.utils.extension.getIsUserReal
import timber.log.Timber
import com.info_turrim.polandnews.base.Result

class NewsCommentsFragment :
    BaseFragment<FragmentNewsCommentsBinding>(R.layout.fragment_news_comments) {

    override val viewModel by viewModels<NewsCommentsViewModel> { viewModelFactory }

    private lateinit var newsCommentsController: NewsCommentsController

    private val args by navArgs<NewsCommentsFragmentArgs>()

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
        bindViews()
        initController()
        observeViewModel()
        if (args.newsId != -1) {
            viewModel.loadComments(
                GetCommentsRequestParam(
                    newsId = args.newsId,
                    pageIndex = 1,
                    pageSize = null
                )
            )
        }
    }

    private fun onClickEvent(event: ModelViewEvent) {
        when (event) {
            is ModelViewEvent.CommentEvent.CommentLikeEvent -> {
                if (prefs.getIsUserReal()) {
                    viewModel.onCommentLike(event.commentId)
                } else {
                    showInformationDialog(
                        R.string.need_to_be_registered_or_logged_in_like_comment,
                        R.string.error
                    )
                }
            }
            else -> {}
        }
    }

    private fun bindViews() {
        binding.apply {
            commentsAmount = args.commentsAmount
            ivBack.setOnClickListener {
                navController.navigateUp()
            }

            onLeaveCommentClick = View.OnClickListener {
                if (prefs.getIsUserReal()) {
                    navController.navigate(
                        R.id.action_newsCommentsFragment_to_leaveCommentFragment,
                        bundleOf("newsId" to args.newsId)
                    )
                } else {
                    showInformationDialog(
                        R.string.need_to_be_registered_or_logged_in_leave_comment,
                        R.string.error
                    ) {
                        navController.navigate(R.id.action_newsCommentsFragment_to_profileGraph)
                    }
                }
            }
        }
    }

    private fun initController() {
        newsCommentsController = NewsCommentsController(requireContext())
        newsCommentsController.listener = ::onClickEvent
        newsCommentsController.isUserLoggedIn = prefs.getIsUserReal()
        binding.rvComments.setController(newsCommentsController)
    }

    private fun observeViewModel() {
        viewModel.comments.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = {
                    if (it.isEmpty()) {
                        binding.apply {
                            rvComments.isGone = true
                            tvNoCommentsMsg.isGone = false
                        }
                    } else {
                        binding.apply {
                            rvComments.isGone = false
                            tvNoCommentsMsg.isGone = true
                            newsCommentsController.commentList = it
                        }
                    }
                },
                onFailure = {
                    it as Result.ErrorResult.NetworkErrorResponse
                    Timber.tag("ERROR").d("${it.code} | ${it.errorMessage}")
                }
            )
        }
    }
}