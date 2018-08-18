package amigo.atom.team.amigo.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.adapters.FragmentAdapter;
import amigo.atom.team.amigo.views.fragments.ConfirmSignupFragment;
import amigo.atom.team.amigo.views.fragments.SigninFragment;
import amigo.atom.team.amigo.views.fragments.SignupFragment;
import amigo.atom.team.amigo.widgets.CustomViewPager;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class AuthActivity extends AppCompatActivity {

    private ImageView bgImageview;
    private CustomViewPager pager;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


        setContentView(R.layout.activity_auth);

        castWidgets();

        Glide.with(this)
                .load(R.drawable.bg_theme1)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bgImageview);


        initFragmentsWithPager();
    }

    private void initFragmentsWithPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new SigninFragment());
        fragments.add(new SignupFragment());
        fragments.add(new ConfirmSignupFragment());

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(fragmentAdapter);
        pager.setPagingEnabled(false);
        pager.changePagerScroller();
    }

    private void castWidgets() {
        bgImageview = (ImageView) findViewById(R.id.bgImage);
        pager = (CustomViewPager) findViewById(R.id.fragmentViewPager);
    }

    public void nextPage(){
        if(pager.getChildCount() > pager.getCurrentItem()) {
            pager.setCurrentItem(pager.getCurrentItem() + 1);
        }
    }

    public void previousPage() {
        if (pager.getCurrentItem() > 0) {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem() > 0){
            this.previousPage();
        } else {
            super.onBackPressed();
        }
    }
}
