package ir.pepotect.app.zarisshop.presenter

import com.google.gson.Gson
import ir.pepotect.app.zarisshop.model.ApiClient
import ir.pepotect.app.zarisshop.model.dataModel.*
import org.json.JSONArray
import kotlin.collections.ArrayList

class HomeListPresenter(private val listener: Result, private val cancelTag: String) {

    interface Result {
        fun tagTable(data: TagTable)
        fun productList(data: ProductList)
        fun timingProductList(data: TimingProductList)
        fun tagListVerticaly(data: TagList)
        fun tagListHorizontaly(data: TagList)
        fun homeListPresenterResult(ok: Boolean, message: String)
    }

    fun getHomeData() {
        ApiClient(object : ApiClient.ServerData {
            override fun jsonArrayResponse(data: JSONArray?) {
                for (i in 0 until (data?.length() ?: 0)) {
                    val type = data?.getJSONObject(i)?.getString("type") ?: ""
                    when (type.toLowerCase()) {
                        "pl" -> {
                            val products = ArrayList<Product>()
                            val sub = data?.getJSONObject(i)?.getString("sub") ?: ""
                            val jArray = data?.getJSONObject(i)?.getJSONArray("data")
                            for (j in 0 until (jArray?.length() ?: 0)) {
                                products.add(
                                    Gson().fromJson(
                                        jArray?.getJSONObject(j).toString(),
                                        Product::class.java
                                    )
                                )
                            }
                            val pl = ProductList("pl", sub, products)
                            listener.productList(pl)
                        }
                        "tpl" -> {
                            val products = ArrayList<Product>()
                            val sub = data?.getJSONObject(i)?.getString("sub") ?: ""
                            val endTime = data?.getJSONObject(i)?.getString("endTime") ?: 0
                            val jArray = data?.getJSONObject(i)?.getJSONArray("data")
                            for (j in 0 until (jArray?.length() ?: 0)) {
                                products.add(
                                    Gson().fromJson(
                                        jArray?.getJSONObject(j).toString(),
                                        Product::class.java
                                    )
                                )
                            }
                            val tpl = TimingProductList("tpl", sub, /*endTime*/0, products)
                            listener.timingProductList(tpl)
                        }
                        "tt" -> {
                            val products = ArrayList<TagItem>()
                            val sub = data?.getJSONObject(i)?.getString("sub") ?: ""
                            val columns = data?.getJSONObject(i)?.getInt("columns") ?: 0
                            val jArray = data?.getJSONObject(i)?.getJSONArray("data")
                            for (j in 0 until (jArray?.length() ?: 0)) {
                                products.add(
                                    Gson().fromJson(
                                        jArray?.getJSONObject(j).toString(),
                                        TagItem::class.java
                                    )
                                )
                            }
                            val tt = TagTable("tt", columns, sub, products)
                            listener.tagTable(tt)
                        }
                        "tlv" -> {
                            val products = ArrayList<TagItem>()
                            val jArray = data?.getJSONObject(i)?.getJSONArray("data")
                            for (j in 0 until (jArray?.length() ?: 0)) {
                                products.add(
                                    Gson().fromJson(
                                        jArray?.getJSONObject(j).toString(),
                                        TagItem::class.java
                                    )
                                )
                            }
                            val tlv = TagList("tlv", products)
                            listener.tagListVerticaly(tlv)
                        }
                        "tlh" -> {
                            val products = ArrayList<TagItem>()
                            val jArray = data?.getJSONObject(i)?.getJSONArray("data")
                            for (j in 0 until (jArray?.length() ?: 0)) {
                                products.add(
                                    Gson().fromJson(
                                        jArray?.getJSONObject(j).toString(),
                                        TagItem::class.java
                                    )
                                )
                            }
                            val tlv = TagList("tlh", products)
                            listener.tagListHorizontaly(tlv)
                        }
                    }
                }
                listener.homeListPresenterResult(true, "")
            }

            override fun errorInf(statusCode: String, message: String) {
                listener.homeListPresenterResult(false, "خطا در دریافت اطلاعات لطفا اتصال اینترنت خود را چک کنید.")
            }
        }).getJsonArray("https://api.myjson.com/bins/hw082", false, cancelTag)
    }

}