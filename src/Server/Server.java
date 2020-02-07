package Server;

import Server.threadedHandler.ThreadedHandler;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server {
    public  static void main(String[] args) {
        try{
            FileInputStream fil=new FileInputStream("src/sample/config.properties");
            Properties property=new Properties();
            property.load(fil);
            String port=property.getProperty("may.port");
            try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port))) {
                while (true) {
                    Socket clientSocket = null;
                    try {
                        clientSocket = serverSocket.accept();
                    } catch (IOException e) {
                        if (false) {
                            System.out.println("Server Stopped.");
                            return;
                        }
                        throw new RuntimeException("Error accepting client connection", e);
                    }
                    Runnable r = new ThreadedHandler(clientSocket);
                    Thread thread = new Thread(r);
                    thread.start();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
