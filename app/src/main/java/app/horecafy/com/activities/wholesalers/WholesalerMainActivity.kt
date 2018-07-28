package app.horecafy.com.activities.wholesalers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import app.horecafy.com.R
import app.horecafy.com.activities.MainActivity
import app.horecafy.com.services.AuthService
import kotlinx.android.synthetic.main.activity_wholesaler_main.*


class WholesalerMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wholesaler_main)

        wholesalerCreateListButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, WholesalerCreateListCategoryActivity::class.java))
        })

        wholesalerMakeOffersButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, WholesalerMakeOfferCategoryActivity::class.java))
        })

        wholesalerDownloadOffersButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, WholesalerDownloadOffersActivity::class.java))
        })

        wholesalerUploadOffersButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, WholesalerUploadOffersActivity::class.java))
        })

        wholesalerCommercialVisitButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, WholesalerCommercialVisitsActivity::class.java))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_profile -> {
                startActivity(Intent(this, WholesalerProfileActivity::class.java))

                return true
            }
            R.id.action_logout -> {
                AuthService.logout(this)

                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
