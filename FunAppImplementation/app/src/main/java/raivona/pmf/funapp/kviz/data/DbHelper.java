package raivona.pmf.funapp.kviz.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import raivona.pmf.funapp.R;
import raivona.pmf.funapp.kviz.Question;

public class DbHelper extends SQLiteOpenHelper {

    /**
     * onCreate(...) se zove svaki put kad je app nanovo instalirana, onUpgrade
     * kad god je upgradeana  i database_version nije ista.
     */
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "quiz";
    private SQLiteDatabase db;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + QuizContract.QuizEntry.TABLE_NAME + " ( " +
                    QuizContract.QuizEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                    QuizContract.QuizEntry.QUESTION + " TEXT, " + QuizContract.QuizEntry.ANSWER
                    + " TEXT, " + QuizContract.QuizEntry.OPTION1 + " TEXT, " + QuizContract.QuizEntry.OPTION2
                    + " TEXT, " + QuizContract.QuizEntry.OPTION3 + " TEXT )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuizContract.QuizEntry.TABLE_NAME;

    private static final String SELECT_QUERY = "SELECT * FROM " + QuizContract.QuizEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;
        db.execSQL(SQL_CREATE_ENTRIES);
        addQuestions();
    }

    private void addQuestions() {
        Question q1 = new Question("Najveći od ovih brojeva je ...", "13", "13", "0", "-100");
        Question q2 = new Question("Koji broj nedostaje u nizu: 64, 58, ?, 46", "52", "50", "52", "56");
        Question q3 = new Question("Što je krug?", "Dio ravnine omeđen kružnicom.", "Dio ravnine omeđen kružnicom.", "Dio ravnine izvan kružnice.", "Dio ravnine unutar kružnice.");
        Question q4 = new Question("Kako se zove skup svih točaka jednako udaljenih od jedne točke?", "kružnica", "polukružnica", "krug", "kružnica");
        Question q5 = new Question("Koliko je duga najdulja tetiva kružnice?", "2r", "r", "2r", "3r");
        Question q6 = new Question("Ostatak pri dijeljuenju 4278 s 10 je:", "8", "6", "2", "8");
        Question q7 = new Question("Koliko djelitelja ima broj 34?", "4", "4", "2", "1");
        Question q8 = new Question("Koji broj nije djeljiv s 4?", "14", "16", "12", "14");
        Question q9 = new Question("Najmanji višekratnik broja 50 je ...", "50", "50", "200", "100");
        Question q10 = new Question("-9+4 = ?", "-5", "5", "-5", "13");
        Question q11 = new Question("Skup prirodnih brojeva označavamo s ...", "N", "R", "N", "Z");
        Question q12 = new Question("Koji od ovih brojeva je prost broj?", "2", "22", "2", "0");
        Question q13 = new Question("Koja od ovih tvrdnji je točna?", "3 > 0", "-2 > 0", "3 > 0", "-10 = -(-10)");
        Question q14 = new Question("Pravokutnik je vrsta:", "paralelograma", "kvadrata", "trokuta", "paralelograma");
        Question q15 = new Question("Kolikim brojem točaka je određen pravac?", "dvjema točkama", "jednom točkom", "trima točkama", "dvjema točkama");
        this.addQuestions(q1);
        this.addQuestions(q2);
        this.addQuestions(q3);
        this.addQuestions(q4);
        this.addQuestions(q5);
        this.addQuestions(q6);
        this.addQuestions(q7);
        this.addQuestions(q8);
        this.addQuestions(q9);
        this.addQuestions(q10);
        this.addQuestions(q11);
        this.addQuestions(q12);
        this.addQuestions(q13);
        this.addQuestions(q14);
        this.addQuestions(q15);
    }

    private void addQuestions(Question question) {
        ContentValues values = new ContentValues();
        values.put(QuizContract.QuizEntry.QUESTION, question.getQuestion());
        values.put(QuizContract.QuizEntry.ANSWER, question.getAnswer());
        values.put(QuizContract.QuizEntry.OPTION1, question.getOption1());
        values.put(QuizContract.QuizEntry.OPTION2, question.getOption2());
        values.put(QuizContract.QuizEntry.OPTION3, question.getOption3());
        db.insert(QuizContract.QuizEntry.TABLE_NAME,null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        // Create tables again
        onCreate(db);
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = SELECT_QUERY;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setAnswer(cursor.getString(2));
                question.setOption1(cursor.getString(3));
                question.setOption2(cursor.getString(4));
                question.setOption3(cursor.getString(5));
                questions.add(question);
            } while (cursor.moveToNext());

        }
        return questions;
    }

    public int rowCount()
    {
        int rows = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        rows = cursor.getCount();
        return rows;
    }
}
