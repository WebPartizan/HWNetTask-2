package com.kosmocourses.java.junior.io.files.lesson.client;

import java.io.*;
import java.net.Socket;

public class ServerListenClient extends  Thread{
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;


    public ServerListenClient(Socket socket) throws IOException {
        try {
            this.client = socket;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
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
    public void run() {
        try {
            while(!client.isOutputShutdown()) {
                String serverMsg = in.readLine();
                System.out.println(serverMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
