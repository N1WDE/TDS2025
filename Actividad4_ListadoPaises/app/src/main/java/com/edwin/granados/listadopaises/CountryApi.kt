package com.edwin.granados.listadopaises

import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

/** Consulta REST Countries v5 y recorre sus páginas hasta completar el catálogo. */
object CountryApi {
    private const val BASE_URL = "https://api.restcountries.com/countries/v5"
    private const val DEMO_TOKEN = "rc_live_demo"
    private const val PAGE_SIZE = 100
    private const val RESPONSE_FIELDS =
        "names.common,names.translations,codes.alpha_2,flag.url_png,region"

    fun getCountries(): List<Country> {
        val result = mutableListOf<Country>()
        var offset = 0
        var hasMore = true

        while (hasMore) {
            val url = URL(
                "$BASE_URL?limit=$PAGE_SIZE&offset=$offset&response_fields=$RESPONSE_FIELDS"
            )
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 15_000
                readTimeout = 20_000
                setRequestProperty("Accept", "application/json")
                setRequestProperty("Authorization", "Bearer $DEMO_TOKEN")
                setRequestProperty("User-Agent", "Actividad4-ListadoPaises-Android/1.0")
            }

            try {
                val status = connection.responseCode
                val stream = if (status in 200..299) connection.inputStream else connection.errorStream
                val payload = stream?.bufferedReader()?.use { it.readText() }.orEmpty()
                if (status !in 200..299) {
                    val errorMessage = try {
                        JSONObject(payload).optJSONArray("errors")
                            ?.optJSONObject(0)?.optString("message")
                    } catch (_: Exception) { null }
                    throw IllegalStateException(
                        errorMessage?.takeIf { it.isNotBlank() }
                            ?: "El servicio respondió HTTP $status."
                    )
                }

                val root = JSONObject(payload)
                val data = root.optJSONObject("data")
                    ?: throw IllegalStateException("La respuesta del servicio no contiene datos.")
                val countries = data.optJSONArray("objects") ?: JSONArray()

                for (index in 0 until countries.length()) {
                    val item = countries.optJSONObject(index) ?: continue
                    val names = item.optJSONObject("names")
                    val translations = names?.optJSONObject("translations")
                    val spanish = translations?.optJSONObject("spa")
                    val common = spanish?.optString("common")
                        ?.takeIf { it.isNotBlank() && it != "null" }
                        ?: names?.optString("common")?.takeIf { it.isNotBlank() }
                        ?: "País sin nombre"

                    val flagObject = item.optJSONObject("flag")
                    val flag = flagObject?.optString("url_png")
                        ?.takeIf { it.startsWith("https://") }
                        .orEmpty()
                    val codes = item.optJSONObject("codes")

                    result += Country(
                        name = common,
                        flagUrl = flag,
                        alphaCode = codes?.optString("alpha_2", "--") ?: "--",
                        region = item.optString("region", "Sin región")
                    )
                }

                val meta = data.optJSONObject("meta")
                val count = meta?.optInt("count", countries.length()) ?: countries.length()
                hasMore = meta?.optBoolean("more", false) ?: false
                if (count <= 0) hasMore = false
                offset += count
            } finally {
                connection.disconnect()
            }
        }

        return result.distinctBy { it.alphaCode }
            .sortedBy { it.name.lowercase(Locale("es", "GT")) }
    }
}
