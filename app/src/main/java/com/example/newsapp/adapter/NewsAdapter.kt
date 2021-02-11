package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.database.model.response.ArticlesItem
import com.example.newsapp.utils.load

/**
 * The NotesAdapter.kt to populate the recyclerview
 */
class NewsAdapter(
    private val noteItemClickListener: NoteItemClickListener
) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private val newsList: ArrayList<ArticlesItem> = ArrayList()

    fun setItems(newsData: List<ArticlesItem>) {
        newsData.run {
            newsList.clear()
            newsList.addAll(this)
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //creating views
        var mainLayout = view.findViewById(R.id.mainLayout) as CardView
        var imgView = view.findViewById(R.id.imgView) as ImageView
        var tvTitle = view.findViewById(R.id.tvTitle) as TextView
        var tvDetails = view.findViewById(R.id.tvDetails) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //returning the View Holder
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_ui, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //populating the data from list to view
        val note = newsList[position]
        note.run {
            holder.tvTitle.text = title
            holder.tvDetails.text = description
            holder.imgView.load(urlToImage)

            //attaching the onCLick to the layout
            holder.mainLayout.setOnClickListener {
                noteItemClickListener.onItemClickListener(this)
            }
        }
    }

    //interface to get the callback
    interface NoteItemClickListener {
        fun onItemClickListener(note: ArticlesItem)
    }
}