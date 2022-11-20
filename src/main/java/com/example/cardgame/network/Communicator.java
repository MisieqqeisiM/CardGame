package com.example.cardgame.network;

import com.example.cardgame.network.messages.Disconnect;
import com.example.cardgame.network.messages.NetworkMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
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
    AtomicBoolean disconnected = new AtomicBoolean(false);
    public Communicator(Socket socket, Runnable onDisconnect) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        senderThread = new Thread(() -> {
            while(true) {
                try {
                    var message = outgoingMessageQueue.take();
                    outputStream.writeObject(message);
                    if(message instanceof Disconnect) {
                        if(disconnected.get()) return;
                        disconnected.set(true);
                        socket.close();
                        onDisconnect.run();
                        return;
                    }
                } catch (InterruptedException | IOException e) {
                    if(disconnected.get()) return;
                    disconnected.set(true);
                    onDisconnect.run();
                    return;
                }
            }
        });

        receiverThread = new Thread(() -> {
            while(true) {
                try {
                    NetworkMessage message = (NetworkMessage) inputStream.readObject();
                    if(message instanceof Disconnect) {
                        if(disconnected.get()) return;
                        disconnected.set(true);
                        socket.close();
                        onDisconnect.run();
                        return;
                    }
                    if(onMessage == null)
                        incomingMessageQueue.add(message);
                    else onMessage.accept(message);
                } catch (IOException | ClassNotFoundException e) {
                    if(disconnected.get()) return;
                    disconnected.set(true);
                    onDisconnect.run();
                    return;
                }
            }
        });
    }

    public void send(NetworkMessage message) {
        outgoingMessageQueue.add(message);
    }
    public void disconnect() {
        send(new Disconnect());
    }

    public NetworkMessage getMessage() {
        return incomingMessageQueue.poll();
    }

    public void start() {
        senderThread.start();
        receiverThread.start();
    }
}
