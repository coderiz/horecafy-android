package app.horecafy.com.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.horecafy.com.R
import app.horecafy.com.activities.customers.CustomerReviewOfferProductsActivity
import app.horecafy.com.models.ReviewOfferItems


class ReviewOfferProductDistributorsListRecyclerViewAdapter(val mProductsList: MutableList<ReviewOfferItems>?,
                                                            val mContext: Context)
    : RecyclerView.Adapter<ReviewOfferProductDistributorsListRecyclerViewAdapter.CategoryViewHolder>() {

    // ViewHolder
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvDistributorName = itemView.findViewById<TextView>(R.id.tvDistributorName)
        val tvContactDistributor = itemView.findViewById<TextView>(R.id.tvContactDistributor)
        val tvBrand = itemView.findViewById<TextView>(R.id.tvBrand)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvFormat = itemView.findViewById<TextView>(R.id.tvFormat)
        val tvComments = itemView.findViewById<TextView>(R.id.tvComments)

        fun bindItem(listItems: ReviewOfferItems, position: Int) {

            tvDistributorName.setText(listItems.WholeSalerName)
            tvBrand.setText(listItems.brand)
            tvPrice.setText(listItems.offerPrice.toString())
            tvFormat.setText(listItems.fomat)
            tvComments.setText(listItems.comments)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.recycler_view_item_review_offer_product_distributors,
                        parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder?, position: Int) {

        if (mProductsList != null) {

            val item = mProductsList[position]

            holder?.bindItem(item, position)

            holder!!.tvContactDistributor.setOnClickListener { v: View? ->

                if (mContext is CustomerReviewOfferProductsActivity){
                    mContext.contactDistributor(item.WholeSalerId.toString())
                }
            }
        }
    }

    override fun getItemCount() = mProductsList?.size ?: 0
}