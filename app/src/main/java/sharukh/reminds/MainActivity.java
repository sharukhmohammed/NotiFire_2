package sharukh.reminds;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import sharukh.reminds.consts.Reminder;

@SuppressLint({"UseSparseArrays"})
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{


    public EditText title, text, time;

    public Map<Integer, Reminder> reminderMap = new HashMap<>();

    public Reminder newReminder;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        Paper.init(context);
        reminderMap = Paper.book().read("reminderMap", new HashMap<Integer, Reminder>());

        setContentView(R.layout.activity_main);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        title = (EditText) findViewById(R.id.activity_main_editText_title);
        text = (EditText) findViewById(R.id.activity_main_editText_text);
        time = (EditText) findViewById(R.id.activity_main_editText_time);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.fab:
            {
                //TODO: Validations for Text fields
                newReminder = new Reminder(title.getText().toString(), text.getText().toString(), time.getText().toString());
                Snackbar.make(view, String.valueOf(newReminder.getID()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                break;
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Paper.book().write("reminderMap",reminderMap);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i("OnResume", reminderMap.values().toString());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("Last Modified", String.valueOf(Paper.book().lastModified("reminderMap")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
