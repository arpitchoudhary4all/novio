package amigo.atom.team.amigo.widgets.customs.regular.holders.messages;

import android.view.View;

import com.stfalcon.chatkit.messages.MessageHolders;

import amigo.atom.team.amigo.common.model.Message;

public class CustomOutcomingTextMessageViewHolder
        extends MessageHolders.OutcomingTextMessageViewHolder<Message> {

    public CustomOutcomingTextMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

        time.setText(message.getStatus() + " " + time.getText());
    }
}
