package ir.pepotect.app.zarisshop.ui.activityMain.fragmentCategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.CategoryModel
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.loadImage
import kotlinx.android.synthetic.main.item_category.view.*
import kotlin.collections.ArrayList

class AdapterCategory(private val data: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<AdapterCategory.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder =
        MyHolder(
            LayoutInflater.from(App.ctx)
                .inflate(R.layout.item_category, parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.apply {
            with(data[position])
            {
                txtItemCategory.text = name
                loadImage(imgSrc, imgItemCategory)
                setOnClickListener {

                }
            }
        }
    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)

}