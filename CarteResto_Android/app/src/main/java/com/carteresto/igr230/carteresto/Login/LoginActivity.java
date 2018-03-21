package com.carteresto.igr230.carteresto.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.database.Cursor;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carteresto.igr230.carteresto.MenuPrincipale.MenuPrincipalActivity;
import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.MyApplication;
import com.carteresto.igr230.carteresto.R;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    MyApplication application;
    static final int NO_DISPONIBLE = 0;
    static final int DISPONIBLE = 1;
    static final String PASSWORD = "123456789";
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    @BindView(R.id.tabele_number)
    AutoCompleteTextView mTableView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.sign_in_button)
    Button mSignInButton;
    @BindView( R.id.login_form)
    View mLoginFormView;

    MyHandler handler;
    static private String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        application = (MyApplication) getApplication();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        handler = new MyHandler(this);
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

         // Store values at the time of the login attempt.
        final String tableNb = mTableView.getText().toString();
        final String password = mPasswordView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            mPasswordView.isFocused();
            return;
        }

        if (!TextUtils.isEmpty(tableNb) && !isTableValid(tableNb)) {
            mTableView.setError(getString(R.string.error_invalid_table));
            mTableView.isFocused();
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Table info/" + tableNb);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message msg = new Message();
                String cmdId;
                if(dataSnapshot.exists()){
                    Log.d(TAG, "onDataChange: Table " + tableNb + " is not free!");
                    msg.what = NO_DISPONIBLE;
                    cmdId = dataSnapshot.getValue(String.class);
                }else{
                    Log.d(TAG, "onDataChange: Table " + tableNb + " is free!");
                    msg.what = DISPONIBLE;
                    cmdId = application.getCmdNumber();
                }

                Command cmd = new Command(cmdId, tableNb);
                msg.obj = cmd;
                handler.sendMessage(msg);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: Can not the info about the table!" );
            }
        });
    }

    private boolean isTableValid(String table) {
        if( !android.text.TextUtils.isDigitsOnly(table)){
            return false;
        }
        int tableNb = Integer.valueOf(table);
        return  tableNb >0 || tableNb <=20;
    }

    private boolean isPasswordValid(String password) {
        if(password.equals(PASSWORD))
        return password.length() > 4;
        else return false;
    }



    public void handleMessage(final Message msg) {
        Log.d(TAG, "handleMessage: Message:" + msg);
        final Command cmd = (Command) msg.obj;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        if(msg.what == NO_DISPONIBLE){
            alertDialog.setCancelable(true)
                    .setTitle(R.string.title_no_dispo)
                    .setMessage(R.string.msg_no_dispo)
                    .setPositiveButton(R.string.valide, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == DialogInterface.BUTTON_POSITIVE){
                                Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
                                application.setCmdNumber(cmd.getId());
                                startActivity(intent);
                            }
                        }
                    });
        }else if(msg.what == DISPONIBLE ){
            alertDialog.setCancelable(false)
                    .setTitle(R.string.title_dispo)
                    .setMessage(R.string.msg_dispo)
                    .setPositiveButton(R.string.valide, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == DialogInterface.BUTTON_POSITIVE){
                        Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
                        FirebaseDatabaseService.setCmd(cmd);
                        startActivity(intent);
                    }
                }
            });

        }
        alertDialog.show();
    }

}

class MyHandler extends Handler{
    private final WeakReference<LoginActivity> mActivity;

    MyHandler(LoginActivity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg)
    {
        super.handleMessage(msg);
        LoginActivity activity = mActivity.get();
        if (activity != null) {
            activity.handleMessage(msg);
        }
    }
}



