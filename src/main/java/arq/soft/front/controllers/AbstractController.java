package arq.soft.front.controllers;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class AbstractController {
	
	private WebClient webClient = WebClient.create("http://localhost:8081");
	//private WebClient webClient = WebClient.create("https://arqsoftlibreback.herokuapp.com");

	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

}
