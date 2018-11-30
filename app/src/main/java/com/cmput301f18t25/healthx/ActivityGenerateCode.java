package com.cmput301f18t25.healthx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ExecutionException;

public class ActivityGenerateCode extends AppCompatActivity {

    Button generate_btn;
    TextView code_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        generate_btn = (Button) findViewById(R.id.btn_generate);
        code_output = (TextView) findViewById(R.id.code_output);

        Bundle bundle = this.getIntent().getExtras();
        String Bid = bundle.getString("id");
        String Bemail = bundle.getString("email");
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        User user = null;
        try {
            user = getUserTask.execute(Bid,Bemail).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        code_output.setText(user.getCode());


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Bundle bundle = null;
            bundle = this.getIntent().getExtras();
            Intent intent = new Intent(this, ViewProblemList.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void generateCode(View view) {
        code_output = (TextView) findViewById(R.id.code_output);
        String new_code = RandomStringUtils.randomAscii(RandomUtils.nextInt(3,7));
        code_output.setText(new_code);
        Bundle bundle = this.getIntent().getExtras();
        String Bid = bundle.getString("id");
        String Bemail = bundle.getString("email");
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        User user = null;
        try {
            user = getUserTask.execute(Bid,Bemail).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        user.setCode(new_code);

    }
}
