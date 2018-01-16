package com.example.connections;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.connections.Networking;

import java.util.ArrayList;

public class UDPFragment extends Fragment {

    private ArrayList<UDPConnection> UDPArrayList = new ArrayList<>();
    private ConnectionsAdapter adapter;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_udp, container, false);
        adapter = new ConnectionsAdapter(getActivity(), UDPArrayList);
        ListView listView = view.findViewById(R.id.udp_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        listView.setAdapter(adapter);
        downloadConnections();
        return view;
    }

    public static UDPFragment newInstance(String text) {

        UDPFragment f = new UDPFragment();
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

    private void downloadConnections(){
        UDPFragment.DownloadTask task = new UDPFragment.DownloadTask();
        task.execute("http://192.168.0.59:8081/zst-webservice/api/snmp/getUDPListenerTable");

    }

    public class DownloadTask extends AsyncTask<String, Void, ArrayList<UDPConnection>> {

        @Override
        protected ArrayList<UDPConnection> doInBackground(String... urls) {
            ArrayList<UDPConnection> connections = (ArrayList<UDPConnection>) Networking.fetchData(urls[0],"UDP");
            return connections;
        }

        @Override
        protected void onPostExecute(ArrayList<UDPConnection> connections) {
            UDPArrayList.clear();
            UDPArrayList.addAll(connections);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            Toast toast = Toast.makeText(getContext(), "UDP connections updated", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}