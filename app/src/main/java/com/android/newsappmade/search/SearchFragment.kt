package com.android.newsappmade.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.newsappmade.R
import com.android.newsappmade.core.data.Resource
import com.android.newsappmade.core.domain.model.Article
import com.android.newsappmade.core.ui.TrendingAdapter
import com.android.newsappmade.databinding.FragmentSearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val searchViewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        val trendingAdapter = TrendingAdapter()

        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    searchViewModel.queryChannel.send(s.toString())
                    /*
                    searchViewModel.searchNews(s.toString()).observe(viewLifecycleOwner, {result ->
                        trendingAdapter.setData(result)
                        with(binding.rvSearch) {
                            layoutManager = LinearLayoutManager(context)
                            setHasFixedSize(true)
                            adapter = trendingAdapter
                        }
                    })*/
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        searchViewModel.searchNews.observe(viewLifecycleOwner, {
            var temp = listOf<Article>()
            it.map { article ->
                temp = article
            }
            trendingAdapter.setData(temp)
            with(binding.rvSearch) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = trendingAdapter
            }
        })

        /*
        searchViewModel.searchNews.observe(viewLifecycleOwner, { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBarSearch.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBarSearch.visibility = View.GONE
                        trendingAdapter.setData(result.data)
                        with(binding.rvSearch) {
                            layoutManager = LinearLayoutManager(context)
                            setHasFixedSize(true)
                            adapter = trendingAdapter
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBarSearch.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = result.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        })
         */

        return view
    }

}