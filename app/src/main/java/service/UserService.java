package service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.User;

/**
 * Created by omaabdillah on 4/14/17.
 */

public class UserService {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "UserService";
    private static final String childNode = "user";

    public static void createNewUser(User user, final CreateNewUserListener listener) {

        final DatabaseReference newRef = mDatabase.child(childNode).child(user.username);
        newRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d(TAG, databaseError.toString());
                }
                listener.onInsertSessionCompleted(databaseError == null);
            }
        });

    }

    public interface CreateNewUserListener {
        void onInsertSessionCompleted(boolean isSuccess);
    }
}
