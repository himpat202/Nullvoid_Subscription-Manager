package com.polymath.subscriptionmanager.data;

import android.provider.BaseColumns;

public final class SubscriptionContract
{
    public final String PATH_SUBSCRIPTION = "subs";

    private SubscriptionContract() {}

    public static final class SubscriptionEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "subs";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_START_DATE = "startDate";
        public static final String COLUMN_DAYS_OF_SUBSCRIPTION = "days";
    }
}
