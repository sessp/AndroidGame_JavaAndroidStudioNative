package au.edu.curtin.assignmentmad;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class MyOtherFragment extends Fragment
{
    private GameData gameEngine;
    private MyAdapter adapter;

    private class MyUseHolder extends RecyclerView.ViewHolder
    {
        /* Fragment for the Market Activity. 3 Different types of view holders, and hence in order to implement these there were
        * structural changes made in the adapter classes.
        *
        * One viewholder for selling, one for using and one for buying.
         *
         * Marker Note: In some cases when the system updated the data in the list the view holders wouldn't update automatically.
         * To demonstrate I did the required function everything that is Logged to the run tab so you can see even though the view may not always
         * represent what's happening the log should confirm that I've implemented the function.
         *
         * E.G. When eating a piece of food it can sometimes still show as a buy view  holder, even after the update method has been called so the
         * console logs the functions associated with the food item, increasing health, ect.*/
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
                        adapter.notifyItemChanged(getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }


                    //adapter.notifyItemRemoved(getAdapterPosition());
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

    private class MySellHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private Button sellB;
        private String d;
        public MySellHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_data1,parent,false));
            textView = (TextView) itemView.findViewById(R.id.textView7);
            sellB = (Button) itemView.findViewById(R.id.sellButton);

            sellB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("GameData",d);

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

    private class MyBuyHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private Button buyB;
        private String d;
        public MyBuyHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_data,parent,false));
            textView = (TextView) itemView.findViewById(R.id.textView6);
            buyB = (Button) itemView.findViewById(R.id.buyButton);

            buyB.setOnClickListener(new View.OnClickListener()
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
                i = 0;
            }
            else if(gameEngine.getCurrentPlayer().inInventory(gameEngine.getCurrentPlayer().searchItInventory(data.get(position)))) //if item is in the players inventory + isn't usable then it can be sold!
            {
                if (gameEngine.getCurrentPlayer().searchItInventory(data.get(position)) instanceof Usable) {
                    i = 2;
                } else {
                    i = 1;
                }
            }

            return i;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            RecyclerView.ViewHolder viewHolder = new MyBuyHolder(li,parent);

            if(viewType == 0)
            {
                viewHolder = new MyBuyHolder(li,parent);
            } else if(viewType == 1)
            {
                viewHolder = new MySellHolder(li,parent);
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

                MyBuyHolder dh0 = (MyBuyHolder) vh;
                dh0.bind(data.get(index));

            }
            else if(vh.getItemViewType() == 1)
            {
                MySellHolder dh1 = (MySellHolder) vh;
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
        View view = i.inflate(R.layout.rc_layout, ui, false);
        Bundle bundle = getArguments();
        gameEngine = GameData.getGameData();

        List<Item> items = gameEngine.getCurrentArea().getItems();
        List<Equipment> inventoryItems = gameEngine.getCurrentPlayer().getInventory();
        List<String> data = new LinkedList<String>();

        /* Since the structure of this part, as listed above, required 3 types of view holders in only 1 list we have to add all the data into one
         * main list and then let the view holder creator and binder worry about which case is which.  */
        for (Item i1: items)
        {
            data.add(i1.getDescription());
        }
        for (Equipment e1: inventoryItems)
        {
            data.add(e1.getDescription());
        }
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.list_mydata);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(data);
        rv.setAdapter(adapter);

        return view;
    }

}
