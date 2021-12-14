package au.edu.curtin.assignmentmad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyGridFragment extends Fragment
{
    /* This fragment is responsible for all the magic associated with the Overview Function/requirement in the assignment.
     * We only need one type of data holder for this one. The main difference is the recycler view layout has to be a grid. */
    private MyAdapter adapter;
    GameData gameEngine;

    private class MyDataHolder extends RecyclerView.ViewHolder
    {
        private ImageView imAge;
        private ImageView imageView;
        private ImageView starImage;
        private ImageView personImage;
        private Area a;
        public MyDataHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.griditem_layout,parent,false));
            imAge = (ImageView) itemView.findViewById(R.id.imType);
            imageView = (ImageView) itemView.findViewById(R.id.backgroundImage);
            personImage = (ImageView) itemView.findViewById(R.id.playerPosition);
            starImage = (ImageView) itemView.findViewById(R.id.starImage);

            starImage.setOnClickListener(new View.OnClickListener()
            {
                 @Override
                 public void onClick(View view)
                 {
                    a.setStarred();
                    adapter.notifyItemChanged(getAdapterPosition());
                 }
            });
        }

        public void bind(Area data)
        {
            a = data;
            if(data.getExplored() == true)
            {

                   if(gameEngine.getCurrentArea().equals(data))
                   {
                       personImage.setImageResource(R.drawable.ic_round_person_24px);
                   }
                   if(data.getStarred())
                   {
                       starImage.setImageResource(R.drawable.ic_round_star_rate_18px);
                   }//don't need an else if cause it's already set to bordered star.
                   if (data.getTown()) {
                       imAge.setImageResource(R.drawable.ic_building2);
                   } else if (!data.getTown()) {
                       imAge.setImageResource(R.drawable.ic_tree4);
                   }

            }else if(!data.getExplored())
            {
                //If the area isn't explored
                imAge.setImageResource(R.drawable.ic_round_help_24px);
            }
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataHolder>
    {
        private List<Area> data;
        public MyAdapter(List<Area> d)
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
        View view = i.inflate(R.layout.grid_layout, ui, false);


        RecyclerView rv = (RecyclerView) view.findViewById(R.id.gridLayout);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),3, GridLayoutManager.HORIZONTAL,false));

        gameEngine = GameData.getGameData();
        List<Area> areasInMap = gameEngine.getAllAreas();


        adapter = new MyAdapter(areasInMap);
        rv.setAdapter(adapter);

        return view;
    }

}
