package com.api4shared.searchfor4shared.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.api4shared.searchfor4shared.R;
import com.api4shared.searchfor4shared.adapters.FileAdapter;
import com.api4shared.searchfor4shared.api.ApiClient;
import com.api4shared.searchfor4shared.models.FileModel;
import com.api4shared.searchfor4shared.models.FilesHolder;
import com.api4shared.searchfor4shared.models.IFileModel;
import com.api4shared.searchfor4shared.otto.BusProvider;
import com.api4shared.searchfor4shared.otto.SearchEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FileSearchFragment extends Fragment {

    private final String TAG = "FileSearchFragment";
    private ListView filesListView;
    private FileAdapter fileAdapter;
    private boolean isDownloadInProgress = false;
    private int nextPage = 0;
    private List<IFileModel> filesList = new ArrayList<IFileModel>();
    private static String query = "";

    /**
     * When this amount of items is left in the ListView yet to be displayed we will start downloading more data (if available).
     */
    private static final int RUNNING_LOW_ON_DATA_THRESHOLD = 2;

    private static final int ITEMS_PER_PAGE = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_file_search, container, false);
        filesListView = (ListView) rootView.findViewById(R.id.listView_of_files);
        return rootView;
    }

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListView();
    }

    /**
     * Initialization of adapter and listView itself
     */
    private void initListView() {
        filesListView.setOnScrollListener(scrollListener);
        fileAdapter = new FileAdapter(getActivity(),filesList, this);
        filesListView.setAdapter(fileAdapter);
        filesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickItem(position);
            }
        });
    }

    /**
     * Click on file item
     * @param position Position of item in list
     */
    private void onClickItem(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(filesList.get(position).getDownloadPage()));
        startActivity(browserIntent);
    }

    /**
     * Download data from server
     * @param pageNumber
     */
    public void downloadFileData(final int pageNumber, String query) {
        if (!isDownloadInProgress) {
            isDownloadInProgress = true;
            this.query = query;
            Log.d(TAG, "query: " + query);
            ApiClient.getSearchApi().searchFiles(getResources().getString(R.string.consumer_key),
                    ITEMS_PER_PAGE,
                    pageNumber * ITEMS_PER_PAGE,
                    query,
                    new Callback<FilesHolder>() {

                @Override
                public void success(FilesHolder fileListHolder, Response response) {
                    consumeApiData(fileListHolder.getFiles());
                    Log.d(TAG, "success downloading" + fileListHolder.getFiles().size());
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    consumeApiData(null);
                    Log.d(TAG, "failure downloading:  " + retrofitError.getMessage());
                }
            });
        }
    }

    /**
     * Consume and render data.
     * @param filesFromServer
     */
    private void consumeApiData(List<FileModel> filesFromServer) {
        if (filesFromServer != null) {

            // Add the found files to list to render
            filesList.addAll(filesFromServer);

            // Tell the adapter that it needs to rerender
            fileAdapter.notifyDataSetChanged();

            nextPage++;
        }
        isDownloadInProgress = false;
    }

    /**
     * Handle search from activity
     * @param event
     */
    @Subscribe
    public void handleSearch(SearchEvent event) {
        //Clear list for new search
        filesList.clear();
        downloadFileData(event.getPage(), event.getQuery());
    }

    /**
     * Scroll-handler for the ListView which can auto-load the next page of data.
     */
    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // Nothing to do
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            // Detect if the ListView is running low on data
            if (totalItemCount > 0 && totalItemCount - (visibleItemCount + firstVisibleItem) <= RUNNING_LOW_ON_DATA_THRESHOLD) {
                downloadFileData(nextPage,query);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // Kick off first download, if there is first page
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Kick off first download, if there is first page
        BusProvider.getInstance().unregister(this);
    }

}
