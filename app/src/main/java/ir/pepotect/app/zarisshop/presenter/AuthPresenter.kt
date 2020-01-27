package ir.pepotect.app.zarisshop.presenter

import com.orhanobut.hawk.Hawk
import ir.pepotect.app.zarisshop.model.ApiClient
import ir.pepotect.app.zarisshop.model.localData.Pref
import org.json.JSONObject

class AuthPresenter(private val listener: Result, private val cancelTag: String) {

    interface Result {
        fun result(ok: Boolean, message: String){}
    }

    fun register(phone: String) {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonObjectResponse(data: JSONObject?) {
                Hawk.put(Pref.phone, phone)
                listener.result(true, "")
            }

            override fun errorInf(statusCode: String, message: String) {
                listener.result(false, "Error")
            }
        }, false).postJsonObject("", JSONObject().apply {
            put("username", phone)
        }, tag = cancelTag)
    }

    fun checkVerifyCode(code: String) {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonObjectResponse(data: JSONObject?) {
                Hawk.put(Pref.logIn, true)
                listener.result(true, "")
            }

            override fun errorInf(statusCode: String, message: String) {
                listener.result(false, "Error")
            }
        }, false).postJsonObject("", JSONObject().apply {
            put("username", Hawk.get(Pref.phone, ""))
            put("code", code)
        })
    }

    fun requestVerifyCode() {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonObjectResponse(data: JSONObject?) {
                listener.result(true, "کد تایید به شماره همراه شما ارسال شد.")
            }

            override fun errorInf(statusCode: String, message: String) {
                listener.result(false, "خطا,لطفا مجددا تلاش کنید.")
            }
        }, false).postJsonObject("", JSONObject().apply {
            put("username", Hawk.get(Pref.phone, ""))
        })
    }

}