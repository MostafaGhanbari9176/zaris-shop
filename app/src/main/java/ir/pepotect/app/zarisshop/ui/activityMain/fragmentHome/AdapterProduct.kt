package ir.pepotect.app.zarisshop.ui.activityMain.fragmentHome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.Product
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.loadImage
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterProduct(private val data: ArrayList<Product>) :
    RecyclerView.Adapter<AdapterProduct.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder =
        MyHolder(LayoutInflater.from(App.ctx).inflate(R.layout.item_product, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.apply {
            with(data[position]) {
                txtSubItemProduct.text = name
                txtPriceItemProduct.text = price
                txtRebateItemProduct.text = "%"+rebate.toString()
                loadImage(imgSrc, imgItemProduct)
            }
        }
    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)

}