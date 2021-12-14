package au.edu.curtin.assignmentmad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class SmellFragment extends Fragment
{
    /* Fragment associated with the Smell-O-Function task.
    It's just a recyclerviewer of a list which GameData calculates. Simply outputs said list. */
    private MyAdapter adapter;
    GameData gameEngine;

    private class MyDataHolder extends RecyclerView.ViewHolder
    {
        private TextView textDisplay1;
        private TextView textDisplay2;
        private TextView textDisplay3;

        public MyDataHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.smellrowlayout,parent,false));
            textDisplay1 = (TextView)itemView.findViewById(R.id.description);
            textDisplay2 = (TextView)itemView.findViewById(R.id.row);
            textDisplay3 = (TextView)itemView.findViewById(R.id.column);
        }

        public void bind(String data)
        {
            String[] dataToDisplay = data.split(",",3);
            textDisplay1.setText(dataToDisplay[0]);
            textDisplay2.setText(dataToDisplay[1]);
            textDisplay3.setText(dataToDisplay[2]);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataHolder>
    {
        private List<String> data;
        public MyAdapter(List<String> d)
        {
            this.data = d;
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }



        @Override
        public MyDataHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {

            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MyDataHolder(li,parent);

        }

        @Override
        public void onBindViewHolder(MyDataHolder vh, int index)
        {
            vh.bind(data.get(index));
        }
    }

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup ui, Bundle b)
    {
        View view = i.inflate(R.layout.smell_rc_layout, ui, false);


        RecyclerView rv = (RecyclerView) view.findViewById(R.id.smellRC);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        gameEngine = GameData.getGameData();
        List<String> s = gameEngine.smellOUse();



        adapter = new MyAdapter(s);
        rv.setAdapter(adapter);

        return view;
    }

}
