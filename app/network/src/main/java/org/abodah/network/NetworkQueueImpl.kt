package org.abodah.network

import android.content.Context
import android.os.Handler
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NetworkQueueImpl (ctx: Context) : NetworkQueue {
    private var volleyRequestQueue: RequestQueue = Volley.newRequestQueue(ctx)
    private val tag = "NET_QUEUE"

    init {
        Log.i(tag, "NetworkQueueImpl constructor was called")
    }

    private class RequestImpl<T> : Request<T> {
        private var tag = "NET_REQUEST"
        private var canceled = false
        private var cb: ((T) -> Unit)? = null

        override fun cancel() {
            Log.i(tag, "Canceled one request")
            canceled = true
        }

        override fun isCanceled() = canceled

        override fun success(cb: (T) -> Unit) {
            Log.i(tag, "Setting callback")
            this.cb = cb
        }

        override fun resolve(result: T) {
            if (cb == null) {
                Log.i(tag, "Callback is null")
            } else {
                Log.i(tag, "Callback is not null")
            }
            cb?.invoke(result)
        }
    }

    override fun infinitePostJsonObjectRequest(
        url: String, params: JSONObject, timeout: Long, savedRequest: Request<JSONObject>?
    ): Request<JSONObject> {
        Log.i(tag, "Starting loading json object from $url")
        val request = savedRequest ?: RequestImpl()
        val jsonObjectRequest = JsonObjectRequest(com.android.volley.Request.Method.POST, url, params, {
            Log.i(tag, "Successfully loaded json object from $url")
            if (!request.isCanceled()) {
                Log.i(tag, "Resolving")
                request.resolve(it)
            } else {
                Log.i(tag, "Request was canceled")
            }
        }, {
            Log.i(tag, "Bad result of loading from $url")
            Log.i(tag, it.toString())
            if (!request.isCanceled()) {
                Handler().postDelayed({
                    infinitePostJsonObjectRequest(url, params, timeout, request)
                }, timeout)
            }
        })
        volleyRequestQueue.add(jsonObjectRequest)
        return request
    }

    override fun infinitePostJsonObjectRequest(
        url: String,
        params: JSONObject,
        resolve: (JSONObject) -> Unit,
        timeout: Long
    ): Request<JSONObject> {
        val request = infinitePostJsonObjectRequest(url, params, timeout)
        request.success(resolve)
        return request
    }

    override fun infinitePostStringRequest(
        url: String, params: JSONObject, timeout: Long, savedRequest: Request<String>?
    ): Request<String> {
        val request = savedRequest ?: RequestImpl()
        val stringRequest = object : StringRequest(Method.POST, url, {
            if (!request.isCanceled()) {
                request.resolve(it)
            }
        }, {
            if (!request.isCanceled()) {
                Handler().postDelayed({
                    infinitePostStringRequest(url, params, timeout)
                }, timeout)
            }
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return params.toString().toByteArray()
            }
        }
        volleyRequestQueue.add(stringRequest)
        return request
    }

    override fun infinitePostStringRequest(
        url: String,
        params: JSONObject,
        resolve: (String) -> Unit,
        timeout: Long
    ): Request<String> {
        val request = infinitePostStringRequest(url, params, timeout)
        request.success(resolve)
        return request
    }
}