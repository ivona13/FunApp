package raivona.pmf.funapp.anagrami;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import raivona.pmf.funapp.MainActivity;
import raivona.pmf.funapp.R;

public class AnagramGameActivity extends AppCompatActivity {

    public static final String[] words = {"MATEMATIKA", "ALGORITAM", "LOGARITAM", "ANANAS", "POKUS", "KVADRAT", "KVADRANT", "SUNCE",
            "KRIVULJA", "ZVIJEZDA", "TROKUT", "FORMULA", "RAVNINA", "PARABOLA", "HIPERBOLA", "PRAVAC", "LOPTICA", "MATRICA", "ALGEBRA"};
    public static final int NUMBER_OF_WORDS = 10;
    public static final String NUMBER = "NUMBER";
    public static final String RESULT = "RESULT";

    ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(words));
    ArrayList<String> displayed = new ArrayList<>();
    int numberOfAsked = 0, numberOfCorrect;
    String currentWord;
    String permutationOfWord;
    TextView anagram;
    EditText word;
    Button check;
    ImageView image, next, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagram_game);

        initializeElements();

        if(savedInstanceState == null) {
            generateNewWord();
        } else {
            currentWord = savedInstanceState.getString("word");
            permutationOfWord = savedInstanceState.getString("permutation");
        }
        displayword();
        setListeners();
    }

    private void setListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnagramGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guessedWord = word.getText().toString();
                image.setVisibility(View.VISIBLE);
                if(guessedWord.isEmpty()) return;
                if(guessedWord.toUpperCase().equals(currentWord)) {
                    image.setBackgroundResource(R.mipmap.correct);
                    numberOfCorrect++;
                } else {
                    image.setBackgroundResource(R.mipmap.incorrect);
                }
                check.setVisibility(View.GONE);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNewWord();
                image.setVisibility(View.INVISIBLE);
                word.setText(new StringBuilder().toString());
                check.setVisibility(View.VISIBLE);
            }
        });
    }

    public void generateNewWord() {
        if(numberOfAsked == NUMBER_OF_WORDS) {
            Intent intent = new Intent(AnagramGameActivity.this, AnagramFinish.class);
            intent.putExtra(RESULT, numberOfCorrect);
            intent.putExtra(NUMBER, NUMBER_OF_WORDS);
            startActivity(intent);
            return;
        }

        do {
            int random = new Random().nextInt(wordsList.size());
            currentWord = wordsList.get(random);
        } while(displayed.contains(currentWord));

        displayed.add(currentWord);
        permutationOfWord = permutation(currentWord);
        displayword();
        numberOfAsked++;
    }

    private void displayword() {
        anagram.setText(permutationOfWord);
    }

    private String permutation(String word) {
        StringBuilder sb = new StringBuilder();
        int wordLength = word.length();
        Random rand = new Random();
        int random = rand.nextInt(wordLength);
        ArrayList<Integer> positions = new ArrayList<>();

        for(int i = 0; i < wordLength; i++) {
            while(positions.contains(random)) {
                random = rand.nextInt(wordLength);
            }
            positions.add(random);
            sb.append(word.charAt(random)+"");
        }
        return sb.toString();
    }

    private void initializeElements() {
        anagram = findViewById(R.id.anagram);
        word = findViewById(R.id.editText);
        check = findViewById(R.id.check);
        image = findViewById(R.id.message);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("word", currentWord);
        outState.putInt("numberOfCorrect", numberOfCorrect);
        outState.putString("permutation", permutationOfWord);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        currentWord = savedInstanceState.getString("word");
        numberOfCorrect = savedInstanceState.getInt("numberOfCorrect");
        permutationOfWord = savedInstanceState.getString("permutation");
    }
}
