/*
 * This source file was generated by the Gradle 'init' task
 */
package me.wayne;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class App {
    private static final int PORT = 3000;
    private final Logger logger = Logger.getLogger(App.class.getName());
    private final InMemoryStore dataStore = new InMemoryStore();

    public static void main(String[] args) {
        new App();
    }

    App() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Accepted connection from " + clientSocket.getInetAddress());
                CommandHandler commandHandler = new CommandHandler(clientSocket, dataStore);
                new Thread(commandHandler).start();
                if (clientSocket.isClosed()) {
                    logger.info("Connection closed");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
