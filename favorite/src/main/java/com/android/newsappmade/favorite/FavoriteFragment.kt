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
import org.koin.core.context.unloadKoinModules

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        val favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.setOnItemClickListener {

            val directions = FavoriteFragmentDirections.actionFavoriteFragment2ToDetailActivity(it)
            findNavController().navigate(directions)

        }

        favoriteViewModel.favoriteNews.observe(viewLifecycleOwner, {
            favoriteAdapter.setData(it)
            binding?.viewEmpty?.root?.visibility = if(it.isNotEmpty()) View.GONE else View.VISIBLE
            Log.d("FavoriteFragment", "onCreateView: $it")
        })

        with(binding?.rvFavorite){
            this?.layoutManager = LinearLayoutManager(requireContext())
            this?.adapter = favoriteAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(favoriteModule)
    }

}