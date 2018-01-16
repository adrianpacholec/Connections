package com.example.connections;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connections.Networking;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class TCPFragment extends Fragment {

    private ArrayList<TCPConnection> TCPArrayList = new ArrayList<>();
    private ConnectionsAdapter adapter;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tcp, container, false);
        adapter = new ConnectionsAdapter(getActivity(), TCPArrayList);
        ListView listView = view.findViewById(R.id.tcp_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        listView.setAdapter(adapter);
        downloadConnections();
        return view;
    }

    public static TCPFragment newInstance(String text) {

        TCPFragment f = new TCPFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        downloadConnections();
                    }
                }
        );

    }

    private void downloadConnections() {
        DownloadTask task = new DownloadTask();
        task.execute("http://192.168.0.59:8081/zst-webservice/api/snmp/getTCPConnections");
    }

    public class DownloadTask extends AsyncTask<String, Void, ArrayList<TCPConnection>> {

        @Override
        protected ArrayList<TCPConnection> doInBackground(String... urls) {
            ArrayList<TCPConnection> connections = (ArrayList<TCPConnection>) Networking.fetchData(urls[0], "TCP");
            return connections;
        }

        @Override
        protected void onPostExecute(ArrayList<TCPConnection> connections) {
            TCPArrayList.clear();
            TCPArrayList.addAll(connections);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            Toast toast = Toast.makeText(getContext(), "TCP connections updated", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}