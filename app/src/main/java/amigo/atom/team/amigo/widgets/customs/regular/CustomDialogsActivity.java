package amigo.atom.team.amigo.widgets.customs.regular;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.common.fixtures.DialogsFixtures;
import amigo.atom.team.amigo.common.model.Dialog;
import amigo.atom.team.amigo.common.demo.DialogsActivity;
import amigo.atom.team.amigo.widgets.customs.regular.holders.dialogs.CustomDialogViewHolder;


public class CustomDialogsActivity extends DialogsActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomDialogsActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_holder_dialogs);

        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        initAdapter();
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        CustomMessagesActivity.open(this);
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(
                R.layout.item_custom_dialog_view_holder,
                CustomDialogViewHolder.class,
                super.imageLoader);

        super.dialogsAdapter.setItems(DialogsFixtures.getDialogs());

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }
}
