package com.example.chucknorrisjokes

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val URL = "https://api.icndb.com/jokes/random"
    var okHttpClient: OkHttpClient = OkHttpClient()

    val imageLink = arrayOf(
            "http://www.needgod.ru/assets/components/phpthumbof/cache/chak-norris.d7269340a23fc47c57a57a30d9fe943551.jpg",
            "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTE5NDg0MDU1MjQ5OTc4ODk1/chuck-norris-15720761-1-402.jpg",
            "https://sophosnews.files.wordpress.com/2012/01/chuck-norris-thumb.jpg?w=250",
            "https://vignette.wikia.nocookie.net/factpile/images/c/c8/Chuck-Norris-Twitter.png/revision/latest?cb=20160328233434",
            "http://cdn-scraplogo.pearltrees.com/9b/d6/9bd6040882fa2256b8c97e49620f7add-pearlsquare.jpg?v=2"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_get_joke.setOnClickListener {
            randomJoke()
            checkInternetConnection()
        }
    }

    private fun randomImage() {
        val r = Random
        val random = r.nextInt(imageLink.size)
        Picasso.get().load(imageLink[random]).into(iv_chak)
    }

    private fun randomJoke() {
        runOnUiThread {
            progress_bar.visibility = View.VISIBLE
        }
        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val json = response?.body()?.string()
                val txt = (JSONObject(json).getJSONObject("value").get("joke")).toString()

                runOnUiThread {
                    randomImage()
                    progress_bar.visibility = View.GONE
                    tv_joke.text = Html.fromHtml(txt)
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.run {
            putString("TEXT", tv_joke.text.toString())
            val drawable = iv_chak.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            putParcelable("IMAGE", bitmap)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        tv_joke.text = savedInstanceState?.getString("TEXT")
        iv_chak.setImageBitmap(savedInstanceState?.getParcelable("IMAGE"))
    }

    fun checkInternetConnection() {
        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = cm.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            /*Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()*/
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }
}
