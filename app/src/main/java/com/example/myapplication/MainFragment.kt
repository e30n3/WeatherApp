package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
import android.text.format.Time
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {

    val args: MainFragment by navArgs()
    private lateinit var navController: NavController

    private var fIcon: String = ""
    private lateinit var sPref: SharedPreferences

    /*  private fun saveText() {
          sPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
          val ed = sPref.edit()
          ed.putString("CITY", textCity.text.toString())
          ed.putString("LATLONG", textLatLong.text.toString())
          ed.putString("DESC", textDescription.text.toString())
          ed.putString("TEMP", textTemp.text.toString())
          ed.putString("FEELS", textFeels.text.toString())
          ed.putString("DET", textCurrentDetails.text.toString())
          ed.putString("ICON", fIcon)
          ed.apply()
      }*/

    /*    private fun loadText() {
          sPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
          var savedText = sPref.getString("CITY", "")
          textCity.text = savedText
          savedText = sPref.getString("LATLONG", "")
          textLatLong.text = savedText
          savedText = sPref.getString("DESC", "")
          textDescription.text = savedText
          savedText = sPref.getString("TEMP", "")
          textTemp.text = savedText
          savedText = sPref.getString("FEELS", "")
          textFeels.text = savedText
          savedText = sPref.getString("DET", "")
          textCurrentDetails.text = savedText
          savedText = sPref.getString("ICON", "")
          Log.i("ICON", fIcon)
          if (savedText != null) {
              fIcon = savedText
          }
          when (fIcon) {
              "01d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l01d))
              "01n" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l01n))
              "02d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l02d))
              "02n" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l02n))
              "03d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l03d))
              "03n" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l03n))
              "04d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l04d))
              "04n" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l04n))
              "09d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l09d))
              "10n" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l10n))
              "10d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l10d))
              "11n" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l11n))
              "11d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l11d))
              "13n" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l13n))
              "13d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l13d))
              "50n" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l50n))
              "50d" -> imageIcon.setImageDrawable(resources.getDrawable(R.drawable.l50d))
          }
      }*/

    internal inner class Weather : AsyncTask<String?, Void?, String?>() {
        override fun doInBackground(vararg p0: String?): String? {
            try {
                val url = URL(p0[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val `is` = connection.inputStream
                val isr = InputStreamReader(`is`)
                var data = isr.read()
                var content = ""
                var ch: Char
                while (data != -1) {
                    ch = data.toChar()
                    content += ch
                    data = isr.read()
                }
                Log.i("Content", content)
                return content
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentLocation() {
        progressBar.progressiveStart()
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED && context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        activity?.let {
            LocationServices.getFusedLocationProviderClient(it)
                    .requestLocationUpdates(locationRequest, object : LocationCallback() {
                        @SuppressLint("SetTextI18n")
                        override fun onLocationResult(locationResult: LocationResult) {
                            super.onLocationResult(locationResult)
                            LocationServices.getFusedLocationProviderClient(it)
                                    .removeLocationUpdates(this)
                            if (locationResult.locations.size > 0) {
                                val latestLocationIndex = locationResult.locations.size - 1
                                val latitude = locationResult.locations[latestLocationIndex].latitude
                                val longitude = locationResult.locations[latestLocationIndex].longitude
                                textLatLong.text = String.format(
                                        "Latitude: %s\nLongitude: %s",
                                        latitude,
                                        longitude
                                )
                                val weather = Weather()
                                try {
                                    val content: String = weather.execute("https://openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&appid=439d4b804bc8187953eb36d2a8c26a02").get()!!
                                    Log.i("contentData", content)
                                    val jsonObject = JSONObject(content)
                                    val weatherData = jsonObject.getString("weather")
                                    val mainTemperature = jsonObject.getString("main")
                                    val wind = jsonObject.getString("wind")
                                    val cityName = jsonObject.getString("name")
                                    Log.i("weatherData", weatherData)
                                    val array = JSONArray(weatherData)
                                    val date = Date()
                                    var main = ""
                                    var description = ""
                                    var icon = ""
                                    val temperature: String
                                    val feelsLike: String
                                    val pressure: String
                                    val humidity: String
                                    val windSpeed: String
                                    for (i in 0 until array.length()) {
                                        val weatherPart = array.getJSONObject(i)
                                        main = weatherPart.getString("main")
                                        description = weatherPart.getString("description")
                                        icon = weatherPart.getString("icon")
                                    }
                                    val mainPart = JSONObject(mainTemperature)
                                    temperature = mainPart.getString("temp")
                                    feelsLike = mainPart.getString("feels_like")
                                    pressure = mainPart.getString("pressure")
                                    humidity = mainPart.getString("humidity")
                                    val windPart = JSONObject(wind)
                                    windSpeed = windPart.getString("speed")
                                    Log.i("main", main)
                                    Log.i("description", description)
                                    Log.i("icon", icon)
                                    Log.i("temp", temperature)
                                    Log.i("city", cityName)
                                    fIcon = icon
                                    when (icon) {
                                        "01d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l01d, null))
                                        "01n" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l01n, null))
                                        "02d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l02d, null))
                                        "02n" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l02n, null))
                                        "03d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l03d, null))
                                        "03n" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l03n, null))
                                        "04d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l04d, null))
                                        "04n" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l04n, null))
                                        "09d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l09d, null))
                                        "10n" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l10n, null))
                                        "10d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l10d, null))
                                        "11n" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l11n, null))
                                        "11d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l11d, null))
                                        "13n" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l13n, null))
                                        "13d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l13d, null))
                                        "50n" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l50n, null))
                                        "50d" -> iconImageView.setImageDrawable(getDrawable(resources, R.drawable.l50d, null))
                                    }
                                    description = description.substring(0, 1).toUpperCase(Locale.ROOT) + description.substring(1)
                                    textDescription.text = description
                                    textMain.text = "$temperature℃"
                                    textFeels.text = "Feels like: $feelsLike℃"
                                    val sm =
                                            SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT)
                                    textCity.text = "$cityName, ${sm.format(date)}"
                                    textCurrentDetails.text = "Current details:\n\n" +
                                            "Pressure          $pressure mBar\n" +
                                            "Humidity          $humidity%\n" +
                                            "Wind speed     $windSpeed m/s\n"
                                    //TODO пофиксить
                                    //saveText()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    val toast = Toast.makeText(context,
                                            "No internet connection", Toast.LENGTH_SHORT)
                                    toast.show()
                                }
                            }
                            progressBar.progressiveStop()
                        }
                    }, Looper.getMainLooper())
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.progressiveStop()
        //TODO: пофиксить
        //loadText()
        swipeRefresh.setOnRefreshListener {
            if (context?.let {
                        ContextCompat.checkSelfPermission(
                                it, Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED) {
                activity?.let {
                    ActivityCompat.requestPermissions(
                            it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_CODE_LOCATION_PERMISSION
                    )
                }
                swipeRefresh.isRefreshing = false
            } else {
                getCurrentLocation()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_LOCATION_PERMISSION = 1
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

}