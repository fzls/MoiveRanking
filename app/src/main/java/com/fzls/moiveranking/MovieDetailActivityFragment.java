package com.fzls.moiveranking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    private final String BASE_URI = "http://image.tmdb.org/t/p/";
    private final String size = "w185";

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Intent intent = getActivity().getIntent();
        Movie movie = intent.getParcelableExtra("movie");

        ImageView poster = (ImageView) rootView.findViewById(R.id.imageView_poster);
        Picasso.with(getContext()).load(Uri.parse(BASE_URI + size + movie.getPoster_url())).fit().into(poster);

        TextView title = (TextView) rootView.findViewById(R.id.textView_title);
        title.setText(title.getText()+movie.getTitle());

        TextView release_date = (TextView) rootView.findViewById(R.id.textView_release_date);
        release_date.setText(release_date.getText()+movie.getRelease_date());

        TextView vote_average = (TextView) rootView.findViewById(R.id.textView_vote_average);
        vote_average.setText(vote_average.getText()+ String.valueOf(movie.getVote_average()));

        TextView plot_overview = (TextView) rootView.findViewById(R.id.textView_plot_overview);
        plot_overview.setText(plot_overview.getText()+movie.getPlot_overview());

        return rootView;
    }
}
