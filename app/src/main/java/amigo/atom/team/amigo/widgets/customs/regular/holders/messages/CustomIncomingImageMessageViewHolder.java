package amigo.atom.team.amigo.widgets.customs.regular.holders.messages;

import android.view.View;

import com.stfalcon.chatkit.messages.MessageHolders;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.common.model.Message;


/*
 * Created by troy379 on 05.04.17.
 */
public class CustomIncomingImageMessageViewHolder
        extends MessageHolders.IncomingImageMessageViewHolder<Message> {

    private View onlineIndicator;

    public CustomIncomingImageMessageViewHolder(View itemView) {
        super(itemView);
        onlineIndicator = itemView.findViewById(R.id.onlineIndicator);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

//        boolean isOnline = message.getUser().isOnline();
        boolean isOnline = true;
        if (isOnline) {
            onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_online);
        } else {
            onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_offline);
        }
    }
}