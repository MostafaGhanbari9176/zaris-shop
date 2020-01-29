package ir.pepotect.app.zarisshop.model.dataModel

data class User(
    val name:String,
    val family:String,
    val avatar:String,
    val email:String
)

data class Address(
    val sub:String,
    val address:String,
    val pelak:String,
    val vahed:String
)