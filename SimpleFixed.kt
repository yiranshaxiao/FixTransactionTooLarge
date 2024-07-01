import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.collection.LruCache
import java.util.UUID

/**
 * kotlin version
 * [Thanks](https://juejin.cn/post/7306388118914973734)
 */
object SimpleFixed {
    private const val KEY = "TransactionTooLargeOptKey"
    private var map: LruCache<String, Bundle> = LruCache(32)

    @JvmStatic
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            DefaultActivityLifecycleCallbacks() {
            override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
                val uuid = UUID.randomUUID().toString()
                val newBundle = Bundle()
                newBundle.putAll(outState)
                map.put(uuid, newBundle)
                outState.clear()
                outState.putString(KEY, uuid)
            }

            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                savedInstanceState?.let {
                    val uuid = it.getString(KEY) ?: return
                    val bundle = map.get(uuid)
                    savedInstanceState.clear()
                    savedInstanceState.putAll(bundle)
                }
            }
        })
    }
}

open class DefaultActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}
