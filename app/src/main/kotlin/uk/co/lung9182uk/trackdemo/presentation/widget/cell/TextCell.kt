package uk.co.lung9182uk.trackdemo.presentation.widget.cell

import android.content.Context
import com.jaychang.srv.kae.SimpleCell
import com.jaychang.srv.kae.SimpleViewHolder
import kotlinx.android.synthetic.main.cell_text.view.*
import uk.co.lung9182uk.trackdemo.util.L

class TextCell(item: Pair<String,String>) : SimpleCell<Pair<String,String>>(item) {

    override fun getLayoutRes(): Int {
        return L.cell_text
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int, context: Context, payload: Any?) {
        holder.itemView.nameView.text = item.first
        holder.itemView.valueView.text =item.second
    }

}