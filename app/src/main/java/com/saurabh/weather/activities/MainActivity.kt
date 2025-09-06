package com.saurabh.weather.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.saurabh.weather.R
import com.saurabh.weather.adapters.WeatherForecastAdapter
import com.saurabh.weather.apiservices.RetrofitInstance
import com.saurabh.weather.databinding.ActivityMainBinding
import com.saurabh.weather.models.WeatherResponse
import com.saurabh.weather.repository.WeatherRepository
import com.saurabh.weather.utils.MethodLibrary
import com.saurabh.weather.viewmodel.WeatherViewmodel
import com.saurabh.weather.viewmodel.WeatherViewmodelFactory
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var viewmodel: WeatherViewmodel
    private lateinit var getData: WeatherResponse
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private lateinit var adapter: WeatherForecastAdapter
    private val toolbox = MethodLibrary()
    private var isRefreshing = false // Track refresh state

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup SwipeRefreshLayout
        setupSwipeRefresh()

        // Initialize shimmer layout
        val view = LayoutInflater.from(this).inflate(R.layout.skeleton_weather_screen, binding.mainLayout, false)
        shimmerLayout = view.findViewById(R.id.shimmerLayout)
        binding.mainLayout.addView(view)
        setupShimmerEffect()

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
                    showShimmer()
                    postCity(cityName)
                } else {
                    Toast.makeText(this, "Please Enter City Name", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            isRefreshing = true
            refreshWeatherData()
        }

        // Optional: Customize refresh colors
        binding.swipeRefresh.setColorSchemeResources(
            R.color.accent_color,
            R.color.text_color
        )
    }

    private fun refreshWeatherData() {
        // If we have location permissions, get current location data
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getUserLocation()
        } else {
            // If no location permission, just stop the refresh indicator
            stopRefreshing()
            Toast.makeText(this, "Location permission needed for refresh", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRefreshing() {
        isRefreshing = false
        binding.swipeRefresh.isRefreshing = false
    }

    private fun setupShimmerEffect() {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1500)
            .setBaseAlpha(1f)
            .setHighlightAlpha(1f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        shimmerLayout.setShimmer(shimmer)
        showShimmer()
    }

    private fun showShimmer() {
        // Only show shimmer if not refreshing (to avoid conflict with SwipeRefreshLayout)
        if (!isRefreshing) {
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.startShimmer()
            binding.mainLayout.visibility = View.GONE
        }
    }

    private fun hideShimmer() {
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.GONE
        binding.mainLayout.visibility = View.VISIBLE

        // Stop refresh indicator if it was refreshing
        if (isRefreshing) {
            stopRefreshing()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                getUserLocation()
            } else {
                // Even if permission is denied, we can show some default data
                hideShimmer()
            }
        }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getUserLocation()
        }
    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showShimmer()
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                mainLooper
            )
        }
    }

    private fun locationFinder() {
        locationCallback = object : LocationCallback() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val lastLocation: Location? = p0.lastLocation
                if (lastLocation != null) {
                    val lat = lastLocation.latitude
                    val lon = lastLocation.longitude
                    fetchingData(lat, lon)
                    takeForecastData(lat, lon)
                    airQualityIndex(lat, lon)
                    fusedLocationClient.removeLocationUpdates(this)
                } else {
                    hideShimmer()
                    Toast.makeText(this@MainActivity, "Unable to get location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun takeStateData(cityName: String) {
        viewmodel.fetchStateName(cityName)
        viewmodel.stateName.observe(this) { response ->
            binding.stateName.text = response[0].state
        }
        viewmodel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            hideShimmer() // Make sure to hide shimmer on error
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchingData(lat: Double, lon: Double) {
        viewmodel.getWeatherData(lat, lon)
        viewmodel.response.observe(this) { response ->
            getData = response
            updateUI(getData)
            weatherIconChanger(getData)
            takeStateData(getData.name)
            hideShimmer()
        }
        viewmodel.error.observe(this) { error ->
            hideShimmer()
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postCity(cityName: String) {
        viewmodel.getCityWeatherData(cityName)
        viewmodel.cityResponse.observe(this) { cityResponse ->
            getData = cityResponse
            updateUI(getData)
            weatherIconChanger(getData)
            takeStateData(getData.name)
            binding.searchCity.text?.clear()
            hideShimmer()
        }
        viewmodel.error.observe(this) { cityError ->
            hideShimmer()
            Toast.makeText(this, cityError, Toast.LENGTH_SHORT).show()
        }
    }

    private fun takeForecastData(lat: Double, lon: Double) {
        viewmodel.fetchForecast(lat, lon)
        viewmodel.forecast.observe(this) { response ->
            adapter = WeatherForecastAdapter(response)
            binding.forecastHour.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.forecastHour.adapter = adapter
        }
        viewmodel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            hideShimmer() // Make sure to hide shimmer on error
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun updateUI(getData: WeatherResponse) {
        binding.cityName.text = "${getData.name}, "
        binding.mainTemp.text = toolbox.kelvinToCelsius(getData.main.temp).toString() + "°C"
        binding.maxandminTemp.text =
            "Max " + toolbox.kelvinToCelsius(getData.main.temp_max) + "°C" + " | " +
                    "Min " + toolbox.kelvinToCelsius(getData.main.temp_min) + "°C"
        binding.skydispcrit.text = getData.weather[0].description
        binding.feelsLike.text = "Feel Like ${toolbox.kelvinToCelsius(getData.main.feels_like)} °C"
        binding.humidityValue.text = "${getData.main.humidity} °C"
        binding.cloudsValue.text = getData.clouds.all.toString() + "%"
        binding.visibilityValue.text = "${getData.visibility / 1000} Km"
        binding.windspeed.text = String.format(Locale.getDefault(), "%.1f km/h", getData.wind.speed * 3.6)
        binding.pressureValue.text = getData.main.pressure.toString()
    }

    private fun weatherIconChanger(getData: WeatherResponse) {
        val weatherIcon = when (getData.weather[0].main) {
            "Clouds" -> R.drawable.clouds
            "Clear" -> R.drawable.sun
            "Rain" -> R.drawable.rain
            "Snow" -> R.drawable.snow
            "Thunderstorm" -> R.drawable.thunderstorm
            else -> R.drawable.sun
        }
        binding.weatherIcon.visibility = View.VISIBLE
        binding.weatherIcon.setImageResource(weatherIcon)
    }

    private fun airQualityIndex(lat: Double, lon: Double) {
        viewmodel.fetchAirQuality(lat, lon)
        viewmodel.airQuality.observe(this) { response ->
            val aqiValue = response.list[0].main.aqi
            val aqiDescription = toolbox.getAQIDescription(aqiValue)
            binding.AQIValue.text = String.format(Locale.getDefault(), "AQI: %d (%s)", aqiValue,aqiDescription)
        }
        viewmodel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            hideShimmer() // Make sure to hide shimmer on error
        }
    }
}