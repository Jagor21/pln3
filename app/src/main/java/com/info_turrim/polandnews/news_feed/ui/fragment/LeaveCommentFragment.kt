package com.info_turrim.polandnews.news_feed.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentLeaveCommentBinding
import com.info_turrim.polandnews.news_feed.data.model.CommentRequest
import com.info_turrim.polandnews.news_feed.domain.model.LeaveCommentRequestParams
import com.info_turrim.polandnews.news_feed.ui.view_model.LeaveCommentViewModel

class LeaveCommentFragment :
    BaseFragment<FragmentLeaveCommentBinding>(R.layout.fragment_leave_comment) {

    override val viewModel by viewModels<LeaveCommentViewModel> { viewModelFactory }

    private val args by navArgs<LeaveCommentFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
        observeViewModel()
    }

    private fun bindView() {
        binding.apply {
            onLeaveCommentClick = View.OnClickListener {

                etComment.error = null

                if (checkComment()) {
                    viewModel.leaveComment(
                        LeaveCommentRequestParams(
                            commentRequest = CommentRequest(
                                text = etComment.text.toString(),
                                comment = null
                            ),
                            newsId = args.newsId
                        )
                    )
                } else {
                    etComment.error = requireContext().getString(R.string.empty_comment)
                }
            }
            tbLeaveComment.setNavigationOnClickListener {
                navController.navigateUp()
            }
        }
    }

    private fun checkComment(): Boolean {
        return binding.etComment.text.toString().isNotEmpty()
    }

    private fun observeViewModel() {
        viewModel.leaveCommentResult.observe(viewLifecycleOwner) {
            if (it) {
                showSnackbarMessage(getString(R.string.comment_was_left))
                navController.navigateUp()
            }
        }
    }
}