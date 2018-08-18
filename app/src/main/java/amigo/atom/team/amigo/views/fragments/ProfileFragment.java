package amigo.atom.team.amigo.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import amigo.atom.team.amigo.R;


public class ProfileFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView dp = (ImageView)view.findViewById(R.id.dp);
        Glide.with(getActivity())
                .load(R.drawable.test_dp)
                .centerCrop()
                .into(dp);
        
        return this.view;
    }
}
