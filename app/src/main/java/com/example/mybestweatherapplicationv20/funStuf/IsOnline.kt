package com.example.mybestweatherapplicationv20.funStuf

import android.content.Context
import android.net.NetworkInfo

import android.net.ConnectivityManager


fun isOnline(context: Context) {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    NoInternet.noInternet = !(netInfo != null && netInfo.isConnectedOrConnecting)
}