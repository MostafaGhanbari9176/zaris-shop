package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.fragmentAddressList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.AddressModel
import ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.FragmentProfile
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import kotlinx.android.synthetic.main.fargment_address_list.*

class FragmentAddressList : MyFragment() {

    lateinit var parent:FragmentProfile

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(ctx).
            inflate(R.layout.fargment_address_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = ArrayList<AddressModel>()
        data.add(AddressModel("خانه", "بلوارانقلاب انقلاب34", "4", "2"))
        data.add(AddressModel("پارک", "بلوار دانشگاه پارک علم و فناوری", "ندارد", "فناوران یک"))
        data.add(AddressModel("خانه", "بلوارانقلاب انقلاب34", "4", "2"))
        data.add(AddressModel("پارک", "بلوار دانشگاه پارک علم و فناوری", "ندارد", "فناوران یک"))
        setUpRV(data)
    }

    private fun setUpRV(data: java.util.ArrayList<AddressModel>) {
        RVAddress.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = AdapterAddress(data)
        }
    }

}