package app.horecafy.com.activities.customers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import app.horecafy.com.R
import app.horecafy.com.adapters.ReviewOfferWholesalersDetailsRecyclerViewAdapter
import app.horecafy.com.models.ContactOffer
import app.horecafy.com.models.ReviewOfferItems
import app.horecafy.com.models.ReviewOffersWholesalerDetails
import app.horecafy.com.services.AuthService
import app.horecafy.com.services.CustomerService
import app.horecafy.com.util.UiHelpers
import kotlinx.android.synthetic.main.activity_customer_review_offer_wholesalers_details.*
import java.util.*


class CustomerReviewOfferWholesalersDetailsActivity : AppCompatActivity() {

    companion object {
        val INTENT_REVIEW_OFFERS_LIST = "INTENT_REVIEW_OFFERS_LIST"
        val INTENT_SELECTED_WHOLESALER = "INTENT_SELECTED_WHOLESALER"

        fun intent(context: Context, selectedItem: ReviewOffersWholesalerDetails, list: ArrayList<ReviewOfferItems>): Intent {
            val intent = Intent(context, CustomerReviewOfferWholesalersDetailsActivity::class.java)
            intent.putExtra(INTENT_SELECTED_WHOLESALER, selectedItem);
            intent.putParcelableArrayListExtra(INTENT_REVIEW_OFFERS_LIST, list);
            return intent
        }
    }

    var mReviewOffersParentList: ArrayList<ReviewOfferItems>? = null
    var mSelectedWholesaler: ReviewOffersWholesalerDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_review_offer_wholesalers_details)

        if (mReviewOffersParentList == null) {
            mReviewOffersParentList = intent.getParcelableArrayListExtra(INTENT_REVIEW_OFFERS_LIST)
        }

        if (mSelectedWholesaler == null) {
            mSelectedWholesaler = intent.getSerializableExtra(INTENT_SELECTED_WHOLESALER) as ReviewOffersWholesalerDetails?
        }

        tvWholesalerName.setText(mSelectedWholesaler!!.name)

        val wholesalerDetailList: MutableList<ReviewOfferItems> = mutableListOf()

        if (mSelectedWholesaler!!.hiddenId != null) {
            mReviewOffersParentList!!.forEach {
                if (it.WholeSalerHiddenId!!.toString().equals(mSelectedWholesaler!!.hiddenId.toString())) {
                    wholesalerDetailList.add(it)
                }
            }
        }

        // Configure recyclerView
        rvReviewOfferWholesalersDetailList.layoutManager = LinearLayoutManager(this)
        rvReviewOfferWholesalersDetailList.itemAnimator = DefaultItemAnimator()

        // Set the adapter
        val mAdapter = ReviewOfferWholesalersDetailsRecyclerViewAdapter(wholesalerDetailList, this)
        rvReviewOfferWholesalersDetailList.adapter = mAdapter

        tvContactDistributor.setOnClickListener { v: View? ->

            UiHelpers.showProgessBar(window, rlProgressBarReviewOfferWholesalersDetails)

            val customerId = AuthService.customer!!.id!!

            val inputModel = ContactOffer(customerId, mSelectedWholesaler!!.id.toString())

            CustomerService.contactOffer(this, inputModel) { status, error ->

                UiHelpers.hideProgessBar(window, rlProgressBarReviewOfferWholesalersDetails)

                if (status) {
                    startActivity(Intent(this, CustomerReviewOffersThanks::class.java))
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home ->
                onBackPressed()
        }
        return true
    }
}
