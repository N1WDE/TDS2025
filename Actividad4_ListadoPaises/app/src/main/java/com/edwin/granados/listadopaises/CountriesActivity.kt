package com.edwin.granados.listadopaises

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class CountriesActivity : AppCompatActivity() {
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())
    private val adapter = CountryAdapter()
    private var allCountries: List<Country> = emptyList()

    private lateinit var list: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var emptyState: TextView
    private lateinit var retryButton: Button
    private lateinit var counter: TextView
    private lateinit var search: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        list = findViewById(R.id.countriesList)
        progress = findViewById(R.id.loadingProgress)
        emptyState = findViewById(R.id.emptyState)
        retryButton = findViewById(R.id.btnRetry)
        counter = findViewById(R.id.countryCounter)
        search = findViewById(R.id.searchCountries)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        retryButton.setOnClickListener { loadCountries() }

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCountries(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
        loadCountries()
    }

    private fun loadCountries() {
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
        emptyState.visibility = View.GONE
        retryButton.visibility = View.GONE
        search.isEnabled = false

        executor.execute {
            try {
                val result = CountryApi.getCountries()
                mainHandler.post {
                    allCountries = result
                    search.isEnabled = true
                    progress.visibility = View.GONE
                    list.visibility = View.VISIBLE
                    counter.text = "${result.size} países encontrados"
                    filterCountries(search.text.toString())
                }
            } catch (error: Exception) {
                mainHandler.post {
                    progress.visibility = View.GONE
                    list.visibility = View.GONE
                    emptyState.text = "No fue posible cargar los países.\n\nVerifica tu conexión a Internet e inténtalo de nuevo.\n\nDetalle: ${error.localizedMessage ?: "Error de conexión"}"
                    emptyState.visibility = View.VISIBLE
                    retryButton.visibility = View.VISIBLE
                    search.isEnabled = false
                }
            }
        }
    }

    private fun filterCountries(query: String) {
        if (allCountries.isEmpty()) return
        val filtered = if (query.isBlank()) allCountries else allCountries.filter {
            it.name.contains(query.trim(), ignoreCase = true) ||
                it.region.contains(query.trim(), ignoreCase = true) ||
                it.alphaCode.contains(query.trim(), ignoreCase = true)
        }
        adapter.submitItems(filtered)
        counter.text = if (query.isBlank()) "${allCountries.size} países encontrados"
            else "${filtered.size} resultados"
        emptyState.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        if (filtered.isEmpty()) emptyState.text = "No se encontraron países con ese criterio."
        list.visibility = if (filtered.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onDestroy() {
        executor.shutdownNow()
        super.onDestroy()
    }
}
