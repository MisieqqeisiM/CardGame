package com.example.cardgame.network;

import com.example.cardgame.network.messages.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class Communicator {
    Socket socket;

    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    BlockingQueue<NetworkMessage> outgoingMessageQueue = new LinkedBlockingQueue<>();
    BlockingQueue<NetworkMessage> incomingMessageQueue = new LinkedBlockingQueue<>();

    private final Thread senderThread;
    private final Thread receiverThread;

    public Consumer<NetworkMessage> onMessage;
    public Communicator(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        senderThread = new Thread(() -> {
            while(true) {
                try {
                    var message = outgoingMessageQueue.take();
                    outputStream.writeObject(message);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        receiverThread = new Thread(() -> {
            while(true) {
                try {
                    NetworkMessage message = (NetworkMessage) inputStream.readObject();
                    if(onMessage == null)
                        incomingMessageQueue.add(message);
                    else onMessage.accept(message);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void send(NetworkMessage message) {
        outgoingMessageQueue.add(message);
    }

    public NetworkMessage getMessage() {
        return incomingMessageQueue.poll();
    }

    void start() {
        senderThread.start();
        receiverThread.start();
    }
}
