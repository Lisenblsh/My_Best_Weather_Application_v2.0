package com.example.mybestweatherapplicationv20

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import com.example.mybestweatherapplicationv20.retrofit.Constrains
import org.json.JSONObject
import java.net.URL

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        GetCity().execute()
    }

    fun start(){
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetCity: AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String? {
            val response = try {
                URL("http://ipwhois.app/json/?lang=ru")
                    .readText()
            } catch (e: Exception) {
                null
            }
            return response
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            try{
                val json = JSONObject(result).getString("city")
                Constrains.city = json
                start()
            }catch (e: Exception) {
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }

    }
}