package com.pengfei.network.request

import androidx.collection.SimpleArrayMap
import com.google.gson.Gson
import com.pengfei.network.config.LocalCookieJar
import com.pengfei.network.config.OkhttpLogInterceptor
import com.pengfei.network.config.RetryInterceptor
import com.pengfei.network.support.IHttpCallback
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 *
 */
class OkHttpApi : HttpApi {

    companion object {
        private const val TAG = "OkHttpApi"//Tag
    }

    private var baseUrl = "http://api.qingyunke.com/"
    var maxRetry = 0//最大重试 次数

    //存储请求，用于取消
    private val callMap = SimpleArrayMap<Any, Call>()

    //okHttpClient
    private val mClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回数据，默认值0，不限定,
        .connectTimeout(10, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(10, TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)//重连
        .followRedirects(false)//重定向
        .cache(Cache(File("sdcard/cache", "okhttp"), 1024))
//        .cookieJar(CookieJar.NO_COOKIES)
        .cookieJar(LocalCookieJar())
        .addNetworkInterceptor(OkhttpLogInterceptor {
            logLevel(OkhttpLogInterceptor.LogLevel.BODY)
            logTag("HHHDH")
        })//添加网络拦截器，可以对okHttp的网络请求做拦截处理，不同于应用拦截器，这里能感知所有网络状态，比如重定向。
        .addNetworkInterceptor(RetryInterceptor(maxRetry))
        .build()

    override fun get(params: Map<String, Any>, path: String, callback: IHttpCallback) {
        val url = "$baseUrl$path"
        val urlBuilder = url.toHttpUrl().newBuilder()
        params.forEach { entry ->
            urlBuilder.addEncodedQueryParameter(entry.key, entry.value.toString())
        }
        val request = Request.Builder()
            .get()
            .tag(params)
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()
        val newCall = mClient.newCall(request)
        //存储请求，用于取消
        callMap.put(request.tag(), newCall)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }
        })
    }

    override fun post(body: Any, path: String, callback: IHttpCallback) {
        val url = "$baseUrl$path"
        val request = Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .url(url)
            .tag(body)
            .build()

        val newCall = mClient.newCall(request)
        //存储请求，用于取消
        callMap.put(request.tag(), newCall)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }

        })
    }


    /**
     * 取消网络请求，tag就是每次请求的id 标记，也就是请求的传参
     */
    override fun cancelRequest(tag: Any) {
        callMap.get(tag)?.cancel()
    }

    /**
     * 取消所有网络请求
     */
    override fun cancelAllRequest() {
        for (i in 0 until callMap.size()) {
            callMap.get(callMap.keyAt(i))?.cancel()
        }
    }
}