package gr.csd.plantsreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout linearBottomLayout;
    private Button backButton;
    private Button nextButton;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideViewPager = findViewById(R.id.slideViewPager);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        slideViewPager.setAdapter(sliderAdapter);
        slideViewPager.addOnPageChangeListener(onPageChangeListener);

        linearBottomLayout = findViewById(R.id.linearBottomLayout);
        addDotsIndicators(0);

        setNextButtonListener();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                slideViewPager.setCurrentItem(currentPage - 1);

            }
        });

    }

    private void setNextButtonListener() {
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextButton.getText().equals(getString(R.string.finish))){
                    SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("introduced", true);
                    editor.apply();
                    changeIntent();
                }else
                    slideViewPager.setCurrentItem(currentPage + 1);
            }
        });
    }

    private void changeIntent() {
        Intent intent = new Intent(MainActivity.this, MyPlantsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void addDotsIndicators(int position){
        TextView[] dotsTextView = new TextView[2];
        linearBottomLayout.removeAllViews();
        for (int i = 0; i< dotsTextView.length; i++){
            dotsTextView[i] = new TextView(this);
            dotsTextView[i].setText(Html.fromHtml("&#8226;"));
            dotsTextView[i].setTextSize(35);
            dotsTextView[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            linearBottomLayout.addView(dotsTextView[i]);
        }
        dotsTextView[position].setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicators(position);

            currentPage = position;

            if (position == 0){
                backButton.setVisibility(View.INVISIBLE);
                nextButton.setText(R.string.next);
            }else if (position == 1){
                nextButton.setText(R.string.finish);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
