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
import java.net.UnknownHostException;

/**
 * @author Rahul Bilimoria 500569144
 *         Tenzin Nyendak  500573810
 *         Brian Turner    500565237 
 */
public class Client {

    DatagramSocket socket;
    DatagramPacket receivePacket;
    InetAddress address;
    byte buf[];
    int port;
    int count;
    int lastSeq;
    boolean received;
    String syn;
    String req;
    String ack;
    String receive;

    /**
     * Initializes variables and creates a new DatagramSocket
     */
    public Client() {
        port = 4444;
        buf = new byte[25];
        syn = "SYN";
        req = "REQUEST";
        ack = "ACK";
        received = false;
        count = 0;
        lastSeq = 1;
        receivePacket = new DatagramPacket(buf, buf.length);
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(1000);
            address = InetAddress.getByName("localhost");
        } catch (SocketException se) {
        } catch (UnknownHostException uhe) {
        }
    }

    /**
     * Creates a new client
     * @param args 
     */
    public static void main(String args[]) {
        Client myClient = new Client();
        myClient.talkToServer();
    }

    /**
     * Connects to the server and receives packets
     */
    public void talkToServer() {
        String check[];
        String receiveMessage;

        sendSYN();
        while (!received) {
            received = true;
            sendREQUEST();
            try {
                socket.receive(receivePacket);
            } catch (IOException ioe) {
                count++;
                if (count == 3) {
                    socket.close();
                    System.exit(1);
                }
                System.out.println("Timeout Reached!!!");
                received = false;
            }
        }
        count = 0;
        received = false;
        receiveMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
        check = receiveMessage.split(" ");
        while (!check[0].equalsIgnoreCase("fin")) {
            if (check[0].equalsIgnoreCase("data")) {
                sendACK(check);
            }
            try {
                socket.receive(receivePacket);
            } catch (IOException ioe) {
            }
            receiveMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            check = receiveMessage.split(" ");
        }
        ACKofFIN();
    }

    /**
     * Establishes connection with the server
     */
    public void sendSYN() {
        received = false;
        count = 0;
        buf = syn.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, address, port);
        while (!received) {
            received = true;
            try {
                socket.send(sendPacket);    //send syn
                socket.receive(receivePacket); //receive synack
            } catch (IOException ioe) {
                received = false;
                count++;
                System.out.println("TIMEOUT REACHED!!!");
                if (count == 3) {
                    socket.close();
                    System.exit(1);
                }
            }
        }
        received = false;
    }

    /**
     * Sends a request for the message of the day
     */
    public void sendREQUEST() {
        buf = req.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(sendPacket);    //send request
        } catch (IOException ioe) {
        }
    }

    /**
     * Sends an acknowledgment of the packet that was received
     * @param check 
     */
    public void sendACK(String[] check) {
        int sequenceNumber = Integer.parseInt(check[1]);
        String sendMessage = ack + " " + sequenceNumber;
        if (lastSeq != sequenceNumber) {
            for (int i = 2; i < check.length; i++) { //prints the message out
                if (i == (check.length - 1)) {
                    System.out.print(check[i]);
                } else {
                    System.out.print(check[i] + " ");
                }
            }
            lastSeq = sequenceNumber;
        }
        buf = sendMessage.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(sendPacket); // send ack
        } catch (IOException ioe) {
        }
    }

    /**
     * Sends an acknowledgment of the packet received
     */
    public void ACKofFIN() {
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(sendPacket); //send ack of fin message
        } catch (IOException ioe) {
        }
        socket.close();
    }
}
