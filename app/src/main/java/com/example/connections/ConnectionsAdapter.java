package com.example.connections;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectionsAdapter<T> extends ArrayAdapter<T> {

    public ConnectionsAdapter(Context context, ArrayList<T> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Object currentResult = getItem(position);

        if (currentResult instanceof TCPConnection){
            TCPViewHolder tcpViewHolder = null;

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.tcp_list_item, parent, false);
                tcpViewHolder = new TCPViewHolder(convertView);
                convertView.setTag(tcpViewHolder);
            }
            else tcpViewHolder = (TCPViewHolder)convertView.getTag();

            tcpViewHolder.tcpConnState.setText(((TCPConnection) currentResult).getTcpConnState());
            tcpViewHolder.tcpConnLocalAddress.setText(((TCPConnection) currentResult).getTcpConnLocalAddress());
            tcpViewHolder.tcpConnLocalPort.setText(((TCPConnection) currentResult).getTcpConnLocalPort());
            tcpViewHolder.tcpConnRemAddress.setText(((TCPConnection) currentResult).getTcpConnRemAddress());
            tcpViewHolder.tcpConnRemPort.setText(((TCPConnection) currentResult).getTcpConnRemPort());

        }
        else if (currentResult instanceof UDPConnection) {
            UDPViewHolder udpViewHolder = null;

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.udp_list_item, parent, false);
                udpViewHolder = new UDPViewHolder(convertView);
                convertView.setTag(udpViewHolder);
            }
            else udpViewHolder = (UDPViewHolder)convertView.getTag();

            udpViewHolder.udpLocalAddress.setText(((UDPConnection) currentResult).getUdpLocalAddress());
            udpViewHolder.udpLocalPort.setText(((UDPConnection) currentResult).getUdpLocalPort());
        }
        return convertView;
    }

    class TCPViewHolder {
        TextView tcpConnState;
        TextView tcpConnLocalAddress;
        TextView tcpConnLocalPort;
        TextView tcpConnRemAddress;
        TextView tcpConnRemPort;

        public TCPViewHolder(View view) {
            tcpConnState = (TextView) view.findViewById(R.id.tcpConnState);
            tcpConnLocalAddress = (TextView) view.findViewById(R.id.tcpLocalAddress);
            tcpConnLocalPort = (TextView) view.findViewById(R.id.tcpLocalPort);
            tcpConnRemAddress = (TextView) view.findViewById(R.id.tcpRemoteAddress);
            tcpConnRemPort = (TextView) view.findViewById(R.id.tcpRemotePort);
        }
    }

    class UDPViewHolder {
        TextView udpLocalAddress;
        TextView udpLocalPort;
        public UDPViewHolder(View view){
            udpLocalAddress = (TextView) view.findViewById(R.id.udpLocalAddress);
            udpLocalPort = (TextView) view.findViewById(R.id.udpLocalPort);

        }
    }
}