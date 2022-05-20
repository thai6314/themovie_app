package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.btl_android.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import activities.LoginActivity;
import activities.MainActivity;
import models.User;

public class FragmentProfile extends Fragment {
    private MainActivity mainActivity;
    private User user;
    private TextView prUsername, prName;
    private ImageView avatar;
    private LinearLayout signOut, favorite;
    private GoogleSignInAccount account;
    private GoogleSignInClient gsc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUser();
        account = mainActivity.getAccount();
        gsc = mainActivity.getGsc();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prName = view.findViewById(R.id.prName);
        prUsername = view.findViewById(R.id.prUsername);
        avatar = view.findViewById(R.id.avatar);
        signOut = view.findViewById(R.id.signOut);
        favorite = view.findViewById(R.id.favorite);

        if(user != null){
            prName.setText(user.getName());
            prUsername.setText(user.getUsername());
            Picasso.get().load(user.getAvatar()).into(avatar);

            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(),
                            LoginActivity.class));
                }
            });
        }
        else if(account!= null){
            prName.setText(account.getDisplayName());
            prUsername.setText(account.getEmail());
            Picasso.get().load(account.getPhotoUrl()).into(avatar);

            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete( Task<Void> task) {
                            getActivity().finish();
                            startActivity(new Intent(getActivity(),
                                    LoginActivity.class));

                        }
                    });
                }
            });
        }


    }
}
