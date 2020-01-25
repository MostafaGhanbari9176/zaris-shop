package ir.pepotect.app.zarisshop.model.dataModel

data class CategoryModel(
    val id:Int,
    val name:String,
    val parent:Int,
    val description:String,
    var imgSrc:String
)