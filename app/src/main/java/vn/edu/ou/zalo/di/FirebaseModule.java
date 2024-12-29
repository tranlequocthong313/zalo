package vn.edu.ou.zalo.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import vn.edu.ou.zalo.BuildConfig;

@Module
@InstallIn(SingletonComponent.class)
public class FirebaseModule {

    // Emulator host and port for Firebase Auth
    private static final String EMULATOR_HOST = "192.168.1.175"; // TODO: Special IP for emulator
    private static final int AUTH_EMULATOR_PORT = 9099;
    private static final int FIRESTORE_EMULATOR_PORT = 8080;
    public static final int FUNCTIONS_EMULATOR_PORT = 5001;

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Use Firebase Emulator in debug builds
        if (BuildConfig.DEBUG) {
            firebaseAuth.useEmulator(EMULATOR_HOST, AUTH_EMULATOR_PORT);
        }

        return firebaseAuth;
    }

    @Provides
    @Singleton
    public FirebaseFirestore provideFirebaseFirestore() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Use Firestore Emulator in debug builds
        if (BuildConfig.DEBUG) {
            firestore.useEmulator(EMULATOR_HOST, FIRESTORE_EMULATOR_PORT);
        }

        return firestore;
    }

    @Provides
    @Singleton
    public FirebaseFunctions provideFirebaseFunctions() {
        FirebaseFunctions functions = FirebaseFunctions.getInstance();

        if (BuildConfig.DEBUG) {
            functions.useEmulator(EMULATOR_HOST, FUNCTIONS_EMULATOR_PORT);
        }
        return functions;
    }
}
