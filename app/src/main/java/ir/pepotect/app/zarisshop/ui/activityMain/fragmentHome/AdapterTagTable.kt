package ir.pepotect.app.zarisshop.ui.activityMain.fragmentHome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.TagItem
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.loadImage
import ir.pepotect.app.zarisshop.ui.uses.loadImageWithBounds
import kotlinx.android.synthetic.main.item_tag_table.view.*

class AdapterTagTable(private val data: ArrayList<TagItem>) :
    RecyclerView.Adapter<AdapterTagTable.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder =
        MyHolder(LayoutInflater.from(App.ctx).inflate(R.layout.item_tag_table, parent, false))

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.apply {
            with(data[position])
            {
                txtSubItemTable.text = sub
                loadImageWithBounds(imgSrc, imgItemTable)
            }
        }
    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)

}