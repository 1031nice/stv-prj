package me.donghun.gatewayserver

import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureWireMock
class RouteTest @Autowired constructor(val webClient: WebTestClient) {

    @DisplayName("Sample API routing")
    @Test
    fun hello() {
        /*
        TODO /hi가 gateway인데 /hi를 스텁하면 아무것도 테스트 못하는거 아닌가?
        /hi가 라우팅해주는 그 url을 스텁해야 할 것 같은데
        외부 라이브러리 스텁하는 방법 조사 필요
         */
        stubFor(get(urlEqualTo("/hi"))
            .willReturn(aResponse()
                .withBody("{\"headers\":{\"Hello\":\"World\"}}")
                .withHeader("Content-Type", "application/json")));

        webClient
            .get().uri("/hi")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.headers.Hello").isEqualTo("World")
    }

}