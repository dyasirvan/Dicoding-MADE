package com.android.newsappmade.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.android.newsappmade.R
import com.android.newsappmade.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private val detailNewsViewModel: DetailNewsViewModel by viewModel()

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        val article = args.article

        binding.tvTitle.text = article.title
        binding.tvContent.text = article.content
        binding.tvAuthor.text = article.author
        binding.tvDescription.text = article.description
        binding.tvSource.text = ""
        binding.tvShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }

        val date = article.publishedAt?.replace("T", " \u2022 ")?.replace("Z", "")

        binding.tvDate.text = date
        Glide.with(this)
            .load(article.urlToImage)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
            .into(binding.imgThumbnail)

        binding.fabExpand.setOnClickListener {
            onExpandedButton()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        var statusFavorite = article.isFavorite
        setStatusFavorite(statusFavorite)
        binding.fabFav.setOnClickListener {
            statusFavorite = !statusFavorite
            detailNewsViewModel.setFavoriteTourism(article, statusFavorite)
            setStatusFavorite(statusFavorite)
        }

        binding.fabShare.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${getString(R.string.message_share)} ${article.url}")
                type = "text/plain"
            }
            Intent.createChooser(intent, getString(R.string.share_with))
            startActivity(intent)
        }
    }

    private fun onExpandedButton() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            binding.fabFav.startAnimation(fromBottom)
            binding.fabShare.startAnimation(fromBottom)
            binding.fabExpand.startAnimation(rotateOpen)
        }else{
            binding.fabFav.startAnimation(toBottom)
            binding.fabShare.startAnimation(toBottom)
            binding.fabExpand.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            binding.fabFav.visibility = View.VISIBLE
            binding.fabShare.visibility = View.VISIBLE
        }else{
            binding.fabFav.visibility = View.INVISIBLE
            binding.fabShare.visibility = View.INVISIBLE
        }
    }

    private fun setClickable(clicked: Boolean){
        if(!clicked){
            binding.fabFav.isClickable = true
            binding.fabShare.isClickable = true
        }else{
            binding.fabFav.isClickable = false
            binding.fabShare.isClickable = false
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24))
        } else {
            binding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_outline_favorite))
        }
    }
}