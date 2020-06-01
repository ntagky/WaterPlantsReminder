package gr.csd.plantsreminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> implements Filterable {

    private static Context context;
    private Cursor cursor;
    private static SQLiteDatabase sqLiteDatabase;
    private List<PlantData> plantList;
    private List<PlantData> plantListAll;
    private Date currentDate;

    PlantAdapter(Context context, Cursor cursor){
        PlantAdapter.context = context;
        this.cursor = cursor;
        currentDate = new Date();

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        plantList = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            plantList.add(new PlantData());
            plantList.get(plantList.size()-1).setId(cursor.getLong(cursor.getColumnIndex(PlantsContract.PlantEntry._ID)));
            plantList.get(plantList.size()-1).setName(cursor.getString(cursor.getColumnIndex(PlantsContract.PlantEntry.COLUMN_NAME)));
            plantList.get(plantList.size()-1).setType(cursor.getString(cursor.getColumnIndex(PlantsContract.PlantEntry.COLUMN_TYPE)));
            plantList.get(plantList.size()-1).setLast(cursor.getString(cursor.getColumnIndex(PlantsContract.PlantEntry.COLUMN_LAST_TIMESTAMP)));
            plantList.get(plantList.size()-1).setWater(cursor.getInt(cursor.getColumnIndex(PlantsContract.PlantEntry.COLUMN_WATERING)));
            plantList.get(plantList.size()-1).setFern(cursor.getInt(cursor.getColumnIndex(PlantsContract.PlantEntry.COLUMN_FERTILIZER)));
            plantList.get(plantList.size()-1).setPrun(cursor.getInt(cursor.getColumnIndex(PlantsContract.PlantEntry.COLUMN_PRUNING)));
            cursor.moveToNext();
        }

        plantListAll = new ArrayList<>(plantList);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<PlantData> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty())
                filteredList.addAll(plantListAll);
            else {
                for (int i=0; i<plantListAll.size(); i++){
                    if (plantListAll.get(i).getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(plantListAll.get(i));
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            plantList.clear();
            plantList.addAll((Collection<? extends PlantData>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    static class PlantViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layout;
        TextView nameTextView;
        ImageView typeImageView;
        ImageView waterImageView;
        TextView lastTextView;
        TextView wateringTextView;
        TextView fertilizeTextView;
        TextView pruningTextView;
        TextView nextWateringTextView;

        PlantViewHolder(@NonNull final View itemView, final PlantAdapter plantAdapter) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            typeImageView = itemView.findViewById(R.id.plantImageView);
            lastTextView = itemView.findViewById(R.id.lastTextView);
            wateringTextView = itemView.findViewById(R.id.waterTextView);
            waterImageView = itemView.findViewById(R.id.waterImageView);
            nextWateringTextView = itemView.findViewById(R.id.nextWateringTextView);

            waterImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onClick(View view) {
                    ContentValues contentValues = new ContentValues();
                    String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                    contentValues.put(PlantsContract.PlantEntry.COLUMN_LAST_TIMESTAMP, date);
                    sqLiteDatabase.update(PlantsContract.PlantEntry.TABLE_NAME, contentValues, "_id=" + itemView.getTag(), null);
                    lastTextView.setText(date);
                    plantAdapter.notifyDataSetChanged();
                }
            });

            fertilizeTextView = itemView.findViewById(R.id.fertTextView);
            pruningTextView = itemView.findViewById(R.id.prunTextView);

            layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(context, AddPlantActivity.class);
                    intent.putExtra("id", (long) itemView.getTag());
                    intent.putExtra("name", nameTextView.getText().toString());
                    intent.putExtra("type", (String) typeImageView.getTag());
                    intent.putExtra("water", Integer.parseInt(wateringTextView.getText().toString()));
                    intent.putExtra("fert", fertilizeTextView.getText().toString());
                    intent.putExtra("prun", pruningTextView.getText().toString());
                    context.startActivity(intent);
                    ((Activity)context).finish();
                    return true;
                }
            });
        }
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plant_entry, parent, false);
        return new PlantViewHolder(view, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        if (!cursor.moveToPosition(position))
            return;


        holder.itemView.setTag(plantList.get(position).getId());

        holder.nameTextView.setText(plantList.get(position).getName());

        switch (plantList.get(position).getType()){
            case "Fern":
                holder.typeImageView.setBackgroundResource(R.drawable.img_fern);
                holder.typeImageView.setTag("0");
                break;
            case "Moss":
                holder.typeImageView.setBackgroundResource(R.drawable.img_moss);
                holder.typeImageView.setTag("1");
                break;
            case "Conifer":
                holder.typeImageView.setBackgroundResource(R.drawable.img_conifer);
                holder.typeImageView.setTag("2");
                break;
            case "Flowering":
                holder.typeImageView.setBackgroundResource(R.drawable.img_flowering);
                holder.typeImageView.setTag("3");
                break;
        }

        holder.lastTextView.setText(plantList.get(position).getLast());

        String givenDateString = plantList.get(position).getLast();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            int[] n = printDifference(findNextDate(mDate, plantList.get(position).getWater()));
            //Update db
            ContentValues contentValues = new ContentValues();
            contentValues.put(PlantsContract.PlantEntry.COLUMN_WATERING_DIFFERENCE, (n[0] + (n[1] * 0.01)));
            sqLiteDatabase.update(PlantsContract.PlantEntry.TABLE_NAME, contentValues, "_id=" + plantList.get(position).getId(), null);

            if (n[0] > 0)
                holder.nextWateringTextView.setText("Next watering in : " + n[0] + " " + (n[0] > 1? "days" : "day") + " and " + n[1] + " " + (n[1] > 1? "hours." : "hour."));
            else if (n[0] == 0 && n[1] > 0){
                holder.nextWateringTextView.setText("Next watering in : " + n[1] + " " + (n[1] > 1? "hours." : "hour."));
                holder.nextWateringTextView.setTextColor(context.getColor(R.color.colorWarningLight));
            }else {
                holder.nextWateringTextView.setText("Needs to be watered ASAP.");
                holder.nextWateringTextView.setTextColor(context.getColor(R.color.colorWarningHigh));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.nextWateringTextView.setVisibility(View.INVISIBLE);
        }

        holder.wateringTextView.setText(String.valueOf(plantList.get(position).getWater()));

        int fert = plantList.get(position).getFern();
        if (fert == -1)
            holder.fertilizeTextView.setText("/");
        else
            holder.fertilizeTextView.setText(String.valueOf(fert));

        int prun = plantList.get(position).getPrun();
        if (prun == -1)
            holder.pruningTextView.setText("/");
        else
            holder.pruningTextView.setText(String.valueOf(prun));

    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    private long findNextDate(Date date, int frequency){
        long millis = date.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        return millis + hoursInMilli * 24 * frequency;
    }

    private int[] printDifference(long startDate) {
        long different = startDate - currentDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        return new int[]{(int) elapsedDays, (int) elapsedHours};
    }

}
