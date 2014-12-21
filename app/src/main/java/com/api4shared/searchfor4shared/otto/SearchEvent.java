package com.api4shared.searchfor4shared.otto;

/**
 * Created by stanislavsikorsyi on 21.12.14.
 */
public class SearchEvent {

    private String query;
    private int page;

    public SearchEvent(String query, int page) {
        this.query = query;
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public int getPage() {
        return page;
    }
}
