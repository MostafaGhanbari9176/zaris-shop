package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.fragmentAddressList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.Address
import ir.pepotect.app.zarisshop.ui.App
import kotlinx.android.synthetic.main.item_address.view.*

class AdapterAddress(private val data:ArrayList<Address>):RecyclerView.Adapter<AdapterAddress.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder =
        MyHolder(LayoutInflater.from(App.ctx).
            inflate(R.layout.item_address, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        (holder.itemView).apply {
         with(data[position]){
             txtSubItemAddress.text = sub
             txtItemAddress.text = address + " پلاک " + pelak + " واحد " + vahed
         }
        }
    }

    class MyHolder(v: View):RecyclerView.ViewHolder(v)

}