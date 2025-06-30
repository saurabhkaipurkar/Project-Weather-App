package com.project.weather

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.project.weather.models.WeatherResponse
import com.project.weather.viewmodel.WeatherViewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.project.weather.adapters.WeatherForecastAdapter
import com.project.weather.apiservices.RetrofitInstance
import com.project.weather.databinding.ActivityMainBinding
import com.project.weather.repository.WeatherRepository
import com.project.weather.viewmodel.WeatherViewmodelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var viewmodel: WeatherViewmodel
    private lateinit var getData : WeatherResponse
    private lateinit var shimmerLayout : ShimmerFrameLayout
    private lateinit var adapter : WeatherForecastAdapter
    private val toolbox = MethodLibrary()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shimmerLayout = findViewById(R.id.shimmerLayout) // or use binding if included
        shimmerLayout.startShimmer()

        val repository = WeatherRepository(RetrofitInstance.retrofit)
        val factory = WeatherViewmodelFactory(repository)
        viewmodel = ViewModelProvider(this, factory)[WeatherViewmodel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()
        locationFinder()
        requestPermission()

        binding.searchCity.setOnEditorActionListener { textView, actionId, event ->
            if ((actionId == EditorInfo.IME_ACTION_SEND) ||
                (actionId == EditorInfo.IME_ACTION_SEARCH) ||
                ((event != null) && (event.keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN))
            ) {
                val cityName = binding.searchCity.text?.trim().toString()
                if (cityName.isNotEmpty()) {
                    postCity(cityName)
                }else{
                    Toast.makeText(this, "Please Enter City Name", Toast.LENGTH_SHORT).show()
                }

                true
            }else{
                false
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                getUserLocation()
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun requestPermission(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissionLauncher.launch(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }else{
            getUserLocation()
        }
    }

    private fun getUserLocation(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback,mainLooper)
        }
    }

    private fun locationFinder(){
        locationCallback = object : LocationCallback() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val lastLocation: Location? = p0.lastLocation
                if (lastLocation != null) {
                    val lat = lastLocation.latitude
                    val lon = lastLocation.longitude
                    fetchingData(lat,lon)
                    takeForecastData(lat,lon)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }
    }

    private fun takeStateData(cityName: String){
        viewmodel.fetchStateName(cityName)
        viewmodel.stateName.observe(this) {response ->
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = GONE
            binding.mainlayout.visibility = VISIBLE
            binding.stateName.text = response[0].state
        }
        viewmodel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchingData(lat: Double, lon: Double){
        
        viewmodel.getWeatherData(lat,lon)
        viewmodel.response.observe(this) {response ->
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = GONE
            binding.mainlayout.visibility = VISIBLE
            getData = response
            updateUI(getData)
            weatherIconChanger(getData)
            takeStateData(getData.name)
        }
        viewmodel.error.observe(this) {error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postCity(cityName: String){
        viewmodel.getCityWeatherData(cityName)
        viewmodel.cityResponse.observe(this) {cityResponse ->
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = GONE
            binding.mainlayout.visibility = VISIBLE
            getData = cityResponse
            updateUI(getData)
            weatherIconChanger(getData)
            takeStateData(getData.name)
            binding.searchCity.text?.clear()
        }
        viewmodel.error.observe(this) {cityError ->
        }
    }

    private fun takeForecastData(lat: Double, lon: Double){
        viewmodel.fetchForecast(lat,lon)
        viewmodel.forecast.observe(this) { response ->
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = GONE
            binding.mainlayout.visibility = VISIBLE
            adapter = WeatherForecastAdapter(response)
            binding.forecastHour.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.forecastHour.adapter = adapter

        }
        viewmodel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun updateUI(getData: WeatherResponse) {
        binding.cityName.text = "${getData.name}, "
        binding.mainTemp.text = toolbox.kelvinToCelsius(getData.main.temp).toString() + "°C"
        binding.maxandminTemp.text = "Max " + toolbox.kelvinToCelsius(getData.main.temp_max) + "°C" + " | " +
                                        "Min " + toolbox.kelvinToCelsius(getData.main.temp_min) + "°C"
        binding.skydispcrit.text = getData.weather[0].description
        binding.feelsLike.text = "Feel Like ${toolbox.kelvinToCelsius(getData.main.feels_like)} °C"
        binding.humidityValue.text = "${ getData.main.humidity } °C"
        binding.cloudsValue.text = getData.clouds.all.toString() + "%"
        binding.sunriseValue.text = toolbox.timeStampConvertor(getData.sys.sunrise)
        binding.sunsetValue.text = toolbox.timeStampConvertor(getData.sys.sunset)
        binding.visibilityValue.text = "${getData.visibility/1000} Km"
        binding.timeofdata.text = toolbox.timeStampConvertor(getData.dt)
        binding.windspeed.text = "${getData.wind.speed * 3.6} km/h"
        binding.pressureValue.text = getData.main.pressure.toString()
    }
    private fun weatherIconChanger(getData: WeatherResponse){
        val weatherIcon = when (getData.weather[0].main){
            "Clouds" -> R.drawable.clouds
            "Clear" -> R.drawable.sun
            "Rain" -> R.drawable.rain
            "Snow" -> R.drawable.snow
            "Thunderstorm" -> R.drawable.thunderstorm
            else -> R.drawable.sun
        }
        binding.weathericon.visibility = VISIBLE
        binding.weathericon.setImageResource(weatherIcon)
    }
}