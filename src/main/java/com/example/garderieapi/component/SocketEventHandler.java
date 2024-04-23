package com.example.garderieapi.component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.garderieapi.Service.MessageService;
import com.example.garderieapi.Service.UserService;
import com.example.garderieapi.dto.MessageDTO;
import com.example.garderieapi.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class SocketEventHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    public void handleConnection(SocketIOServer server) {
        server.addConnectListener(client -> {
            System.out.println("Connected: " + client.getSessionId());
        });
    }
/*
    public void handleAddUser(SocketIOServer server) {
        server.addEventListener("addUser", String.class, (client, userId, ackSender) -> {
            System.out.println("Connected user: " + userId);
            try {
                userService.addUser(Long.parseLong(userId), client.getSessionId().toString());
            } catch (NumberFormatException e) {
                System.err.println("Invalid user ID format: " + userId);
            } catch (Exception e) {
                System.err.println("Failed to add user: " + e.getMessage());
            }
        });
    }
    public void handleSendMessage(SocketIOServer server) {
        server.addEventListener("sendMessage", MessageDTO.class, (client, data, ackSender) -> {
            System.out.println("Send message ");
            try {
                Long senderId = Long.parseLong(data.getSenderId());
                Long receiverId = Long.parseLong(data.getReceiverId());
                String content = data.getContent();
                Message message = messageService.createMessage(senderId, receiverId, content);

                // Recherche du client destinataire
                SocketIOClient receiverClient = findClientBySocketId(server.getAllClients(), data.getReceiverSocketId());
                if (receiverClient != null) {
                    receiverClient.sendEvent("getMessage", new MessageDTO(senderId.toString(), message));
                }

                // Recherche du client émetteur
                SocketIOClient senderClient = findClientBySocketId(server.getAllClients(), data.getSenderSocketId());
                if (senderClient != null) {
                    senderClient.sendEvent("messageSent", new MessageDTO(senderId.toString(), message));
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid user ID format in message: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Failed to send message: " + e.getMessage());
            }
        });
    }

    private SocketIOClient findClientBySocketId(Collection<SocketIOClient> clients, String socketId) {

            for (SocketIOClient client : clients) {
                if (client.getSessionId().toString().equals(socketId)) {
                    return client;
                }
            }
            return null;
        }




    public void handleDisconnection(SocketIOServer server) {
        server.addDisconnectListener(client -> {
            System.out.println("Disconnected: " + client.getSessionId());
            try {
                userService.removeUser(client.getSessionId().toString());
            } catch (Exception e) {
                System.err.println("Failed to disconnect user: " + e.getMessage());
            }
        });
    }*/
}
