package me.wayne;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.wayne.daos.io.StoreBufferedReader;
import me.wayne.daos.io.StorePrintWriter;

public class CommandHandler implements Runnable {

    public static final String INPUT_PREFIX = ">> ";
    private static final Logger logger = Logger.getLogger(CommandHandler.class.getName());

    private Socket clientSocket;

    public CommandHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (StoreBufferedReader in = new StoreBufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             StorePrintWriter out = new StorePrintWriter(clientSocket.getOutputStream())) {

            out.println();
            out.println("-----------------------------");
            out.println("      Welcome to Jedis!      ");
            out.println("-----------------------------");
            out.println();
            out.print(INPUT_PREFIX);
            out.flush();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                logger.log(Level.INFO, "Received command: {0}", inputLine);

                UUID requestUuid = null;
                String[] inputLineSplit = inputLine.split(":");
                if (inputLineSplit.length > 1) {
                    try {
                        requestUuid = UUID.fromString(inputLineSplit[0]);
                        inputLine = String.join(":", Arrays.copyOfRange(inputLineSplit, 1, inputLineSplit.length));
                    } catch (IllegalArgumentException e) {
                        // Invalid UUID, treat as no UUID
                    }
                }

                if (!InMemoryStore.getInstance().hasTransaction(Thread.currentThread())) {
                    
                    InMemoryStore.getInstance().executeCommand(inputLine, out, requestUuid);
                    
                } else {
                    if (inputLine.equals("MULTI")) {
                        out.println(requestUuid, "-ERR MULTI calls can't be nested");
                    } else if (inputLine.equals("DISCARD")) {
                        InMemoryStore.getInstance().removeTransaction(Thread.currentThread());
                        out.println(requestUuid, "+OK");
                    } else if (inputLine.equals("EXEC")) {
                        InMemoryStore.getInstance().executeTransaction(Thread.currentThread(), out, requestUuid);
                        InMemoryStore.getInstance().removeTransaction(Thread.currentThread());
                    } else {
                        String res = InMemoryStore.getInstance().addCommandToTransaction(Thread.currentThread(), inputLine);
                        out.println(requestUuid, res);
                    }
                }

                out.print(">> ");
                out.flush();
            }
        } catch (IOException e) {
            try {
                clientSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public List<String> getArgs(String input) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                result.add(matcher.group(1));
            } else {
                result.add(matcher.group(2));
            }
        }

        return result;
    }

}