package amigo.atom.team.amigo.views.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;


import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.adapters.FragmentAdapter;
import amigo.atom.team.amigo.views.fragments.ChatFragment;
import amigo.atom.team.amigo.views.fragments.FindFragment;
import amigo.atom.team.amigo.views.fragments.ProfileFragment;
import amigo.atom.team.amigo.widgets.CustomViewPager;

/*
 * Created by troy379 on 04.04.17.
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        castWidgets();

        initFragmentsWithPager();

        tabLayout.setupWithViewPager(mViewPager);

        initTabIcons();

    }

    private void initTabIcons() {
        int[] icons = {
                R.drawable.ic_chat,
                R.drawable.ic_map,
                R.drawable.ic_user_profile
        };

        for(int i=0; i<fragmentAdapter.getCount(); i++){
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }


    private void initFragmentsWithPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChatFragment());
        fragments.add(new FindFragment());
        fragments.add(new ProfileFragment());

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(fragmentAdapter);
    }

    private void castWidgets() {

        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

}
