package coder.siy.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.huawei.hms.api.HookTest

/**
 * Created by Siy on 2018/5/16.
 *
 * @author Siy
 * @date 2018/5/16.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
//        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//        if (packageInfo?.signatures != null && packageInfo.signatures.isNotEmpty()) {
//            return packageInfo.signatures[0].toByteArray();
//        }
//        Log.e("sdh", packageInfo.signatures)
        HookTest.hook("s")
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}