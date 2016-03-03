package com.fzls.moiveranking;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 风之凌殇 on 2016/2/6.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private final String BASE_URI = "http://image.tmdb.org/t/p/";
    private final String size = "w185";

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void add(Movie object) {
        super.add(object);
    }

    public MovieAdapter(Activity context, List<Movie> movies){
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        ImageView poster = (ImageView)convertView.findViewById(R.id.movie_poster);
        Picasso.with(getContext()).load(Uri.parse(BASE_URI+size+movie.getPoster_url())).into(poster);

        TextView name = (TextView) convertView.findViewById(R.id.movie_name);
        name.setText(movie.getTitle());

        return convertView;
    }
}
