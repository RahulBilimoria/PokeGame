/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cps706.assignment.pkg2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Calendar;

/**
 * @author Rahul Bilimoria 500569144
 *         Tenzin Nyendak  500573810
 *         Brian Turner    500565237 
 */
public class Server {

    DatagramSocket server;
    DatagramPacket receivePacket;
    InetAddress clientAddress;
    boolean finished;
    int clientPort;
    byte buf[];
    String synack;
    String data;
    String fin;
    String receive;

    /**
     * Initializes variables and creates the server
     */
    public Server() {
        buf = new byte[256];
        receivePacket = new DatagramPacket(buf, buf.length);
        synack = "SYNACK";
        data = "DATA";
        fin = "FIN";
        try {
            server = new DatagramSocket(4444); // creates a socket at port 4444
            server.setSoTimeout(5000);
        } catch (SocketException se) {
            System.out.println("SocketException: Port already in use.");
            System.exit(1);
        }
    }
    
    /**
     * Creates a new server and makes that server wait for a connection
     * @param args
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException {
        while (true) {
            Server myServer = new Server();
            myServer.waitForConnection();
        }
    }
    
    /**
     * Waits for packets to be sent by a client
     */
    public void waitForConnection() {
        String check;
        int count = 0;
        finished = false;
        establishConnection();
        sendSYNACK();
        while (!finished) {
            try {
                server.receive(receivePacket); // receives client packet
            } catch (IOException ioe) {
                count++;
                if (count >= 3) {
                    finished = true;
                }
            }
            if (isClient(receivePacket)) {
                check = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (check.equalsIgnoreCase("request") || isClient(receivePacket)) {
                    sendDATA();
                    if (!finished) {
                        sendFIN();
                    }
                }
            }
        }
        server.close();
    }
    
    /**
     * Waits for a client to connect to the server.
     */
    public void establishConnection() {
        boolean done = false;
        while (!done) {
            done = true;
            try {
                server.receive(receivePacket); // receives client packet
            } catch (IOException ioe) {
                done = false;
            }
        }
        clientAddress = receivePacket.getAddress();
        clientPort = receivePacket.getPort();
    }

    /**
     * Checks to see if the packet was sent by the client
     * @param packet
     * @returns true or false
     */
    public boolean isClient(DatagramPacket packet) {
        return clientAddress == packet.getAddress();
    }

    /**
     * Sends a SYNACK packet to the client
     */
    public void sendSYNACK() {
        buf = synack.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, clientAddress, clientPort);
        try {
            server.send(sendPacket);    //send synack
        } catch (IOException ioe) {
        }
    }

    /**
     * Sends the message of the day to the client
     */
    public void sendDATA() {
        DatagramPacket sendPacket;
        String message = getMOTD();
        String sendMessage;
        String receiveMessage[];
        String r;
        int count = 0;
        boolean done = false;
        boolean done2 = false;
        int sequenceNumber = 0;
        while (!done) {
            if (message.length() < 16) {
                sendMessage = "DATA" + " " + String.valueOf(sequenceNumber) + " " + message;
                buf = sendMessage.getBytes();
            } else { //breaks the message up 
                String temp = message.substring(0, 16);
                sendMessage = "DATA" + " " + String.valueOf(sequenceNumber) + " " + temp;
                buf = sendMessage.getBytes();
            }
            sendPacket = new DatagramPacket(buf, buf.length, clientAddress, clientPort);
            try {
                server.send(sendPacket); // send packet containing actual data
                server.receive(receivePacket); // receive ack
                while (!isClient(receivePacket)){
                    server.receive(receivePacket);
                }
            } catch (IOException ioe) {
                count++;
                if (count >= 3) {
                    finished = true;
                    break;
                }
            }
            r = new String(receivePacket.getData(), 0, receivePacket.getLength());
            receiveMessage = r.split(" ");
            int ack = Integer.parseInt(receiveMessage[1]);
            if (ack == sequenceNumber) { //changes seq number if it got the right packet
                sequenceNumber++;
                sequenceNumber = sequenceNumber % 2;
                if (message.length() < 16) {
                    done = true;
                } else {
                    message = message.substring(16, message.length());
                }
            }
        }
    }

    /**
     * Tells the client its done sending the message
     */
    public void sendFIN() {
        DatagramPacket sendPacket;
        int count = 0;
        boolean done = false;
        buf = fin.getBytes();
        sendPacket = new DatagramPacket(buf, buf.length, clientAddress, clientPort);
        while (!done) {
            done = true;
            try {
                server.send(sendPacket); // send fin message
                server.receive(receivePacket); //receive ack of fin
                while (!isClient(receivePacket))
                    server.receive(receivePacket);
                finished = true;
            } catch (IOException ioe) {
                count++;
                done = false;
                if (count >= 3) {
                    finished = true;
                    break;
                }
            }
        }
        finished = true;
        System.out.println("Successfully sent MOTD.");
    }

    /**
     * Gets the message of the day
     * @return String message
     */
    public String getMOTD() {
        String MOTD[] = {"I hope that woungang gives us 100 for this assignment!",
        "I think that its tuesday today.",
        "You are going to ace all your exams if you study.",
        "Today is hump day, only 2 more days until the weekend.",
        "This 706 assignment is taking way too long to finish.",
        "Its finally friday, no more school for the weekend.",
        "Today is the best day out of the entire week."};
        Calendar calendar = Calendar.getInstance();        
        return MOTD[calendar.get(Calendar.DAY_OF_WEEK)-1];
    }
}
