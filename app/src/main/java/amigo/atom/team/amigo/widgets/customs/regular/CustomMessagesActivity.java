package amigo.atom.team.amigo.widgets.customs.regular;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.api.DocBot;
import amigo.atom.team.amigo.common.demo.MessagesActivity;
import amigo.atom.team.amigo.common.fixtures.MessagesFixtures;
import amigo.atom.team.amigo.common.model.Message;
import amigo.atom.team.amigo.utils.AppUtils;
import amigo.atom.team.amigo.widgets.customs.regular.holders.messages.CustomIncomingImageMessageViewHolder;
import amigo.atom.team.amigo.widgets.customs.regular.holders.messages.CustomIncomingTextMessageViewHolder;
import amigo.atom.team.amigo.widgets.customs.regular.holders.messages.CustomOutcomingImageMessageViewHolder;
import amigo.atom.team.amigo.widgets.customs.regular.holders.messages.CustomOutcomingTextMessageViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CustomMessagesActivity extends MessagesActivity
        implements MessagesListAdapter.OnMessageLongClickListener<Message>,
        MessageInput.InputListener,
        DialogInterface.OnClickListener,
        MessageInput.AttachmentsListener {

    private String BASE_URL = "http://192.168.43.245:8008/";

    private DatabaseReference dbRef;
    private StorageReference storageRef;
    private Socket socket;
    DataInputStream inputStream;
    MessageInput input;

    private static String currntMsg;

    DataOutputStream outputStream;
    private static final int REQ_CODE_SPEECH_INPUT = 121;


    private TextToSpeech tts;

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomMessagesActivity.class));
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_holder_messages);

        dbRef = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();

        messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);

        messagesAdapter.addToStart(MessagesFixtures.getFakeTextMessage("Hey ! How can i help you?"),true);


        tts = new TextToSpeech(this, status -> {

            if(status != TextToSpeech.ERROR){
                tts.setLanguage(Locale.UK);
                Log.d("TTS", "Init called");
            }

        });

    }

    @Override
    public boolean onSubmit(CharSequence input) {
        Message msg = submitMessage(input.toString());
        new MessageSenderTask().execute(msg.getText());

        return true;
    }

    private Message submitMessage(String input){
        Message msg = MessagesFixtures.getTextMessage(input);
        messagesAdapter.addToStart(msg
                , true);
        return msg;
    }

    @Override
    public void onAddAttachments() {
        new AlertDialog.Builder(this)
                .setItems(new CharSequence[]{"Voice input"}, this)
                .show();
    }

    @Override
    public void onMessageLongClick(Message message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View layout = LayoutInflater.from(this).inflate(R.layout.layout_iptest, null, false);

        final EditText etIp = layout.findViewById(R.id.etIp);

        layout.findViewById(R.id.btnSet).setOnClickListener((v)-> {
            if(TextUtils.isEmpty(etIp.getText().toString().trim()) && TextUtils.isEmpty(etIp.getText().toString().trim())){
                Toast.makeText(this, "Fill the required fields", Toast.LENGTH_SHORT).show();
            } else{
                BASE_URL = "http://" + etIp.getText().toString().trim() + ":8008/";
                Toast.makeText(this, "IP updated succesfully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(layout);
        builder.setCancelable(true);

        AlertDialog feedbackDialog = builder.create();
        feedbackDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        feedbackDialog.show();
    }

    private void initAdapter() {
        MessageHolders holdersConfig = new MessageHolders()
                .setIncomingTextConfig(
                        CustomIncomingTextMessageViewHolder.class,
                        R.layout.item_custom_incoming_text_message)
                .setOutcomingTextConfig(
                        CustomOutcomingTextMessageViewHolder.class,
                        R.layout.item_custom_outcoming_text_message)
                .setIncomingImageConfig(
                        CustomIncomingImageMessageViewHolder.class,
                        R.layout.item_custom_incoming_image_message)
                .setOutcomingImageConfig(
                        CustomOutcomingImageMessageViewHolder.class,
                        R.layout.item_custom_outcoming_image_message);

        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, holdersConfig, super.imageLoader);
        super.messagesAdapter.setOnMessageLongClickListener(this);
//        super.messagesAdapter.setLoadMoreListener(this);
        messagesList.setAdapter(super.messagesAdapter);

    }


    // Showing google speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException aerr) {
            aerr.printStackTrace();
        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    submitMessage(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        askSpeechInput();
    }

    private void speakMyText(String str){
        Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT).show();
        tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
    }

    private class MessageSenderTask extends AsyncTask<String, Void, Void> implements Callback<String> {

        private Gson gson;
        private Retrofit retrofit;



        @Override
        protected void onPreExecute() {}

        @Override
        protected Void doInBackground(String... params) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            DocBot docBot = retrofit.create(DocBot.class);

            Call<String> call = docBot.loadChanges(params[0]);
            call.enqueue(this);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {}


        @Override
        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
            if(response.isSuccessful()) {
                String msg = response.body();


                Log.d("BOT SAY ", msg);

                CustomMessagesActivity.this.speakMyText(msg);
                Message mssg = MessagesFixtures.getFakeTextMessage(msg);
                messagesAdapter.addToStart(mssg, true);

            } else {
                System.out.println(response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
