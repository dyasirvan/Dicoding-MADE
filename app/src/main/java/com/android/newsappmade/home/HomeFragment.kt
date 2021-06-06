package com.android.newsappmade.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.newsappmade.R
import com.android.core.data.Resource
import com.android.core.ui.HeadlineAdapter
import com.android.core.ui.TrendingAdapter
import com.android.newsappmade.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        if(activity != null){
            val headlineAdapter = HeadlineAdapter()
            val trendingAdapter = TrendingAdapter()

            homeViewModel.news.observe(viewLifecycleOwner, { headline ->
                if (headline != null) {
                    when (headline) {
                        is Resource.Loading -> {
                            binding.progressBarHeadline.visibility = View.VISIBLE
                            binding.view.visibility = View.INVISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBarHeadline.visibility = View.GONE
                            binding.view.visibility = View.VISIBLE
                            headlineAdapter.setData(headline.data)
                        }
                        is Resource.Error -> {
                            binding.progressBarHeadline.visibility = View.GONE
                            binding.view.visibility = View.VISIBLE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text = headline.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            })

            with(binding.rvHeadline) {
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = headlineAdapter

                headlineAdapter.setOnItemClickListener{
                    val bundle = Bundle().apply {
                        putParcelable("article", it)
                    }
                    findNavController().navigate(
                        R.id.action_homeFragment_to_detailActivity,
                        bundle
                    )
                }
            }

            homeViewModel.news.observe(viewLifecycleOwner, { trending ->
                if (trending != null) {
                    when (trending) {
                        is Resource.Loading -> binding.progressBarTrending.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBarTrending.visibility = View.GONE
                            trendingAdapter.setData(trending.data?.subList(6, trending.data!!.size))
                        }
                        is Resource.Error -> {
                            binding.progressBarTrending.visibility = View.GONE
                            binding.viewErrorTrending.root.visibility = View.VISIBLE
                            binding.viewErrorTrending.tvError.text = trending.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            })

            with(binding.rvTrending) {
                layoutManager = LinearLayoutManager(requireActivity())
                setHasFixedSize(true)
                adapter = trendingAdapter

            }

            trendingAdapter.setOnItemClickListener{
                val directions: NavDirections = HomeFragmentDirections.actionHomeFragmentToDetailActivity(it)

                findNavController().navigate(directions)
            }
        }

        return view
    }

}