package com.api4shared.searchfor4shared.otto;

import com.squareup.otto.Bus;

/**
 * Created by stanislavsikorsyi on 21.12.14.
 */
// Provided by Square under the Apache License
public  class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
    }
}