package ir.pepotect.app.zarisshop.ui.activityOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.Order
import ir.pepotect.app.zarisshop.presenter.OrderPresenter
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.dialog.DialogProgress
import kotlinx.android.synthetic.main.content_common.*
import kotlinx.android.synthetic.main.fragment_order_history.*
import kotlinx.android.synthetic.main.row_order_history.view.*

class FragmentOrderHistory : MyFragment() {

    private lateinit var progress: DialogProgress
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = LayoutInflater.from(ctx).inflate(R.layout.fragment_order_history, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = DialogProgress(ctx)
        init()
    }

    private fun init() {
        getOrderHistory()
    }

    private fun getOrderHistory() {
        progress.show()
        OrderPresenter(object : OrderPresenter.Result {
            override fun orderHistory(
                ok: Boolean,
                message: String,
                data: ArrayList<Order>?
            ) {
               progress.cancel()
                if (ok) {
                    setUpTable(data)
                } else
                    toast(message)
            }
        }, cancelTag).getOrderHistory()
    }

    private fun setUpTable(data: java.util.ArrayList<Order>?) {
        data?.forEach { orderHistory ->
            val row = LayoutInflater.from(ctx).inflate(R.layout.row_order_history, null, false)
            row.apply {
                rowOrderHistoryOId.text = orderHistory.orderId
                rowOrderHistoryDate.text = orderHistory.date
                rowOrderHistoryCost.text = orderHistory.cost
                rowOrderHistoryState.text = orderHistory.state
                btnRowOrderHistory.setOnClickListener {
                    showOrderDetails(orderHistory.orderId)
                }
            }
            TLOrderHistory.addView(row)
        }
    }

    private fun showOrderDetails(id: String) {
        toast(id)
        (App.ctx as ActivityOrder).apply {
            LBLCC.text = id
            changeView(FragmentOrder().apply {orderId = id})
        }
    }

}