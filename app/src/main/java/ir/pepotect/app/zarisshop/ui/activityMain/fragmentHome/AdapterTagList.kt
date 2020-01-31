package ir.pepotect.app.zarisshop.ui.activityMain.fragmentHome

import android.app.Activity
import android.graphics.Point
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.TagItem
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.loadImage
import ir.pepotect.app.zarisshop.ui.uses.loadImageWithBounds
import kotlinx.android.synthetic.main.item_tag_list.view.*

class AdapterTagList(private val data: ArrayList<TagItem>, private val type: String) :
    RecyclerView.Adapter<AdapterTagList.MyHolder>() {

    val p = Point()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            (App.ctx as Activity).windowManager.defaultDisplay.getRealSize(p)
        } else
            (App.ctx as Activity).windowManager.defaultDisplay.getSize(p)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder =
        MyHolder(LayoutInflater.from(App.ctx).inflate(R.layout.item_tag_list, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.apply {
            loadImageWithBounds(data[position].imgSrc, imgItemWide)
            if (type == "tlh") {
                layoutParams.width = (p.x * 0.9).toInt()
            }
        }
    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)
}