package ir.pepotect.app.zarisshop.model.dataModel

data class OrderProduct(
    val orderId:String,
    val productId:String,
    val productImg:String,
    val productName:String,
    val num:Int,
    val unitCost:String,
    val totalCost:String
)

data class Order(
    val orderId:String,
    val date:String,
    val cost:String,
    val state:String,
    val stateId:Int
)