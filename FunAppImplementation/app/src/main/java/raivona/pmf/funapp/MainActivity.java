package raivona.pmf.funapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String games[] = {"Iks-Oks", "Kviz", "Anagrami"};
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = findViewById(R.id.list_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, R.id.textView, games);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) list.getItemAtPosition(i);
                switch (item) {
                    case "Iks-Oks":
                        startActivity(new Intent(MainActivity.this, raivona.pmf.funapp.iksoks.IksOksActivity.class));
                        break;
                    case "Kviz":
                        startActivity(new Intent(MainActivity.this, raivona.pmf.funapp.kviz.QuizActivity.class));
                        break;
                    case "Anagrami":
                        startActivity(new Intent(MainActivity.this, raivona.pmf.funapp.anagrami.AnagramActivity.class));
                        break;
                }
            }
        });
    }
}
