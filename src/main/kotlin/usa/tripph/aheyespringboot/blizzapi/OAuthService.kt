package usa.tripph.aheyespringboot.blizzapi


import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.client.support.BasicAuthenticationInterceptor
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.time.LocalDateTime
import java.time.ZoneOffset


data class OAuthResponse(val access_token: String, val token_type: String, val expires_in: Long)
@Service
class OAuthService {
    @Value(value = "\${blizz.api-key}")
    private lateinit var API_KEY: String
    @Value(value = "\${blizz.api-secret}")
    private lateinit var API_SECRET: String
    private val restTemplate = RestTemplate()
    private val targetUrl = "https://us.battle.net/oauth/token"
    private fun getTimestamp() = LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli()

    private var token: OAuthResponse? = null
    private var lastRefreshTime: Long? = null

    private val form = LinkedMultiValueMap<String, String>()
    private val headers = HttpHeaders()

    private fun refreshToken() {
        println("Refreshing token")
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        form.add("grant_type", "client_credentials")
        restTemplate.interceptors.add(BasicAuthenticationInterceptor(API_KEY, API_SECRET))
        val requestEntity = RequestEntity<MultiValueMap<String, String>>(form, headers, HttpMethod.POST, URI(targetUrl))
        val response = restTemplate.exchange(requestEntity, OAuthResponse::class.java)
        this.token = response.body
        this.lastRefreshTime = getTimestamp()
    }

    fun getAccessToken(): String {
        if (this.token == null || isTokenExpired()) {
            this.refreshToken()
        } else {
            println("token was already here and (valid?)!")
        }
        return this.token!!.access_token
    }

    private fun isTokenExpired() = (lastRefreshTime!! + this.token!!.expires_in) < getTimestamp()
}
