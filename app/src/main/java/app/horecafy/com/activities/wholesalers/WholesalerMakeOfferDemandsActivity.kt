package app.horecafy.com.activities.wholesalers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import app.horecafy.com.R
import app.horecafy.com.models.Category
import app.horecafy.com.models.Demand
import app.horecafy.com.services.AuthService
import app.horecafy.com.services.DemandService
import app.horecafy.com.util.Constants
import app.horecafy.com.util.Helpers
import app.horecafy.com.util.UiHelpers
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_wholesaler_make_offer_demands.*


class WholesalerMakeOfferDemandsActivity : AppCompatActivity() {

    companion object {
        val INTENT_CATEGORY = "INTENT_REVIEW_OFFERS_LIST"
        val REQUEST_OFFER = 1

        fun intent(context: Context, category: Category): Intent {
            val intent = Intent(context, WholesalerMakeOfferDemandsActivity::class.java)
            intent.putExtra(INTENT_CATEGORY, category);
            return intent
        }
    }

    var category: Category? = null
    var demands: MutableList<Demand> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wholesaler_make_offer_demands)

        // Get category from intent
        if (category == null) {
            category = intent.getSerializableExtra(INTENT_CATEGORY) as Category?
        }

        // Bind useful data
        val uri = "${this.getString(R.string.IMAGE_URL)}${category!!.image}"
        Picasso.with(this).load(uri).into(imageCategory)
        textCategory.text = category!!.name

        // Load demand for the selected category related with the wholesaler
        loadDemands()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_OFFER && resultCode == AppCompatActivity.RESULT_OK) {
            // Reload demands
            loadDemands()
        }
    }

    private fun loadDemands() {
        // Load demands
        UiHelpers.showProgessBar(window, progressBar)
        DemandService.getByWholesaler(this, AuthService.wholesaler?.hiddenId!! ,category!!.id, { status, data, error ->
            UiHelpers.hideProgessBar(window, progressBar)
            if (status && data != null) {
                demands = data.toMutableList()
                setDemandsAdapter()
            } else {
                // TODO Error loading demands
                Log.w(Constants.TAG, "Error loading demands: ${error}")
            }
        })
    }

    private fun setDemandsAdapter() {
        listDemands.adapter = object: ArrayAdapter<Demand>(this, R.layout.list_view_item_demand, demands) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_view_item_demand, parent, false)

                val textCustomerName = view.findViewById<TextView>(R.id.textCustomerName)
                val textFamilyName = view.findViewById<TextView>(R.id.textFamilyName)
                val textZipCode = view.findViewById<TextView>(R.id.textZipCode)
                val textSentTo = view.findViewById<TextView>(R.id.textSentTo)

                val demand = demands[position]

                textCustomerName.text = demand.customerId.toString()
                textFamilyName.text = demand.familyName.toString()
                textZipCode.text = demand.zipCode

                val date = Helpers.StringToDate(demand.sentTo!!)
                textSentTo.text = Helpers.DateToString(date)

                        // Handle click
                view.setOnClickListener(View.OnClickListener {
                    // Update demand
                    val intent = WholesalerOfferActivity.intent(context, category!!, demand)
                    startActivityForResult(intent, REQUEST_OFFER)
                })

                return view
            }
        }
    }
}
