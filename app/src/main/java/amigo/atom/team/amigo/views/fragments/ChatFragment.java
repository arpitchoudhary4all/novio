package amigo.atom.team.amigo.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.widgets.customs.regular.CustomMessagesActivity;


public class ChatFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_chat, container, false);

        final Button btnGoChat = (Button) view.findViewById(R.id.btnGoChat);
//        final ImageView imageBot = (ImageView) view.findViewById(R.id.imageBot);

//        Glide.with(getActivity())
//                .load("https://media.giphy.com/media/10zdNpfpsoV4sM/giphy.gif")
//                .asGif()
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageBot);


        btnGoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CustomMessagesActivity.class));
            }
        });
        return this.view;
    }
}
