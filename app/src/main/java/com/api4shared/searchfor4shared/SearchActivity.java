package com.api4shared.searchfor4shared;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.api4shared.searchfor4shared.fragments.FileSearchFragment;
import com.api4shared.searchfor4shared.otto.BusProvider;
import com.api4shared.searchfor4shared.otto.SearchEvent;
import com.api4shared.searchfor4shared.util.Utils;


public class SearchActivity extends Activity {


    private static final String TAG = "SearchActivityTAG";
    public static final int FIRST_PAGE = 0;

    //Current search query
    public static String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            FileSearchFragment searchList = new FileSearchFragment();
            searchList.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, searchList).commit();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //If new intent is search intent, we initiate search flow
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            initiateSearch(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // Associate searchable configuration with the SearchView
        initSearchView(menu);

        return true;
    }

    private void initSearchView(Menu menu) {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void initiateSearch(Intent intent) {
        if(Utils.isOnline(this)) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);
            BusProvider.getInstance().post(new SearchEvent(searchQuery, FIRST_PAGE));
        } else {
            Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

}
