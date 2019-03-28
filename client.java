import java.net.*;
import java.io.*;
import java.nio.file.*;

public class client {

   public static void main(String args[]) throws IOException
   {
       //Arguments
       String hostname = args[0];
       int port = Integer.parseInt(args[1]);
       
       //Create the Socket
       Socket socket = new Socket(hostname, port);
              
       //Getting input from socket and reading to a string
       InputStream socketInput = socket.getInputStream();
       DataInputStream input = new DataInputStream(socketInput);
       String inputString = new String(input.readUTF());

       //Printing input
       System.out.println(inputString);
       
       //Getting new port from message sent
       inputString = inputString.split("port")[1];
       inputString = inputString.trim();
       
       //Closing connection
       socket.close();
       socketInput.close();
       input.close();
        
       //Creating the UDP socket object to carry the data
       DatagramSocket ds = new DatagramSocket();
   
       //getting host address
       InetAddress ip = InetAddress.getByName(hostname);
              
       //creating file name
       String filename = args[2];
       
       //creating bytes to read file and send data
       byte[] file = Files.readAllBytes(new File(filename).toPath());
       byte[] send = new byte[4];
       
       //making the byte into readable string
       String byteToString;
       
       //loop to send file over 4 bytes at a time
       for (int filePacket = 0; filePacket < file.length; filePacket += 4)
       {
           try 
           {
               DatagramPacket sendPacket = new DatagramPacket(send, send.length, ip, Integer.parseInt(inputString));
               //DatagramPacket sendPacket = new DatagramPacket();
               //UDP socket sending packet information
               ds.send(sendPacket);
               
               //clear each after sending
               for (int i = 0; i < 4; i++) 
               {
                   send[i] = 0;
               } //end of for loop
               
           } catch (ArrayIndexOutOfBoundsException e) {
               //send packet as is
               DatagramPacket sendPacket = new DatagramPacket(send, send.length, ip, Integer.parseInt(inputString));
               //UDP socket sending packet information
               ds.send(sendPacket);
           } //end of catch
           
       } //end of for loop
       
       //closing UDP socket
       ds.close();
       
   } //end of main
       
} //end of client class

