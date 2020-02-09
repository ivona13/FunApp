package raivona.pmf.funapp.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import raivona.pmf.funapp.MainActivity;
import raivona.pmf.funapp.R;
import raivona.pmf.funapp.kviz.data.DbHelper;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int NUMBER_OF_QUESTIONS = 5;

    int qid;
    int numberOfRows;
    int correct = 0, numOfQ = 1;
    Question question;
    Button button1, button2, button3, reset, main;
    TextView questionView;
    List<Question> questions = new ArrayList<>();
    List<Integer> askedQ = new ArrayList<>();
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        DbHelper db = new DbHelper(this);
        questions = db.getAllQuestions();
        numberOfRows = db.rowCount();
        if(savedInstanceState == null) {
            qid = new Random().nextInt(numberOfRows - 1);
        } else {
            correct = savedInstanceState.getInt("correct");
            numOfQ = savedInstanceState.getInt("numOfQ");
            numberOfRows = savedInstanceState.getInt("numberOfRows");
            qid = savedInstanceState.getInt("qid");
        }
        question = questions.get(qid);
        askedQ.add(qid);

        initializeElements();
        addContentToElements();
        setClickListeners();
    }

    @Override
    public void onClick(View view) {
        Button pressedButton = (Button) view;
        String clickedAnswer = pressedButton.getText().toString();
        if(clickedAnswer.equals(question.getAnswer())) {
            pressedButton.setBackground(this.getResources().getDrawable(R.drawable.correct_answer));
            Toast.makeText(this, "Točan odgovor!", Toast.LENGTH_SHORT).show();
            correct++;
        } else {
            pressedButton.setBackground(this.getResources().getDrawable(R.drawable.incorrect_answer));
            Toast.makeText(this, "Netočan odgovor!", Toast.LENGTH_SHORT).show();
        }
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // postavljen odredjeni broj pitanja
                if(numOfQ == NUMBER_OF_QUESTIONS) {
                    setContentView(R.layout.result_layout);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Postavljeno pitanja: ").append(numOfQ).append("\n").append("Točno odgovorenih: ").append(correct);
                    TextView score = findViewById(R.id.score);
                    score.setText(sb.toString());
                    reset = findViewById(R.id.reset);
                    reset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(QuizActivity.this, QuizActivity.class));
                        }
                    });
                    main = findViewById(R.id.main);
                    main.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(QuizActivity.this, MainActivity.class));
                        }
                    });
                }

                qid = new Random().nextInt(numberOfRows - 1);
                while(askedQ.contains(qid)) {
                    qid = new Random().nextInt(numberOfRows - 1);
                }
                question = questions.get(qid);
                askedQ.add(qid);
                addContentToElements();
                numOfQ++;
            }
        }, 3000);
    }

    private void addContentToElements() {
        questionView.setText(question.getQuestion());
        button1.setText(question.getOption1());
        button2.setText(question.getOption2());
        button3.setText(question.getOption3());
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);
        button1.setBackground(this.getResources().getDrawable(R.drawable.quiz_button));
        button2.setBackground(this.getResources().getDrawable(R.drawable.quiz_button));
        button3.setBackground(this.getResources().getDrawable(R.drawable.quiz_button));
    }

    public void initializeElements() {
        questionView = findViewById(R.id.question);
        button1 = findViewById(R.id.opt1);
        button2 = findViewById(R.id.opt2);
        button3 = findViewById(R.id.opt3);
    }

    private void setClickListeners() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("correct", correct);
        outState.putInt("numOfQ", numOfQ);
        outState.putInt("numberOfRows", numberOfRows);
        outState.putInt("qid", qid);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        correct = savedInstanceState.getInt("correct");
        numOfQ = savedInstanceState.getInt("numOfQ");
        numberOfRows = savedInstanceState.getInt("numberOfRows");
    }
}
