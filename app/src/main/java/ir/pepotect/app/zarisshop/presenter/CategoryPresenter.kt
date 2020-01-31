package ir.pepotect.app.zarisshop.presenter

import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import ir.pepotect.app.zarisshop.model.ApiClient
import ir.pepotect.app.zarisshop.model.dataModel.CategoryModel
import ir.pepotect.app.zarisshop.model.localData.Pref
import org.json.JSONArray

class CategoryPresenter(private val listener: CategoryResult, private val cancelTag: String) {

    interface CategoryResult {
        fun result(ok: Boolean, message: String, data: ArrayList<CategoryModel>? = null) {}
    }

    fun getCategorys() {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonArrayResponse(data: JSONArray?) {
                val resultData = ArrayList<CategoryModel>()
                for (i in 0 until (data?.length() ?: 0)) {
                    resultData.add(
                        Gson().fromJson(
                            data?.get(i).toString(),
                            CategoryModel::class.java
                        )
                    )
                    resultData[i].imgSrc =
                        data?.getJSONObject(i)?.getJSONObject("image")?.getString("src") ?: ""
                }
                listener.result(true, "", resultData)
            }

            override fun errorInf(statusCode: String, message: String) {
                listener.result(false, "Error")
            }
        }, false).getJsonArray("https://api.myjson.com/bins/b7r5e", true, cancelTag)
        //}, false).getJsonArray("products/categories?", true, cancelTag)
    }

}