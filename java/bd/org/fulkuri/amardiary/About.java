package bd.org.fulkuri.amardiary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class About extends AppCompatActivity {

    private Toolbar mToolbar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ac_about);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvAboutContent = (TextView) findViewById(R.id.tvAboutContents);
        TextView tvAboutTitle = (TextView) findViewById(R.id.tvAboutTitle);

        getSupportActionBar().setTitle("About");
        tvAboutContent.setText(Html.fromHtml(getResources().getString(R.string.about_fulkuri)));
        tvAboutTitle.setText(getResources().getString(R.string.about_title));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
