package franklyn.unsplashclient;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Franklyn on 11/4/2015.
 */
public class RowViewHolder {
    ImageView image = null;
    ImageView favoriteImage = null;
    TextView user = null;

    public RowViewHolder(View view){
        image = (ImageView)view.findViewById(R.id.unsplashImage);
        favoriteImage = (ImageView)view.findViewById(R.id.favIcon);
        user = (TextView)view.findViewById(R.id.unsplashUser);
    }
}
