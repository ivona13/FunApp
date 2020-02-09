package raivona.pmf.funapp.anagrami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import raivona.pmf.funapp.R;

public class AnagramActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagram);

        start = findViewById(R.id.start);
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(AnagramActivity.this, AnagramGameActivity.class);
        startActivity(intent);
    }
}
