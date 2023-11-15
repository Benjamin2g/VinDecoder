package com.example.vingenerator

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vingenerator.api.Service
import com.example.vingenerator.api.data
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

const val URL = "https://vpic.nhtsa.dot.gov/"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var vin ="3GYFNEE36ES665586"
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val buttonListener = findViewById<Button>(R.id.button)
         buttonListener.setOnClickListener {
             val vinString = findViewById<Button>(R.id.textInput)
             vin = vinString.text.toString()
             getData()
         }

    }
    fun getData(){
        val api: Service = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java)
        GlobalScope.launch {
            val response : Response<data> = api.getData(vin).awaitResponse() // Added suspend to getData() function
            if (response.isSuccessful){
                val data: data = response.body()!!
                // Assigning response to my string builder
                val myStringBuilder = StringBuffer()
                myStringBuilder.append(data.Results.get(0).Make)
                myStringBuilder.append("\n")
                myStringBuilder.append(data.Results.get(0).Model)
                myStringBuilder.append("\n")
                myStringBuilder.append(data.Results.get(0).ModelYear)
                myStringBuilder.append("\n")
                myStringBuilder.append(data.Results.get(0).FuelTypePrimary)



                Log.d("MY RESPONSE", myStringBuilder.toString())
                // Adding vehicle information to the text view
                val resultTextView: TextView = findViewById(R.id.textView)
                resultTextView.text = myStringBuilder
                Log.d("HERE", myStringBuilder.toString())
            }
        }
    }
}