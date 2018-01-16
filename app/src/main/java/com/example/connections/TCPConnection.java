package com.example.connections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TCPConnection {

    private String tcpConnState;
    private String tcpConnLocalAddress;
    private String tcpConnLocalPort;
    private String tcpConnRemAddress;
    private String tcpConnRemPort;

    public static Map<String, String> connStateMap = new HashMap<>();

    public TCPConnection(String state, String locadd, String locport, String remadd, String remport) {
        initConnStateList();
        tcpConnState = connStateMap.get(state);
        tcpConnLocalAddress = locadd;
        tcpConnLocalPort = locport;
        tcpConnRemAddress = remadd;
        tcpConnRemPort = remport;
    }

    private void initConnStateList() {
        connStateMap.put("2", "LISTEN");
        connStateMap.put("3", "SYN-SENT");
        connStateMap.put("4", "SYN-RECEIVED");
        connStateMap.put("5", "ESTABLISHED");
        connStateMap.put("6", "FIN-WAIT-1");
        connStateMap.put("7", "FIN-WAIT-2");
        connStateMap.put("8", "CLOSE-WAIT");
        connStateMap.put("9", "CLOSING");
        connStateMap.put("10", "LAST-ACK");
        connStateMap.put("11", "TIME-WAIT");
        connStateMap.put("12", "CLOSED");
    }

    public String getTcpConnState() {
        return tcpConnState;
    }

    public String getTcpConnLocalAddress() {
        return tcpConnLocalAddress;
    }

    public String getTcpConnLocalPort() {
        return tcpConnLocalPort;
    }

    public String getTcpConnRemAddress() {
        return tcpConnRemAddress;
    }

    public String getTcpConnRemPort() {
        return tcpConnRemPort;
    }


}

