package raivona.pmf.funapp.iksoks;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import raivona.pmf.funapp.R;

public class IksOksActivity extends AppCompatActivity {

    public static final int NUMBER_OF_ROWS = 3;
    public static final int NUMBER_OF_COLS = 3;

    public boolean firstPlayerTurn = true;
    public int numberOfMoves = 0;

    Button[][] buttons = new Button[NUMBER_OF_ROWS][NUMBER_OF_COLS];
    String[][] symbols = new String[NUMBER_OF_ROWS][NUMBER_OF_COLS];

    ImageView back;
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iks_oks);

        initializeButtons();
        setClickListenersToButtons();
        initializeActions();
    }

    private void initializeActions() {
        back = findViewById(R.id.back);
        reset = findViewById(R.id.reset);

        back.setOnClickListener(backListener);
        reset.setOnClickListener(resetListener);
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(IksOksActivity.this, raivona.pmf.funapp.MainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clearBoard();
            TextView result1 = findViewById(R.id.result_1);
            TextView result2 = findViewById(R.id.result_2);

            result1.setText("0");
            result2.setText("0");
            firstPlayerTurn = true;
        }
    };

    private void setClickListenersToButtons() {
        for(int i  = 0; i < NUMBER_OF_ROWS; ++i)
            for(int j = 0; j < NUMBER_OF_COLS; ++j) {
                final Button button = buttons[i][j];
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(button.getText().equals("")) {
                            if(firstPlayerTurn) {
                                button.setText("X");
                                firstPlayerTurn = false;
                                updateStatusOfGame();
                            }
                            else {
                                button.setText("O");
                                firstPlayerTurn = true;
                                updateStatusOfGame();
                            }
                        } else return;
                    }
                });
            }
    }

    private void updateStatusOfGame() {
        numberOfMoves++;
        checkTheGame();
    }

    private void checkTheGame() {
        boolean endOfGame = false;
        TextView result1 = findViewById(R.id.result_1);
        TextView result2 = findViewById(R.id.result_2);
        int rez1 = Integer.parseInt(result1.getText().toString());
        int rez2 = Integer.parseInt(result2.getText().toString());

        for (int i = 0; i < NUMBER_OF_ROWS; ++i)
            for(int j = 0; j < NUMBER_OF_COLS; ++j) {
                symbols[i][j] = buttons[i][j].getText().toString();
            }
        // provjera po retcima
        for (int i = 0; i < NUMBER_OF_ROWS; ++i) {
            for (int j = 0; j < NUMBER_OF_COLS-1; ++j) {
                if(!(symbols[i][j] == symbols[i][j+1] && !symbols[i][j].isEmpty())) {
                    break;
                } else if(j == NUMBER_OF_COLS - 2) {
                    endOfGame = true;
                }
            }
        }
        // provjera po stupcima
        for(int j = 0; j < NUMBER_OF_COLS; ++j)
            for(int i = 0; i < NUMBER_OF_ROWS-1; ++i) {
                if(!(symbols[i][j]==symbols[i+1][j] && !symbols[i][j].isEmpty())) {
                    break;
                } else if(i == NUMBER_OF_ROWS-2) {
                    endOfGame = true;
                }
            }
        // provjera glavne dijagonale
        for(int i = 0; i < NUMBER_OF_ROWS - 1; ++i) {
            if(!(symbols[i][i]==symbols[i+1][i+1] && !symbols[i][i].isEmpty())) {
                break;
            } else if(i == NUMBER_OF_ROWS-2) {
                endOfGame = true;
            }
        }
        // provjera sporedne dijagonale
        for(int i = 0; i < NUMBER_OF_ROWS - 1; ++i)
            if(!(symbols[i][NUMBER_OF_COLS-1-i] ==
                    symbols[i+1][NUMBER_OF_COLS-i-2] && !symbols[i][NUMBER_OF_COLS-i-1].isEmpty())) {
                break;
            } else if(i == NUMBER_OF_ROWS-2){
                endOfGame = true;
            }

        //Toast.makeText(MainActivity.this, "GOTOVO", Toast.LENGTH_SHORT).show();

        StringBuilder sb = new StringBuilder();
        if(endOfGame) {
            sb.append("Pobjednik ove igre je ");

            if(!firstPlayerTurn) {
                sb.append(" Igrač 1.");
                rez1++;
                result1.setText(Integer.toString(rez1));
                clearBoard();
            } else {
                sb.append(" Igrač 2.");
                rez2++;
                result2.setText(Integer.toString(rez2));
                clearBoard();
            }
            Toast.makeText(IksOksActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
        } else if(numberOfMoves == NUMBER_OF_COLS * NUMBER_OF_ROWS) {
            sb.append("Nema pobjednika ove igre.");
            clearBoard();
            Toast.makeText(IksOksActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeButtons() {
        for(int i = 0; i < NUMBER_OF_ROWS; ++i)
            for (int j = 0; j < NUMBER_OF_COLS; ++j) {
                StringBuilder sb = new StringBuilder();
                sb.append("button_").append(i).append(j);
                int buttonID = getResources().getIdentifier(sb.toString(), "id", getPackageName());
                buttons[i][j] = findViewById(buttonID);
                symbols[i][j] = buttons[i][j].getText().toString();
            }
    }

    private void clearBoard() {
        initializeButtons();
        firstPlayerTurn = true;
        numberOfMoves = 0;
        for(int i = 0; i < NUMBER_OF_ROWS; ++i)
            for (int j = 0; j < NUMBER_OF_COLS; ++j) {
                StringBuilder sb = new StringBuilder();
                sb.append("button_").append(i).append(j);
                int buttonID = getResources().getIdentifier(sb.toString(), "id", getPackageName());
                Button button = findViewById(buttonID);
                button.setText("");
            }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("firstPlayerTurn", firstPlayerTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firstPlayerTurn =  savedInstanceState.getBoolean("firstPlayerTurn");
    }
}
