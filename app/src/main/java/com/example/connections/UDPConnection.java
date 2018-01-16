package com.example.connections;

public class UDPConnection {
    private String udpLocalAddress;
    private String udpLocalPort;

    public UDPConnection(String add, String port){
        udpLocalAddress = add;
        udpLocalPort = port;
    }

    public String getUdpLocalAddress(){
        return udpLocalAddress;
    }

    public String getUdpLocalPort(){
        return udpLocalPort;
    }

}
