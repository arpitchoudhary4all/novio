package amigo.atom.team.amigo.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.views.activities.MainActivity;
import amigo.atom.team.amigo.views.activities.AuthActivity;


public class SigninFragment extends Fragment {

    private View view;
    private Button btnSignIn;
    private TextView linkToSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_signin, container, false);

        btnSignIn = (Button) view.findViewById(R.id.btnSignin);
        linkToSignUp = (TextView) view.findViewById(R.id.linkToSignup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        linkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthActivity)getActivity()).nextPage();
            }
        });
        return this.view;
    }

}
