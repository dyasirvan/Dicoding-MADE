package com.android.newsappmade.core.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.newsappmade.R
import com.android.newsappmade.core.domain.model.Article
import com.android.newsappmade.databinding.ItemHeadlineBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList

class HeadlineAdapter: RecyclerView.Adapter<HeadlineAdapter.HeadlineViewHolder>() {
    private var listData = ArrayList<Article>()

    fun setData(newListData: List<Article>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class HeadlineViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val binding = ItemHeadlineBinding.bind(itemView)
        fun bind(data: Article){
            with(binding){
                tvTitle.text = data.title
                tvSource.text = data.source?.name
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(binding.imgThumbnail)
                btnShare.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${itemView.context.getString(R.string.message_share)} ${data.url}")
                        type = "text/plain"
                    }
                    Intent.createChooser(intent, itemView.context.getString(R.string.share_with))
                    itemView.context.startActivity(intent)
                }
                itemView.setOnClickListener {
                    onItemCLickListener?.let { it(data) }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder =
        HeadlineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_headline, parent, false))

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return if(listData.size > 5){
            5
        }else{
            listData.size
        }
    }

    private var onItemCLickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article)-> Unit){
        onItemCLickListener = listener
    }

}