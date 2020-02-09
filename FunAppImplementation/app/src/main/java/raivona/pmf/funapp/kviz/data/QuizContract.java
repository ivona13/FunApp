package raivona.pmf.funapp.kviz.data;

import android.provider.BaseColumns;

public class QuizContract {

    public static class QuizEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static  final String ID = "id";
        public static final String QUESTION = "question";
        public static final String ANSWER = "answer";
        public static final String OPTION1 = "option1";
        public static final String OPTION2 = "option2";
        public static final String OPTION3 = "option3";
    }
}
