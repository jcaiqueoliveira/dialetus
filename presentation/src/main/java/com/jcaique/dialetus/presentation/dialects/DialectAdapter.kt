package com.jcaique.dialetus.presentation.dialects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.presentation.R
import kotlinx.android.synthetic.main.item_dialect.view.*

internal class DialectAdapter(
    private val presentation: DialectsPresentation,
    private val onShareClicked: (Dialect) -> Unit
) : RecyclerView.Adapter<DialectAdapter.DialectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialectHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dialect, parent, false)
        return DialectHolder(view)
    }

    override fun getItemCount() = presentation.dialects.size

    override fun onBindViewHolder(holder: DialectHolder, position: Int) {
        holder.bind(presentation.dialects[position])
    }

    inner class DialectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(row: Dialect) = itemView.apply {
            dialectTitle.text = row.dialect
            dialectMeaning.text = row.meanings.joinToString(separator = "\n")
            dialectExamples.text = row.examples.joinToString(separator = "\n")

            dialectShare.setOnClickListener { onShareClicked(row) }
        }
    }
}
