package gr.csd.plantsreminder;

import android.provider.BaseColumns;

class PlantsContract {

    private PlantsContract(){};

    static final class PlantEntry implements BaseColumns{
        static final String TABLE_NAME = "PlantList";
        static final String COLUMN_NAME = "Name";
        static final String COLUMN_TYPE = "Type";
        static final String COLUMN_WATERING = "Watering";
        static final String COLUMN_FERTILIZER = "Fertilizer";
        static final String COLUMN_PRUNING = "Pruning";
        static final String COLUMN_TIMESTAMP = "Timestamp";
        static final String COLUMN_LAST_TIMESTAMP = "LastTimestamp";
    }

}
