package app.horecafy.com.activities.customers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import app.horecafy.com.R
import app.horecafy.com.adapters.ReviewOfferWholesalersListRecyclerViewAdapter
import app.horecafy.com.models.ReviewOfferItems
import app.horecafy.com.models.ReviewOffersWholesalerDetails
import kotlinx.android.synthetic.main.activity_customer_review_offer_wholesalers.*
import java.util.*

class CustomerReviewOfferWholesalersActivity : AppCompatActivity() {

    companion object {
        val INTENT_REVIEW_OFFERS_LIST = "INTENT_REVIEW_OFFERS_LIST"

        fun intent(context: Context, category: ArrayList<ReviewOfferItems>): Intent {
            val intent = Intent(context, CustomerReviewOfferWholesalersActivity::class.java)
            intent.putParcelableArrayListExtra(INTENT_REVIEW_OFFERS_LIST, category);
            return intent
        }
    }

    var mReviewOffersParentList: ArrayList<ReviewOfferItems>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_review_offer_wholesalers)

        // Get category from intent
        if (mReviewOffersParentList == null) {
            mReviewOffersParentList = intent.getParcelableArrayListExtra(INTENT_REVIEW_OFFERS_LIST)
        }

        val distributorsNamesList: MutableList<ReviewOffersWholesalerDetails> = mutableListOf()
        // add elements to distributorsNamesList, including duplicates

        mReviewOffersParentList!!.forEach {
            val item = ReviewOffersWholesalerDetails(it.WholeSalerHiddenId, it.WholeSalerId,
                    it.WholeSalerName.toString(), 0)
            distributorsNamesList.add(item)
        }

        val unique = HashSet<ReviewOffersWholesalerDetails>(distributorsNamesList)

        val wholesalersList: MutableList<ReviewOffersWholesalerDetails> = mutableListOf()

        unique.forEach {

            //                    Log.e("" + it.name, "" + Collections.frequency(distributorsNamesList, it))
            val item = ReviewOffersWholesalerDetails(it.hiddenId, it.id,
                    it.name.toString(), Collections.frequency(distributorsNamesList, it))
            wholesalersList.add(item)
        }

//                Log.e("Wholesalers List size", "" + wholesalersList.size)

        // Configure recyclerView
        rvReviewOfferWholesalersList.layoutManager = LinearLayoutManager(this)
        rvReviewOfferWholesalersList.itemAnimator = DefaultItemAnimator()

        // Set the adapter
        val mAdapter = ReviewOfferWholesalersListRecyclerViewAdapter(wholesalersList, this)
        rvReviewOfferWholesalersList.adapter = mAdapter

        tvSharedProductsCount.setText(mReviewOffersParentList!!.size.toString())
        tvProductsOfferedCount.setText(wholesalersList.size.toString())
    }

    fun showDetailScreen(item: ReviewOffersWholesalerDetails) {

        val intent = CustomerReviewOfferWholesalersDetailsActivity.intent(this, item,
                mReviewOffersParentList as ArrayList<ReviewOfferItems>)

        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home ->
                onBackPressed()
        }
        return true
    }
}