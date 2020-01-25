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
        val resultData = ArrayList<CategoryModel>()
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        resultData.add(CategoryModel(0,"<color name=\"secondaryTextColor\">#000000</color>",0,"",""))
        listener.result(true, "", resultData)
/*        ApiClient(object : ApiClient.ServerData {
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
        }).getJsonArray("", Hawk.get(Pref.authenticationToken, "products/categories"), cancelTag)*/
    }

}