import java.net.*;
import java.io.*;
import java.util.Random;

public class server {
    
    public static void main(String args[]) throws IOException 
    {   
        //TCP port to pick from
        int TCPport = Integer.parseInt(args[0]);
        
        //initialize socket
        ServerSocket serverSocket = new ServerSocket(TCPport);
        Socket socket = serverSocket.accept();
        
        //creating output communication stream
        OutputStream socketOut = socket.getOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(socketOut);
        
        //creating UDP socket
        DatagramSocket ds = new DatagramSocket();
        
        //getting random port
        Random random = new Random();
        int port = random.nextInt(65535-1024)+1024;
        
        //flag for if connection is open or not
        //false = close connection
        //true = open connection
        boolean isOpen = false;
        
        //creating the recieving byte to get information
        //has length of 4
        byte[] receive = new byte[4];
        
        //trying to open port
        //while connection is open
        while(!isOpen)
        {
            try 
            {
                //creating a new UDP socket based on random port
                ds = new DatagramSocket(port);
                //connection is open
                isOpen = true;
            } catch (SocketException e) {
                //trying new port
                port += 1;
                if (port > 65535 || port < 1024) 
                {
                    //getting new random port
                    port = random.nextInt(65535-1024)+1024; 
                } //end of if
                isOpen = false; //connection is closed
            } //end of catch
        } //end of while
        
        //printing out random port
        System.out.println("Random port: " + port);
        
        //sending message and port number
        dataOutput.writeUTF("Negotiation has been detected. Selected the randmom port " + port);
        
        //closing connection but not server socket
        socketOut.close();
        serverSocket.close();
        dataOutput.close();
        
        //getting packet and outputting to file
        DatagramPacket getPacket;
        FileOutputStream stream = new FileOutputStream("output.txt");
        
        //getting data from client using while loop
        boolean isDone = false;
        ds.setSoTimeout(1000);
        
        while(!isDone) 
        {
            try {
                getPacket = new DatagramPacket(receive, receive.length);
                ds.receive(getPacket);
                stream.write(getPacket.getData());
                
            } catch(SocketTimeoutException e) {
                isDone = true;
            } //end of catch
        } //end of while
        
        //closing stream and UDP socket
        ds.close();
        stream.close();
        
    } //end of main

}