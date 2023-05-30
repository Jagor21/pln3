package com.info_turrim.polandnews.base

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.common.ProgressBarDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VB : ViewDataBinding>(
    @LayoutRes
    private val layoutId: Int
) : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: VB? = null
    val binding get() = _binding!!

    lateinit var navController: NavController

    @Inject
    lateinit var prefs: SharedPreferences

    abstract val viewModel: BaseViewModel?

    private lateinit var progress: ProgressBarDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = ProgressBarDialog(requireContext())
        viewModel?.apply {
            error.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
            loading.observe(viewLifecycleOwner, Observer {
//                manageLoading(it)
            })
        }
        navController = findNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showInformationDialog(@StringRes message: Int, @StringRes title: Int, action:() -> Unit = {}) {
        AlertDialog.Builder(requireActivity())
            .setTitle(requireContext().getString(title))
            .setMessage(requireContext().getString(message))
            .setPositiveButton(
                requireContext().getString(R.string.ok)
            ) { dialog, _ ->
                action.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(
                requireContext().getString(R.string.cancel)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun showError(message: String, title: String = "Error") {
        AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                requireContext().getString(R.string.ok)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun showSnackbarMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun manageLoading(loading: Boolean) {
        if (loading) progress.show() else progress.dismiss()
    }
}