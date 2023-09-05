package com.example.androidsafecoroutines.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidsafecoroutines.data.remote.error.ApiError
import com.example.androidsafecoroutines.databinding.FragmentMainBinding
import com.example.androidsafecoroutines.main.list.RepoAdapter
import com.example.androidsafecoroutines.model.Repo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var viewBinding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by viewModels()

    private var repoAdapter: RepoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return viewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildRepoList()
        observeRepoList()
        observeFailure()
    }

    private fun observeFailure() {
        viewModel.failureLiveData.observe(viewLifecycleOwner) { failure ->
            when (failure) {
                is ApiError.Connection -> {
                    showError("Network connection error")
                }

                is ApiError.Unauthorized -> {
                    showError("Expired token")
                }

                is ApiError.Server -> {
                    showError(failure.errorMessage)
                }

                is UnknownError -> {
                    showError("Unknown error: ${failure.message}")
                }
            }
        }
    }

    private fun observeRepoList() {
        viewModel.repoListLiveData.observe(viewLifecycleOwner) { repoList ->
            repoAdapter?.submitList(repoList)
        }
    }

    private fun buildRepoList() {
        repoAdapter = RepoAdapter(onItemClick = { repo: Repo ->
            showRepoDetail(repo)
        })
        viewBinding?.rvRepos?.adapter = repoAdapter
    }

    private fun showRepoDetail(repo: Repo) {
        AlertDialog.Builder(context)
            .setTitle("Repository detail")
            .setMessage("${repo.name} by ${repo.owner}")
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    private fun showError(message: String) {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    override fun onDestroyView() {
        viewBinding = null
        repoAdapter = null
        super.onDestroyView()
    }
}