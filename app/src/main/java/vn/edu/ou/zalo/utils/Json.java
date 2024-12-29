package vn.edu.ou.zalo.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class Json {
    @NonNull
    public static Map<String, Object> getStringObjectMap(Object object) {
        // Safe cast and check if the result is a Map
        if (object instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) object;
            Map<String, Object> safeMap = new HashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey() instanceof String && entry.getValue() != null) {
                    safeMap.put((String) entry.getKey(), entry.getValue());
                } else {
                    throw new IllegalArgumentException("Unexpected key/value type in map: "
                            + entry.getKey().getClass().getSimpleName() + "/"
                            + entry.getValue().getClass().getSimpleName());
                }
            }
            return safeMap;
        } else {
            assert object != null;
            throw new IllegalArgumentException("Expected Map result, but got: " + object.getClass().getSimpleName());
        }
    }
}
