// package com.chat.backend.api;

// import java.util.List;
// import java.util.Map;
// import java.util.logging.SocketHandler;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.socket.config.annotation.EnableWebSocket;
// import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
// import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;

// import com.chat.backend.model.Message;
// import com.chat.backend.model.User;
// import com.chat.backend.service.chatService;


// @Configuration
// @EnableWebSocket
// public class webSocketConfiguration implements WebSocketConfigurer {

//     @Override
//     public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//         registry.addHandler(new SocketHandler(), "/socket")
//           .setAllowedOrigins("*");
//     }
// }