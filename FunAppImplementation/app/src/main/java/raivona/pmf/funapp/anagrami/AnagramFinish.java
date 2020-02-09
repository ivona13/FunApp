package raivona.pmf.funapp.anagrami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import raivona.pmf.funapp.MainActivity;
import raivona.pmf.funapp.R;

public class AnagramFinish extends AppCompatActivity implements View.OnClickListener {
    Button reset;
    ImageView back;
    RatingBar ratingBar;
    int numberOfAsked, correct;

    public static final String NUMBER = "NUMBER";
    public static final String RESULT = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagram_finish);

        Bundle bundle = getIntent().getExtras();
        numberOfAsked = bundle.getInt(NUMBER);
        correct = bundle.getInt(RESULT);

        initilaizeElements();
        addListeners();

        ratingBar.setNumStars(numberOfAsked/2);
        ratingBar.setStepSize((float)0.5);
        ratingBar.setRating(correct*ratingBar.getStepSize());
    }

    private void initilaizeElements() {
        reset = findViewById(R.id.reset);
        back = findViewById(R.id.back);
        ratingBar = findViewById(R.id.ratingBar);
    }

    private void addListeners() {
        reset.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnagramFinish.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, raivona.pmf.funapp.anagrami.AnagramGameActivity.class);
        startActivity(intent);
    }
}
