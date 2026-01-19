package com.kabutar.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
	    registry
	            .addEndpoint("/ws")
	            .setAllowedOriginPatterns("http://localhost:3000")
	            .addInterceptors(new com.kabutar.auth.ws.WsHandshakeInterceptor())
	            .setHandshakeHandler(new com.kabutar.auth.ws.WsUserHandshakeHandler())
	            .withSockJS();
	}


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // client -> server prefix
        registry.setApplicationDestinationPrefixes("/app");

        // server -> client prefix
        registry.enableSimpleBroker("/topic", "/queue");

        // for /user/queue/...
        registry.setUserDestinationPrefix("/user");
    }
}
