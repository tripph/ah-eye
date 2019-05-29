package usa.tripph.aheyespringboot.blizzapi

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import usa.tripph.aheyespringboot.AhEyeSpringbootApplication

@SpringBootTest(classes = [AhEyeSpringbootApplication::class])
@ExtendWith(SpringExtension::class)
internal class OAuthServiceTest : OAuthService() {
    @Autowired
    private lateinit var oathService: OAuthService

    @Test
    fun `should be able to get access token AND getting access token should only refresh once`() {
        val token = oathService.getAccessToken()
        val token2 = oathService.getAccessToken()
        assert(token.isNotEmpty() && token.length > 1)
        assert(token == token2)
    }

}
