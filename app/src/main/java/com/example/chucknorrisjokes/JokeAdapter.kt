package com.example.chucknorrisjokes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class JokeAdapter(private val jokeList: ArrayList<Joke>) : RecyclerView.Adapter<JokeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_layout, p0, false)

        val holder = ViewHolder(v)

        v.setOnClickListener {
            /*val index = holder.adapterPosition
            jokeList.removeAt(index)

            //notifyItemRemoved(index)
            notifyDataSetChanged()*/
            Toast.makeText(holder.itemView.context, "deleted", Toast.LENGTH_SHORT).show()
        }

        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return jokeList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        val joke: Joke = jokeList[p1]

        p0.textJoke.text = joke.joke

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textJoke = itemView.findViewById(R.id.tv_joke) as TextView
    }


}