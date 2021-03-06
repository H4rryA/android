package illinois.nao.nao.User;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by franklinye on 12/5/16.
 */

public class User {

    private static final String TAG = "User";

    private String displayName;
    private String userName;
    private String profileDescription;
    private String email;
    private String uid;

    public User() {

    }

    public User(FirebaseUser firebaseUser, String displayName, String userName) {
        this.displayName = displayName;
        this.userName = userName;
        this.email = firebaseUser.getEmail();
        this.uid = firebaseUser.getUid();
        this.profileDescription = "I'm Nao a user and I'm really lame!";

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName).build();
        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }
}
