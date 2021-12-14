package au.edu.curtin.assignmentmad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MyWildFragment extends Fragment
{
    /* Note: This class is pretty much identical to MyOtherFragment Class, because GameData worries about if an item is in a town or not.
     * The Only thing that changes is the actual visual and the references to those views. For example instead of a buy button it's a pickup button. */
    private GameData gameEngine;
    private MyAdapter adapter;

    private class MyUseHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private Button useB;
        private Button sButton;
        private String d;
        public MyUseHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_data2,parent,false));
            textView = (TextView) itemView.findViewById(R.id.textView8);
            useB = (Button) itemView.findViewById(R.id.useButton);
            sButton = (Button) itemView.findViewById(R.id.sellsButton);

            useB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(gameEngine.isItemSmelly(d))
                    {
                        Log.d("Smelly", "You've got a Smell-O-Scope");
                        startActivity(SmellOScope.getIntent(getActivity()));
                    }
                    else {
                        gameEngine.useItem(d);
                    }
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });

            sButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    gameEngine.removeItems(d);

                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });
        }

        public void bind(String data)
        {
            d = data;
            textView.setText(data);
        }
    }

    private class MyDropHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private Button sellB;
        private String d;
        public MyDropHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_data1_wild,parent,false));
            textView = (TextView) itemView.findViewById(R.id.textView7);
            sellB = (Button) itemView.findViewById(R.id.dropButton);

            sellB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    gameEngine.removeItems(d);
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });
        }

        public void bind(String data)
        {
            d = data;
            textView.setText(data);
        }
    }

    private class MyPickupHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private Button pickupB;
        private String d;
        public MyPickupHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_data_wild,parent,false));
            textView = (TextView) itemView.findViewById(R.id.textView6);
            pickupB = (Button) itemView.findViewById(R.id.pickUp);

            pickupB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    gameEngine.obtainItem(d);
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });
        }

        public void bind(String data)
        {
            d = data;
            textView.setText(data);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
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
        public int getItemViewType(int position)
        {
            int i = -1;
            if(gameEngine.inArena(gameEngine.getCurrentArea().searchForItem(data.get(position))))//If String is in The area list then add to buy
            {
                i = 0; //buy
            }
            else if(gameEngine.getCurrentPlayer().inInventory(gameEngine.getCurrentPlayer().searchItInventory(data.get(position)))) //if item is in the players inventory + isn't usable then it can be sold!
            {
                if(gameEngine.getCurrentPlayer().searchItInventory(data.get(position)) instanceof Usable)
                {
                    i = 2;
                }
                else {
                    i = 1;
                }
            }
            return i;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            RecyclerView.ViewHolder viewHolder = new MyPickupHolder(li,parent);

            if(viewType == 0)
            {

                viewHolder = new MyPickupHolder(li,parent);
            } else if(viewType == 1)
            {

                viewHolder = new MyDropHolder(li,parent);
            }else if(viewType == 2)
            {

                viewHolder = new MyUseHolder(li,parent);
            }

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder vh, int index)
        {
            if(vh.getItemViewType() == 0)
            {

                MyPickupHolder dh0 = (MyPickupHolder) vh;
                dh0.bind(data.get(index));

            }
            else if(vh.getItemViewType() == 1)
            {
                MyDropHolder dh1 = (MyDropHolder) vh;
                dh1.bind(data.get(index));
            }
            else if(vh.getItemViewType() == 2)
            {
                MyUseHolder dh1 = (MyUseHolder) vh;
                dh1.bind(data.get(index));
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup ui, Bundle b)
    {
        View view = i.inflate(R.layout.wildrc_layout, ui, false);
        gameEngine = GameData.getGameData();

        List<Item> items = gameEngine.getCurrentArea().getItems();
        List<Equipment> inventoryItems = gameEngine.getCurrentPlayer().getInventory();
        List<String> data = new LinkedList<String>();
        for (Item i1: items)
        {
            data.add(i1.getDescription());
        }
        for (Equipment e1: inventoryItems)
        {
            data.add(e1.getDescription());
        }
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rcWild);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(data);
        rv.setAdapter(adapter);

        return view;
    }

}
