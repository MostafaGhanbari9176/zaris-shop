package ir.pepotect.app.zarisshop.model

import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.orhanobut.hawk.Hawk
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.*
import com.android.volley.Response.success
import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import ir.pepotect.app.zarisshop.model.localData.Pref
import ir.pepotect.app.zarisshop.model.uses.VolleyMultipartRequest
import ir.pepotect.app.zarisshop.ui.App


class ApiClient(private val listener: ServerData? = null, private val dataCaching: Boolean = true) {

    private var cachedData = false

    interface ServerData {
        fun jsonObjectResponse(data: JSONObject?) {}
        fun jsonArrayResponse(data: JSONArray?) {}
        fun errorInf(statusCode: String, message: String) {}
    }

    private data class CacheModel(
        val cacheEntry: Cache.Entry,
        val data: String
    )

    companion object {
        var queue: RequestQueue? = null
            get() {
                if (field == null) {
                    field = Volley.newRequestQueue(App.ctx)
                    (field as RequestQueue).cache.clear()
                    return field
                }
                return field
            }
    }

    fun getJsonObject(url: String, authentication: Boolean = false, tag: String = "main") {
        val request = object : JsonObjectRequest(Request.Method.GET,
            if (url.contains("http")) url else App.baseUrl + url,
            null,
            { response: JSONObject? ->
                listener?.jsonObjectResponse(response)
            }
            ,
            { error: VolleyError ->
                if (!(error.message?.contains("Unable to resolve host") == true && cachedData)) {
                    listener?.errorInf(
                        error.networkResponse?.statusCode.toString()
                            ?: "", error.networkResponse?.data.toString() ?: ""
                    )
                }
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> = mutableMapOf<String, String>()
                .apply {
                    put("Content-Type", "application/json")
                    if (authentication)
                        put("Authorization", "token " + Hawk.get(Pref.authenticationToken, ""))
                }

            override fun getBodyContentType(): String = "application/json; charset=utf-8"

            override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject> {
                val cacheModel = setUpCache(response)
                return success(JSONObject(cacheModel.data), cacheModel.cacheEntry)
            }

            override fun addMarker(tag: String) {
                super.addMarker(tag)
                if (tag.contains("cache-hit", true))
                    cachedData = true
                if (tag.contains("cache-miss", true))
                    cachedData = false

            }
        }

        //request.retryPolicy = DefaultRetryPolicy(1, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        if (!dataCaching)
            queue?.cache?.invalidate(request.url, true)
        request.tag = tag
        queue?.add<JSONObject>(request)
    }

    fun getJsonArray(url: String, authentication: Boolean, tag: String = "main") {
        val request = object : JsonArrayRequest(Request.Method.GET, if(url.contains("http", true)) url else App.baseUrl + url, null,
            { response ->
                listener?.jsonArrayResponse(response)
            },
            { error ->
                if (error.message?.contains("Unable to resolve host") == true && !cachedData) {
                    listener?.errorInf(
                        error.networkResponse?.statusCode.toString()
                            ?: "", error.networkResponse?.data.toString() ?: ""
                    )
                }
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> =
                mutableMapOf<String, String>().apply {
                    put("Content-Type", "application/json")
                    if (authentication)
                        put("Authorization", "token " + Hawk.get(Pref.authenticationToken, ""))
                }

            override fun getBodyContentType(): String = "application/json; charset=utf-8"

            override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONArray> {
                val cacheModel = setUpCache(response)
                return success(JSONArray(cacheModel.data), cacheModel.cacheEntry)
            }

            override fun addMarker(tag: String) {
                super.addMarker(tag)
                if (tag.contains("cache-hit", true))
                    cachedData = true
                if (tag.contains("cache-miss", true))
                    cachedData = false

            }

        }
        if (!dataCaching)
            queue?.cache?.invalidate(request.url, true)
        request.tag = tag
        queue?.add(request)
    }

    fun postJsonObject(
        url: String,
        data: JSONObject,
        authentication: Boolean = false,
        tag: String = "main"
    ) {
        val request: JsonObjectRequest =
            object : JsonObjectRequest(Method.POST, App.baseUrl + url, null,
                Response.Listener { response ->
                    listener?.jsonObjectResponse(response)
                }, Response.ErrorListener { error ->
                    listener?.errorInf(
                        error.networkResponse?.statusCode.toString()
                            ?: "", error.networkResponse?.data?.toString(charset("utf-8")) ?: ""
                    )
                }) {
                override fun getBody(): ByteArray? {
                    val requestBody = data.toString()
                    return try {
                        requestBody.toByteArray(charset("utf-8"))
                    } catch (e: Exception) {
                        null
                    }
                }

                override fun getBodyContentType(): String = "application/json; charset=utf-8"

                override fun getHeaders(): MutableMap<String, String> =
                    mutableMapOf<String, String>().apply {
                        put("Content-Type", "application/json")
                        if (authentication)
                            put("Authorization", "token " + Hawk.get(Pref.authenticationToken, ""))
                    }

            }
        //request.retryPolicy = DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        request.tag = tag
        queue?.add<JSONObject>(request)
    }

    fun putJsonObject(url: String, data: JSONObject, tag: String = "main") {
        val request: JsonObjectRequest =
            object : JsonObjectRequest(Method.PUT, App.baseUrl + url, null,
                Response.Listener { response ->
                    listener?.jsonObjectResponse(response)
                }, Response.ErrorListener { error ->
                    listener?.errorInf("404", "Error")

                }) {
                override fun getBody(): ByteArray? {
                    val requestBody = data.toString()
                    return try {
                        requestBody.toByteArray(charset("utf-8"))
                    } catch (e: Exception) {
                        null
                    }
                }

                override fun getBodyContentType(): String = "application/json; charset=utf-8"

                override fun getHeaders(): MutableMap<String, String> =
                    mutableMapOf<String, String>().apply {
                        put("Content-Type", "application/json")
                        put("Authorization", "token " + Hawk.get(Pref.authenticationToken, ""))
                    }

            }
        //request.retryPolicy = DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        request.tag = tag
        queue?.add<JSONObject>(request)
    }

    fun deleteRequest(url: String, auth: Boolean = false, tag: String = "main") {
        val request = object : JsonObjectRequest(Request.Method.DELETE,
            if (url.contains("http")) url else App.baseUrl + url,
            null,
            { response: JSONObject? ->
                listener?.jsonObjectResponse(response)
            }
            ,
            { error: VolleyError ->
                listener?.errorInf(
                    error.networkResponse?.statusCode.toString()
                        ?: "", error.networkResponse?.data.toString() ?: ""
                )
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> = mutableMapOf<String, String>()
                .apply {
                    put("Content-Type", "application/json")
                    if (auth)
                        put("Authorization", "token " + Hawk.get(Pref.authenticationToken, ""))
                }

            override fun getBodyContentType(): String = "application/json; charset=utf-8"
        }

        //request.retryPolicy = DefaultRetryPolicy(1, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        request.tag = tag
        queue?.add<JSONObject>(request)
    }

    fun putMultipart(url: String, data: ByteArray?, tag: String = "main") {
        val request = object : VolleyMultipartRequest(Method.PUT,App.baseUrl + url,
            { response ->
                try {
                    listener?.jsonObjectResponse(JSONObject(response.data.toString()))
                } catch (e: java.lang.Exception) {
                    listener?.jsonObjectResponse(JSONObject().apply { put("ok", "ok") })
                }
            }
            ,
            { error: VolleyError ->
                listener?.errorInf(
                    error.networkResponse?.statusCode.toString()
                        ?: "", error.networkResponse?.data.toString() ?: ""
                )
            }) {
            override fun getHeaders(): MutableMap<String, String> = mutableMapOf<String, String>()
                .apply {
                   // put("Content-Type", "application/json")
                    put("Authorization", "token " + Hawk.get(Pref.authenticationToken, ""))
                }

            override fun getByteData(): MutableMap<String, DataPart> =
                mutableMapOf<String, DataPart>().apply {
                    put("avatar", DataPart("avatar.png", data))
                }
        }
        request.retryPolicy = DefaultRetryPolicy(2000000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue?.add(request)
    }

    fun cancelRequest(tag: String) {
        queue?.cancelAll { request ->
            request.tag == tag
        }
    }

    private fun setUpCache(response: NetworkResponse?): CacheModel {
        var cacheEntry: Cache.Entry? = HttpHeaderParser.parseCacheHeaders(response)
        if (cacheEntry == null) {
            cacheEntry = Cache.Entry()
        }
        val cacheHitButRefreshed =
            (3 * 60 * 1000).toLong() // in 3 minutes cache will be hit, but also refreshed on background
        val cacheExpired =
            (24 * 60 * 60 * 1000).toLong() // in 24 hours this cache entry expires completely
        val now = System.currentTimeMillis()
        val softExpire = now + cacheHitButRefreshed
        val ttl = now + cacheExpired
        cacheEntry.data = response?.data
        cacheEntry.softTtl = softExpire
        cacheEntry.ttl = ttl
        var headerValue: String? = response!!.headers["Date"]
        if (headerValue != null) {
            cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue)
        }
        headerValue = response.headers["Last-Modified"]
        if (headerValue != null) {
            cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue)
        }
        cacheEntry.responseHeaders = response.headers
        val jsonString = String(
            response.data,
            Charset.forName("UTF-8")
        )
        return CacheModel(cacheEntry, jsonString)
    }

}