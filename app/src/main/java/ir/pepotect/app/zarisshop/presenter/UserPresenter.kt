package ir.pepotect.app.zarisshop.presenter

import ir.pepotect.app.zarisshop.model.ApiClient
import ir.pepotect.app.zarisshop.model.dataModel.Address
import ir.pepotect.app.zarisshop.model.dataModel.User
import org.json.JSONArray
import org.json.JSONObject

class UserPresenter(private val listener: Result, private val cancelTag: String) {

    interface Result {
        fun result(ok: Boolean, message: String) {}
        fun userData(ok: Boolean, message: String, data: User?) {}
        fun address(ok: Boolean, message: String, data: ArrayList<Address>?) {}
    }

    fun updateUserInfo(name: String, family: String, email: String) {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonObjectResponse(data: JSONObject?) {
                listener.result(true, "")
            }

            override fun errorInf(statusCode: String, message: String) {
                listener.result(false, "خطا,اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید")
            }
        }).putJsonObject("", JSONObject(), cancelTag)
    }

    fun getUserInfo() {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonObjectResponse(data: JSONObject?) {
                listener.userData(true, "", User("mostafa", "ghanbari", "", "g@g.com"))
            }

            override fun errorInf(statusCode: String, message: String) {
                listener.userData(false, "خطا,در دریافت اطلاعات کاربری", null)
            }
        }).getJsonObject("", true, cancelTag)
    }

    fun getUserAddress() {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonArrayResponse(data: JSONArray?) {
                listener.address(true,"", null)
            }

            override fun errorInf(statusCode: String, message: String) {
                val resultData = ArrayList<Address>()
                resultData.add(Address("خانه", "بلوارانقلاب انقلاب34", "4", "2"))
                resultData.add(
                    Address(
                        "پارک",
                        "بلوار دانشگاه پارک علم و فناوری",
                        "ندارد",
                        "فناوران یک"
                    )
                )
                resultData.add(Address("خانه", "بلوارانقلاب انقلاب34", "4", "2"))
                resultData.add(
                    Address(
                        "پارک",
                        "بلوار دانشگاه پارک علم و فناوری",
                        "ندارد",
                        "فناوران یک"
                    )
                )
                listener.address(true, "", resultData)
            }
        }).getJsonArray("", true, cancelTag)
    }

}