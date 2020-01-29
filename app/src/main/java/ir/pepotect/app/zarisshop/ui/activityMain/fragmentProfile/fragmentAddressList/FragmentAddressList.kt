package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.fragmentAddressList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.Address
import ir.pepotect.app.zarisshop.presenter.UserPresenter
import ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.FragmentProfile
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.dialog.DialogProgress
import kotlinx.android.synthetic.main.fargment_address_list.*

class FragmentAddressList : MyFragment() {

    lateinit var parent: FragmentProfile
    private lateinit var progress: DialogProgress

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(ctx).inflate(R.layout.fargment_address_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = DialogProgress(ctx)
        getAddressList()
        btnAddAddress.setOnClickListener {
            DialogAddress().show()
        }
    }

    private fun getAddressList() {
        progress.show()
        UserPresenter(object : UserPresenter.Result {
            override fun address(ok: Boolean, message: String, data: ArrayList<Address>?) {
                progress.cancel()
                if (ok)
                    setUpRV(data!!)
                else
                    toast(message)
            }
        }, cancelTag).getUserAddress()
    }


    private fun setUpRV(data: java.util.ArrayList<Address>) {
        RVAddress.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = AdapterAddress(data)
        }
    }

}