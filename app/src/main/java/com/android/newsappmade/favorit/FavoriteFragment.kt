package com.android.newsappmade.favorit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.newsappmade.R
import com.android.newsappmade.core.ui.FavoriteAdapter
import com.android.newsappmade.databinding.FragmentFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root

        val favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putParcelable("article", it)
            }
            findNavController().navigate(
                R.id.action_favoriteFragment_to_detailActivity,
                bundle
            )
        }

        favoriteViewModel.favoriteNews.observe(viewLifecycleOwner, {
            favoriteAdapter.setData(it)
            binding.viewEmpty.root.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            Log.d("FavoriteFragment", "onCreateView: $it")
        })

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        return view
    }

}