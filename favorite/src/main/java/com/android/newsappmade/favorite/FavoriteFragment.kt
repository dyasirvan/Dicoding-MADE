package com.android.newsappmade.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.core.ui.FavoriteAdapter
import com.android.newsappmade.favorite.databinding.FragmentFavoriteBinding
import com.android.newsappmade.favorite.di.favoriteModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

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

        loadKoinModules(favoriteModule)

        val favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.setOnItemClickListener {

            val directions = FavoriteFragmentDirections.actionFavoriteFragment2ToDetailActivity(it)
            findNavController().navigate(directions)

        }

        favoriteViewModel.favoriteNews.observe(viewLifecycleOwner, {
            favoriteAdapter.setData(it)
            binding.viewEmpty.root.visibility = if(it.isNotEmpty()) View.GONE else View.VISIBLE
            Log.d("FavoriteFragment", "onCreateView: $it")
        })

        with(binding.rvFavorite){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }

        return view
    }

}