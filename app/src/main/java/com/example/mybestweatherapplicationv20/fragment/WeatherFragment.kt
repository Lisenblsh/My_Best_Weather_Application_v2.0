package com.example.mybestweatherapplicationv20.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.mybestweatherapplicationv20.databinding.FragmentWeatherBinding
import com.example.mybestweatherapplicationv20.funStuf.*
import com.example.mybestweatherapplicationv20.model.MainForecast
import com.example.mybestweatherapplicationv20.retrofit.Constrains
import com.example.mybestweatherapplicationv20.viewmodel.WeatherViewModel
import com.github.florent37.viewanimator.ViewAnimator
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            animation()
        }, 2000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentWeatherViewModel()
        swipeUpdate()
        searchCity()
    }

    @SuppressLint("SetTextI18n")
    private fun currentWeatherViewModel() {

        viewModel.currentWeatherResp.observe(viewLifecycleOwner, { currentWeather ->
            binding.apply {
                val temp = currentWeather.main.temp.roundToInt()
                val tempFeelsLike = currentWeather.main.feelsLike.roundToInt()
                val colorCode = getColorForBG(tempFeelsLike)
                binding.colorLayout.setBackgroundColor(Color.parseColor("#$colorCode"))
                binding.bgForImg.setBackgroundColor(Color.parseColor("#$colorCode"))
                binding.colorDetailsBg.setBackgroundColor(Color.parseColor("#4a$colorCode"))

                val maxTemp = currentWeather.main.tempMax.roundToInt()
                val minTemp = currentWeather.main.tempMin.roundToInt()
                val windDegText = getWindDeg(currentWeather.wind.deg)
                val windSpeed = currentWeather.wind.speed.roundToInt()
                val humidity = currentWeather.main.humidity
                val pressure = currentWeather.main.pressure

                val cityTimeZone = currentWeather.timezone
                val sunriseInt = currentWeather.sys.sunrise
                val sunsetInt = currentWeather.sys.sunset
                val sunrise = getTimeSunriseSunset(sunriseInt, cityTimeZone)
                val sunset = getTimeSunriseSunset(sunsetInt, cityTimeZone)
                var weatherIcon = getImageForBG(cityTimeZone, sunriseInt, sunsetInt)
                if (weatherIcon == "d") {
                    imgSun.visibility = View.VISIBLE
                    imgMoon.visibility = View.GONE
                } else {
                    imgMoon.visibility = View.VISIBLE
                    imgSun.visibility = View.GONE
                }

                val weatherDescription =
                    currentWeather.weather.first().description.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                weatherIcon += currentWeather.weather.first().id

                //Вывод
                binding.temp.text = "$temp°C"
                binding.maxTemp.text = "Максимум: $maxTemp°C"
                binding.minTemp.text = "Минимум: $minTemp°C"
                binding.feelsLike.text = "Ощущается как $tempFeelsLike°C"
                binding.updateAt.text =
                    "${currentWeather.name}, ${getDateUpdate(currentWeather.dt)}"
                binding.windDeg.text = windDegText
                binding.windSpeed.text = "$windSpeed м/с"
                binding.humidity.text = "${humidity}%"
                binding.pressure.text = "$pressure гПа"
                binding.sunrise.text = sunrise
                binding.sunset.text = sunset
                binding.weatherImage.setImageURI(Uri.parse("android.resource://${activity?.packageName}/drawable/$weatherIcon"))
                binding.weatherImageOutLine.setImageURI(Uri.parse("android.resource://${activity?.packageName}/drawable/$weatherIcon"))
                binding.weatherDescription.text = weatherDescription

                forecastWeatherViewModel()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun forecastWeatherViewModel() {
        viewModel.forecastWeatherResp.observe(viewLifecycleOwner, { forecastWeather ->
            binding.apply {
                val listWeather = forecastWeather.list
                val listOfWeather: Map<Int, Map<MainForecast, Int>> =
                    mapOf(
                        0 to mapOf(listWeather.first().main to listWeather[0].weather.first().id),
                        1 to mapOf(listWeather.first().main to listWeather[1].weather.first().id),
                        2 to mapOf(listWeather.first().main to listWeather[2].weather.first().id),
                        3 to mapOf(listWeather.first().main to listWeather[3].weather.first().id),
                        4 to mapOf(listWeather.first().main to listWeather[4].weather.first().id),
                        5 to mapOf(listWeather.first().main to listWeather[5].weather.first().id),
                    )
                val listOfTimeView: List<TextView> =
                    listOf(
                        binding.timeForecast1,
                        binding.timeForecast2,
                        binding.timeForecast3,
                        binding.timeForecast4,
                        binding.timeForecast5,
                        binding.timeForecast6
                    )
                val listOfTempView: List<TextView> =
                    listOf(
                        binding.tempForecast1,
                        binding.tempForecast2,
                        binding.tempForecast3,
                        binding.tempForecast4,
                        binding.tempForecast5,
                        binding.tempForecast6
                    )
                val listOfImageView: List<ImageView> =
                    listOf(
                        binding.imgForecast1,
                        binding.imgForecast2,
                        binding.imgForecast3,
                        binding.imgForecast4,
                        binding.imgForecast5,
                        binding.imgForecast6
                    )
                val cityTimeZone = forecastWeather.city.timezone * 1000
                for (i in 0..5) {
                    val weatherIcon = listOfWeather[i]?.values?.first()
                    listOfTimeView[i].text = getTimeForecast(listWeather[i].dtTxt, cityTimeZone)
                    listOfTempView[i].text =
                        "${listOfWeather[i]?.keys?.first()?.tempMax?.roundToInt()}°C/${listOfWeather[1]?.keys?.first()?.tempMin?.roundToInt()}°C"
                    listOfImageView[i].setImageURI(Uri.parse("android.resource://${activity?.packageName}/drawable/d$weatherIcon"))
                }
            }
        })
    }

    private fun swipeUpdate() {
        val swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            isOnline(requireContext())
            if (!NoInternet.noInternet) {
                viewModel.updateData()
                noInternet()
                Handler().postDelayed({
                    swipeRefreshLayout.isRefreshing = false
                }, 600)
            }else {
                noInternet()
                swipeRefreshLayout.isRefreshing = false

            }
        }
    }

    private fun searchCity() {
        binding.cityText.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    isOnline(requireContext())
                    if (!NoInternet.noInternet) {
                        Constrains.city = binding.cityText.text.toString().trim()
                        viewModel.updateData()
                        noInternet()
                    } else {
                        noInternet()

                    }
                    binding.cityText.setText("")
                    val imm: InputMethodManager =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun animation() {
        ViewAnimator.animate(binding.loadScene).duration(2500).translationX(1000f).alpha(0f).start()
        ViewAnimator.animate(binding.colorLayout).duration(2500).alpha(0.1f).start()
        ViewAnimator.animate(binding.secondContainer).duration(1800).translationX(30f).alpha(0f, 1f)
            .thenAnimate(binding.secondContainer).duration(200).translationX(0f).start()
        ViewAnimator.animate(binding.sunMoon).duration(1800).translationX(30f).alpha(0f, 1f)
            .thenAnimate(binding.sunMoon).duration(200).translationX(0f).start()
        ViewAnimator.animate(binding.mainContainer).duration(1400).translationX(35f).alpha(1f)
            .thenAnimate(binding.mainContainer).duration(200).translationX(0f).start()
        ViewAnimator.animate(binding.updContainer).duration(1000).translationX(40f).alpha(1f)
            .thenAnimate(binding.updContainer).duration(200).translationX(0f).start()
        ViewAnimator.animate(binding.bgForImg).duration(1000).translationX(40f).alpha(0.3f)
            .thenAnimate(binding.bgForImg).duration(200).translationX(0f).start()
    }

    private fun noInternet() {
        if(!NoInternet.noInternet) {
            binding.apply {
                errorText.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                errorText.visibility = View.VISIBLE
            }
        }
    }

}