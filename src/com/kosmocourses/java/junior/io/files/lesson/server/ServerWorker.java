package com.kosmocourses.java.junior.io.files.lesson.server;

import java.io.*;
import java.net.Socket;

public class ServerWorker  extends Thread {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;

    public ServerWorker(Socket socket) throws IOException {
        try {
            this.client = socket;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            start();
        } catch (IOException e) {
            closeConnection(socket);
            throw e;
        }
    }

    private void closeConnection(Socket socket) throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Server worker " + NetworkUtils.getRemoteClientName(client) + " was started");
    }

    @Override
    public void run() {
        try {
            String msg = ServerListener.ServerCommand.START.toString();
            while (!msg.contains(ServerListener.ServerCommand.EXIT.toString())) {
                    msg = in.readLine();
                    System.out.println("Server got message \"" + msg + "\" from client: " + NetworkUtils.getRemoteClientName(client));

                sendMsgToAll(msg);

            }
            ServerListener.setIsRunned(false);
        } catch (IOException e) {
            System.out.println("Server error: " + e.getLocalizedMessage());
        } finally {
            System.out.println("Client " + NetworkUtils.getRemoteClientName(client) + " close connection");
            try {
                closeConnection(client);
            } catch (IOException e) {
                System.out.println("Server error: " + e.getLocalizedMessage());
            }
            System.out.println("Server worker " + NetworkUtils.getRemoteClientName(client) + " was stopped");
        }
    }

    private void sendMsgToAll(String msg) throws IOException {
        for ( ServerWorker worker: ServerListener.getWorkers()) {
            worker.out.write(msg);
            worker.out.newLine();
            worker.out.flush();
        }

    }
}
