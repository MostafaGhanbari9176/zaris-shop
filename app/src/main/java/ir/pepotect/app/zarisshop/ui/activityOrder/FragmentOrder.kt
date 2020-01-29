package ir.pepotect.app.zarisshop.ui.activityOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.OrderProduct
import ir.pepotect.app.zarisshop.presenter.CartPresenter
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.dialog.DialogProgress
import ir.pepotect.app.zarisshop.ui.uses.loadImage
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.row_order.view.*

class FragmentOrder:MyFragment() {

    private lateinit var progress:DialogProgress
    lateinit var orderId:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(ctx).inflate(R.layout.fragment_order, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()
    {
        progress = DialogProgress(ctx)
        getOrder()
    }

    private fun getOrder() {
        progress.show()
        CartPresenter(object:CartPresenter.Result{
            override fun order(ok: Boolean, message: String, data: ArrayList<OrderProduct>?) {
                progress.cancel()
                if(ok)
                setUpTable(data)
                else
                    toast(message)
            }
        }, cancelTag).getOrder(orderId)
    }

    private fun setUpTable(data: ArrayList<OrderProduct>?) {
        data?.forEach { product ->
            val row = LayoutInflater.from(ctx).inflate(R.layout.row_order, null, false)
            row.apply {
                loadImage(product.productImg, row.rowOrderImg)
                rowOrderProductName.text = product.productName
                rowOrderProductNum.text = product.num.toString()
                rowOrderProductCost.text = product.unitCost
                rowOrderTotalCost.text = product.totalCost
                btnRowOrder.setOnClickListener {
                    showProductDetails(product.productId)
                }
            }
            TLOrder.addView(row)
        }

    }

    private fun showProductDetails(productId: String) {
        toast(productId)
    }

}