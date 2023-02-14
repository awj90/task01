package myapp.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String NAME = "Ang Wei Jun";
    private static final String EMAIL = "dennis.awj@gmail.com";

    public static void main(String[] args) throws IOException {
        
        try { 
            
            // 1. Instantiate a Socket object with the host name and port number
            String hostName = args[0];
            String portNumber = args[1];

            Socket socket = new Socket(hostName, Integer.parseInt(portNumber));

            // 2. Instantiate an ObjectInputStream object for receiving inputs from server
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            // 3. Instantiate an ObjectOutputStream object for writing results to server
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            
            // 4. Read and convert the list of random numbers from String to a list of floats
            String messageFromServer = ois.readUTF();
            String[] strArray = messageFromServer.split(","); 
            List<Float> floatList = new ArrayList<>();
            
            for (int i = 0; i < strArray.length; i++) {
                floatList.add(Float.parseFloat(strArray[i]));
            }

            // 5. Instantiate a helper class for performing statistical calculations
            StatisticCalculator sc = new StatisticCalculator(floatList);

            // 6. Write to server
            oos.writeUTF(NAME);
            oos.writeUTF(EMAIL);
            oos.writeFloat(sc.calculateMean());
            oos.writeFloat(sc.calculateStandardDeviation());
            oos.flush();

            // 7. Release resources
            os.flush();
            oos.close();
            os.close();
            ois.close();
            is.close();
            socket.close();

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please provide host name and port number in args:");
            System.out.println("usage: java ... myapp.client.Main <hostName> <portNumber>");
            System.exit(0);
        } 
    }
}