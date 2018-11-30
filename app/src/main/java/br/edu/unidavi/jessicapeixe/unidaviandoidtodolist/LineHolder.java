package br.edu.unidavi.jessicapeixe.unidaviandoidtodolist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LineHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView image;
        public TextView subtitle;

        public LineHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.beer_description);
            image = itemView.findViewById(R.id.beer_image);
            subtitle = itemView.findViewById(R.id.beer_price);
        }
}
