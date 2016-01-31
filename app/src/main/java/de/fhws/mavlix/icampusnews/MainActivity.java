package de.fhws.mavlix.icampusnews;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NewsListViewAdapter.Events {

    Fragment newsListFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Fragment testFragment = new TestFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFrameLayout,testFragment);
        ft.commit();*/

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("FHWS News");

        newsListFragment = new NewsListFragment();
        replaceFragment(newsListFragment);

    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
            ft.replace(R.id.mainFrameLayout, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_reload:
                getSupportFragmentManager().beginTransaction().detach(newsListFragment).attach(newsListFragment).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnNewsClick(News news) {
        NewsFragment newsFragment = new NewsFragment().LoadNews(news);
        replaceFragment(newsFragment);
    }
}
