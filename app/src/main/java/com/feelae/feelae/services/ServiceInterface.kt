package com.feelae.feelae.services

import org.json.JSONArray
import org.json.JSONObject

interface ServiceInterface {
    fun post(path: String, token: String?, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun get(path: String, token: String?, completionHandler: (response: JSONArray?) -> Unit)
}