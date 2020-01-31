package ir.pepotect.app.zarisshop.presenter

import ir.pepotect.app.zarisshop.model.ApiClient
import ir.pepotect.app.zarisshop.model.dataModel.Order
import ir.pepotect.app.zarisshop.model.dataModel.OrderProduct
import org.json.JSONArray

class OrderPresenter(private val listener: Result, private val cancelTag: String) {

    interface Result {
        fun orderHistory(ok: Boolean, message: String, data: ArrayList<Order>?) {}
        fun order(ok:Boolean, message: String, data:ArrayList<OrderProduct>?){}
    }

    fun getOrderHistory() {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonArrayResponse(data: JSONArray?) {

            }

            override fun errorInf(statusCode: String, message: String) {
                val data = ArrayList<Order>()
                data.add(Order("ZS-121511", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-121222", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-121533", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-121544", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-121555", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-121566", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-121577", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-121588", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-12154", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-12154", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-12154", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-12154", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-12154", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-12154", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-12154", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-12154", "1398/02/02", "25000", "Delivered", 1))
                data.add(Order("ZS-121599", "1398/02/02", "2595200", "Delivered", 1))
                listener.orderHistory(true, "", data)
            }
        }).getJsonArray("", true, cancelTag)
    }

    fun getOrder(orderId:String) {
        ApiClient(object:ApiClient.ServerData{
            override fun jsonArrayResponse(data: JSONArray?) {
                listener.order(true,"",null)
            }
            override fun errorInf(statusCode: String, message: String) {
                val resultData = ArrayList<OrderProduct>()
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                resultData.add(OrderProduct("O-548", "O-5454", "img", "سیم ظرف شویی",5,"1000","5000"))
                listener.order(true, "", resultData)
            }
        }).getJsonObject("", true, cancelTag)
    }

}