package app.horecafy.com.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import app.horecafy.com.R
import app.horecafy.com.activities.customers.CustomerEntryActivity
import app.horecafy.com.activities.wholesalers.WholesalerEntryActivity
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        configureUI()
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)

        buttonHoreca.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, WholesalerEntryActivity::class.java))
        })

        buttonCustomer.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, CustomerEntryActivity::class.java))
        })
    }

    private fun configureUI() {
        val window = this.window

        // Hide ActionBar
        window.requestFeature(Window.FEATURE_ACTION_BAR);
        supportActionBar!!.hide();

        // Change StatusBar color
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.backgroundColor);
        }*/
    }
}
