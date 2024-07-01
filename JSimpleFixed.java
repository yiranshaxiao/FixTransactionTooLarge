import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

/**
 * java version
 * <a href="https://juejin.cn/post/7306388118914973734">Thanks</a>
 */
public class JSimpleFixed {
    private static final String KEY = "TransactionTooLargeOptKey";
    private static LruCache<String, Bundle> map = new LruCache<>(32);

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(new DefaultActivityLifecycleCallbacks() {
            @Override
            public void onActivityPostSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                String uuid = UUID.randomUUID().toString();
                Bundle newBundle = new Bundle();
                newBundle.putAll(outState);
                map.put(uuid, newBundle);
                outState.clear();
                outState.putString(KEY, uuid);
            }

            @Override
            public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                if (savedInstanceState == null) return;
                String uuid = savedInstanceState.getString(KEY);
                if (TextUtils.isEmpty(uuid)) return;
                Bundle bundle = map.get(uuid);
                savedInstanceState.clear();
                savedInstanceState.putAll(bundle);
            }
        });
    }
    
    public static class JDefaultActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
        }
    }
}
