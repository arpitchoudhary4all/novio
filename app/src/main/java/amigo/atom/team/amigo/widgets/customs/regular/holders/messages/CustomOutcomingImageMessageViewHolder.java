package amigo.atom.team.amigo.widgets.customs.regular.holders.messages;

import android.view.View;

import com.stfalcon.chatkit.messages.MessageHolders;

import amigo.atom.team.amigo.common.model.Message;

/*
 * Created by troy379 on 05.04.17.
 */
public class CustomOutcomingImageMessageViewHolder
        extends MessageHolders.OutcomingImageMessageViewHolder<Message> {

    public CustomOutcomingImageMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

        time.setText(message.getStatus() + " " + time.getText());
    }
}