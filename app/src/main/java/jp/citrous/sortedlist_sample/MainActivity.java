package jp.citrous.sortedlist_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportFragmentManager().findFragmentByTag(MainFragment.TAG) == null) {
            MainFragment fragment = MainFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, fragment, MainFragment.TAG)
                    .commit();
        }
    }

}
