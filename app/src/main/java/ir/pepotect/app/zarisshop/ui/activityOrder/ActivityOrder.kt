package ir.pepotect.app.zarisshop.ui.activityOrder

import android.os.Bundle
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.MyActivity
import kotlinx.android.synthetic.main.content_common.*

class ActivityOrder : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.ctx = this
        setContentView(R.layout.content_common)
        init()
    }

    private fun init() {
        changeView(FragmentOrderHistory())
        setSupportActionBar(tlbCC)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        LBLCC.text = "تاریخچه سفارشات"
        tlbCC.setNavigationOnClickListener {
            onBackPressed()
        }

    }
    override fun onBackPressed() {
        LBLCC.text = "تاریخچه سفارشات"
        super.onBackPressed()
    }

}