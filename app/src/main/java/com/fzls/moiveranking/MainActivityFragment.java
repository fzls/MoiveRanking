package com.fzls.moiveranking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.model.Discover;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MainActivityFragment extends Fragment {
    private static int currentPage = 1;
    private static int previousPage = 0;
    private boolean mIsSwipeToRefresh = false;
    private ProgressBar mProgress;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Movie> mMovies = new ArrayList<>();
    //use database instead
    private MovieAdapter mMovieAdapter;
    private OnFragmentInteractionListener mListener;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    public void Refresh() {
        new FetchDataFromServer().execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsSwipeToRefresh = true;
                new FetchDataFromServer().execute();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FloatingActionButton nextpage_fab = (FloatingActionButton) getActivity().findViewById(R.id.nextpage_fab);
        nextpage_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousPage = currentPage++;
                new FetchDataFromServer().execute();
            }
        });

        FloatingActionButton previouspage_fab = (FloatingActionButton) getActivity().findViewById(R.id.previouspage_fab);
        previouspage_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousPage = currentPage--;
                new FetchDataFromServer().execute();
            }
        });
        new FetchDataFromServer().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMovieAdapter.getItem(position);
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW);

        mProgress = (ProgressBar) rootView.findViewById(R.id.progress);


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class FetchDataFromServer extends AsyncTask<Void, Integer, List<Movie>> {
        private final String LOG_TAG = FetchDataFromServer.class.getSimpleName();
        private final String BASE_URI = "http://api.themoviedb.org/3/discover/movie?";
        private final String API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;
        /* below will be replaced by terms in the ShraedPreference */
        //chinese: zh
        //english: en
        //Japanese: ja
        private String language_code;
        private String sort_criterion;
        private boolean include_adult;
        private String afterTheYearOf;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (currentPage == 1) {
                ((FloatingActionButton) getActivity().findViewById(R.id.previouspage_fab)).hide();
            } else if (currentPage == 1000) {
                ((FloatingActionButton) getActivity().findViewById(R.id.nextpage_fab)).hide();
            } else if (currentPage == 2 && previousPage == 1) {
                ((FloatingActionButton) getActivity().findViewById(R.id.previouspage_fab)).show();
            } else if (currentPage == 999 && previousPage == 1000) {
                ((FloatingActionButton) getActivity().findViewById(R.id.nextpage_fab)).show();
            }
            if (!mIsSwipeToRefresh) {
                mProgress.setMax(100);
                mProgress.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected List<Movie> doInBackground(Void... params) {
            //change the para into the preferences
            mMovies.clear();
            TmdbApi tmdbApi = new TmdbApi(API_KEY);
            TmdbDiscover tmdbDiscover = tmdbApi.getDiscover();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            language_code = sharedPreferences.getString(getString(R.string.pref_language), getString(R.string.pref_language_default));
            boolean isDesc = sharedPreferences.getBoolean(getString(R.string.pref_sort_order), Boolean.parseBoolean(getString(R.string.pref_sort_order_default)));

            sort_criterion =
                    sharedPreferences.getString(getString(R.string.pref_sort_criterion), getString(R.string.pref_sort_criterion_default))
                            + "."
                            + (isDesc ? "desc" : "asc");
            include_adult = sharedPreferences.getBoolean(getString(R.string.pref_adult_test), Boolean.parseBoolean(getString(R.string.pref_adult_default)));
            afterTheYearOf = sharedPreferences.getString(getString(R.string.pref_date_lower), getString(R.string.pref_date_lower_default));
            Discover param =
                    new Discover().language(language_code)
                            .sortBy(sort_criterion)
                            .includeAdult(include_adult)
                            .page(currentPage)
                            .releaseDateGte(afterTheYearOf);
            MovieResultsPage mResultsPage = tmdbDiscover.getDiscover(param);
            List<MovieDb> movies = mResultsPage.getResults();

            int curr_progress = 0, total = movies.size();
            for (MovieDb movieDb : movies) {
                mMovies.add(new Movie(movieDb.getTitle(), movieDb.getReleaseDate(), movieDb.getPosterPath(), movieDb.getVoteAverage(), movieDb.getOverview()));
                publishProgress((int) (1.0 * ++curr_progress / total * 100));
            }

            return mMovies;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgress.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(List<Movie> results) {
            super.onPostExecute(results);

            if (results != null) {
                mMovieAdapter.clear();
                for (Movie movie : results) {
                    mMovieAdapter.add(movie);
                }
            }
            mSwipeRefreshLayout.setRefreshing(false);
            if (!mIsSwipeToRefresh) {
                mProgress.setVisibility(View.GONE);
            } else {
                mIsSwipeToRefresh = false;
            }
        }
    }

}

