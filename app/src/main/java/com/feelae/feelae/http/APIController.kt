package com.feelae.feelae.http

import org.json.JSONArray
import org.json.JSONObject

//https://www.varvet.com/blog/kotlin-with-volley/

class APIController constructor(serviceInjection: ServiceInterface) : ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun post(path: String, token: String?, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.post(path, token, params, completionHandler)
    }

    override fun get(path: String, token: String?, completionHandler: (response: JSONArray?) -> Unit) {
        service.get(path, token, completionHandler)
    }

}