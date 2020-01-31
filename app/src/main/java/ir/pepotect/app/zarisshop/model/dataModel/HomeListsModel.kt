package ir.pepotect.app.zarisshop.model.dataModel

data class ProductList(
    val type: String,
    val sub: String,
    val data: ArrayList<Product>
)

data class TimingProductList(
    val type: String,
    val sub: String,
    val endTime: Long,
    val data: ArrayList<Product>
)

data class TagList(
    val type: String,//tlv    tlh
    val data: ArrayList<TagItem>
)

data class TagTable(
    val type: String,
    val columns: Int,
    val sub: String,
    val data: ArrayList<TagItem>
)

data class TagItem(
    val tag: String,
    val sub: String,
    val imgSrc: String
)