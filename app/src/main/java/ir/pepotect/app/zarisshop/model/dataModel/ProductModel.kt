package ir.pepotect.app.zarisshop.model.dataModel

data class Product(
    val name:String,
    val id:String,
    val imgSrc:String,
    val price:String,
    val rebate:Int,
    val catId:String,
    val catName:String
)

data class CategoryModel(
    val id:Int,
    val name:String,
    val parent:Int,
    val description:String,
    var imgSrc:String
)