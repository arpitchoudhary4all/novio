package amigo.atom.team.amigo.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.views.activities.AuthActivity;


public class SignupFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_signup, container, false);

        final Button btnNext = (Button) view.findViewById(R.id.btnNext);
        final ImageButton btnBack = (ImageButton) view.findViewById(R.id.btnBack);
        final TextView linkToSignIn = (TextView) view.findViewById(R.id.linkToSignin);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthActivity)getActivity()).previousPage();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthActivity)getActivity()).nextPage();
            }
        });

        linkToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBack.callOnClick();
            }
        });

        return this.view;
    }
}
