package app.horecafy.com.activities.wholesalers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import app.horecafy.com.R
import app.horecafy.com.models.Category
import app.horecafy.com.models.Demand
import app.horecafy.com.models.Offer
import app.horecafy.com.services.AuthService
import app.horecafy.com.services.OfferService
import app.horecafy.com.util.UiHelpers
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_wholesaler_offer.*

class WholesalerOfferActivity : AppCompatActivity() {

    companion object {
        val INTENT_CATEGORY = "INTENT_REVIEW_OFFERS_LIST"
        val INTENT_DEMAND = "INTENT_DEMAND"
        val REQUEST_THANKS = 1

        fun intent(context: Context, category: Category, demand: Demand): Intent {
            val intent = Intent(context, WholesalerOfferActivity::class.java)
            intent.putExtra(INTENT_CATEGORY, category);
            intent.putExtra(INTENT_DEMAND, demand);
            return intent
        }
    }

    var category: Category? = null
    var demand: Demand? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wholesaler_offer)

        // Get category and demand from intent and load it from API
        category = intent.getSerializableExtra(INTENT_CATEGORY) as Category
        demand = intent.getSerializableExtra(INTENT_DEMAND) as Demand

        // Bind useful data
        val uri = "${this.getString(R.string.IMAGE_URL)}${category!!.image}"
        Picasso.with(this).load(uri).into(imageCategory)
        textCategory.text = category!!.name

        textHiddenId.text = demand!!.hiddenId.toString()
        textBrand.text = demand!!.brand
        textFormat.text = demand!!.format
        if (demand!!.quantyPerMonth != null) {
            textQuantyPerMonth.text = demand!!.quantyPerMonth.toString()
        }
        if (demand!!.targetPrice != null) {
            textTargetPrice.text = demand!!.targetPrice.toString()
        }
        textComments.text = demand!!.comments

        buttonSave.setOnClickListener(View.OnClickListener {
            if (isValid()) {
                UiHelpers.showProgessBar(window, progressBar)
                val brand = editBrand.text.toString()
                val format = editFormat.text.toString()
                val offerPrice = editOfferPrice.text.toString()
                val comments = editComments.text.toString()

                val offer = Offer(customerId = demand!!.customerId,
                        demandId = demand!!.hiddenId,
                        wholesalerId =  AuthService.wholesaler?.hiddenId!!,
                        quantyPerMonth = demand!!.quantyPerMonth ?: 0,
                        typeOfFormatId = demand!!.typeOfFormatId,
                        offerPrice = offerPrice.toDouble(),
                        brand = brand,
                        fomat = format,
                        comments = comments)

                OfferService.create(this, offer) { status, data, error ->
                    UiHelpers.hideProgessBar(window, progressBar)
                    if (status) {
                        val intent = WholesalerMakeOfferThanks.intent(this)
                        startActivityForResult(intent, REQUEST_THANKS)
                    } else {
                        Toast.makeText(this, "Error desconocido", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun isValid(): Boolean {
        var isValid = true

        val brand = editBrand.text.toString()
        val format = editFormat.text.toString()
        val offerPrice = editOfferPrice.text.toString()

        if (brand.isNullOrEmpty()) {
            editBrand.setError("Este campo es obligatorio")
            isValid = false
        }

        /*if (format.isNullOrEmpty()) {
            editFormat.setError("Este campo es obligatorio")
            isValid = false
        }*/

        if (offerPrice.isNullOrEmpty()) {
            editOfferPrice.setError("Este campo es obligatorio")
            isValid = false
        }

        return isValid
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_THANKS && resultCode == AppCompatActivity.RESULT_CANCELED) {
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
