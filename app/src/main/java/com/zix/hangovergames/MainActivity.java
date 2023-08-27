package com.zix.hangovergames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;
import com.google.android.gms.games.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PlayGamesAuthCredential;
import com.google.firebase.auth.PlayGamesAuthProvider;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
        setTimeout(2000, ()->{

            auth = FirebaseAuth.getInstance();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id_1)).requestEmail().build();
            GoogleSignInClient client = GoogleSignIn.getClient(this,gso);
            Intent intent = client.getSignInIntent();
            startActivityForResult(intent, 3);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode ==3) {
            txt.append("11");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account);

            } catch (ApiException e) {
                txt.append("3");
            }
        }
    }



    private void firebaseAuth(GoogleSignInAccount account) {
        txt.append("4");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener((task)->{
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setTimeout(int millis, Runnable callback) {
        try {
            Thread.sleep(millis);
            callback.run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

