package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.fragmentAddressList.FragmentAddressList
import ir.pepotect.app.zarisshop.ui.activityOrder.ActivityOrder
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.changeValuePresenter
import kotlinx.android.synthetic.main.fargment_address_list.*
import kotlinx.android.synthetic.main.fragment_accessibility.*

class FragmentAccessibility : MyFragment() {

    lateinit var parent: FragmentProfile

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(ctx).inflate(R.layout.fragment_accessibility, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getCache()
        btnUserInfo.setOnClickListener {
            parent.changeView(FragmentUserInfo().apply {
                this.parent = this@FragmentAccessibility.parent
            })
        }
        `btnَAddressList`.setOnClickListener {
            parent.changeView(FragmentAddressList().apply {
                this.parent = this@FragmentAccessibility.parent
            })
        }
        btnOrderHistory.setOnClickListener {
            startActivity(Intent(ctx, ActivityOrder::class.java))
        }
    }

    private fun getCache() {
        pbCacheProfile.visibility = View.GONE
        txtCacheNumber.text = "25000"
        txtCache.text = changeValuePresenter("25000")
    }

}