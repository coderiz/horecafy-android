package app.horecafy.com.activities.customers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import app.horecafy.com.R
import app.horecafy.com.adapters.ReviewOfferProductsListRecyclerViewAdapter
import app.horecafy.com.models.ContactOffer
import app.horecafy.com.models.ReviewOfferItems
import app.horecafy.com.models.ReviewOffersProductDetails
import app.horecafy.com.services.AuthService
import app.horecafy.com.services.CustomerService
import app.horecafy.com.util.UiHelpers
import kotlinx.android.synthetic.main.activity_customer_review_offer_products.*
import java.util.*

class CustomerReviewOfferProductsActivity : AppCompatActivity() {

    companion object {
        val INTENT_REVIEW_OFFERS_LIST = "INTENT_REVIEW_OFFERS_LIST"

        fun intent(context: Context, list: ArrayList<ReviewOfferItems>): Intent {
            val intent = Intent(context, CustomerReviewOfferProductsActivity::class.java)
            intent.putParcelableArrayListExtra(INTENT_REVIEW_OFFERS_LIST, list);
            return intent
        }
    }

    var mReviewOffersParentList: ArrayList<ReviewOfferItems>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_review_offer_products)

        if (mReviewOffersParentList == null) {
            mReviewOffersParentList = intent.getParcelableArrayListExtra(CustomerReviewOfferWholesalersActivity.INTENT_REVIEW_OFFERS_LIST)
        }

        val productsList: MutableList<ReviewOffersProductDetails> = mutableListOf()
        // add elements to productsList, including duplicates

        mReviewOffersParentList!!.forEach {
            val item = ReviewOffersProductDetails(it.ProductId.toString(),
                    it.ProductName.toString())
            productsList.add(item)
        }

        val uniqueProducts = HashSet<ReviewOffersProductDetails>(productsList)

        val uniqueProductsList: MutableList<ReviewOffersProductDetails> = mutableListOf()

        uniqueProducts.forEach {

            Log.e("" + it.name, "" + Collections.frequency(productsList, it))
            val item = ReviewOffersProductDetails(it.id.toString(), it.name.toString())
            uniqueProductsList.add(item)
        }

        // Configure recyclerView
        rvReviewOfferProductsList.layoutManager = LinearLayoutManager(this)
        rvReviewOfferProductsList.itemAnimator = DefaultItemAnimator()
        rvReviewOfferProductsList.setHasFixedSize(true)
        rvReviewOfferProductsList.setNestedScrollingEnabled(false)

        // Set the adapter
        val mAdapter = ReviewOfferProductsListRecyclerViewAdapter(uniqueProductsList,
                this@CustomerReviewOfferProductsActivity, mReviewOffersParentList)
        rvReviewOfferProductsList.adapter = mAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home ->
                onBackPressed()
        }
        return true
    }

    fun contactDistributor(wholeSalerId: String) {

        UiHelpers.showProgessBar(window, rlProgressBarReviewOfferProducts)

        val customerId = AuthService.customer!!.id!!

        val inputModel = ContactOffer(customerId, wholeSalerId)

        CustomerService.contactOffer(this, inputModel) { status, error ->

            UiHelpers.hideProgessBar(window, rlProgressBarReviewOfferProducts)

            if (status) {
                startActivity(Intent(this, CustomerReviewOffersThanks::class.java))
            }
        }
    }
}