package app.horecafy.com.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.horecafy.com.R
import app.horecafy.com.models.ReviewOfferItems


class ReviewOfferWholesalersDetailsRecyclerViewAdapter(val mList: MutableList<ReviewOfferItems>?,
                                                       val context: Context)
    : RecyclerView.Adapter<ReviewOfferWholesalersDetailsRecyclerViewAdapter.CategoryViewHolder>() {

    // ViewHolder
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvProductName = itemView.findViewById<TextView>(R.id.tvProductName)
        val tvBrand = itemView.findViewById<TextView>(R.id.tvBrand)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvFormat = itemView.findViewById<TextView>(R.id.tvFormat)
        val tvComments = itemView.findViewById<TextView>(R.id.tvComments)

        fun bindItem(listItems: ReviewOfferItems, position: Int) {

            tvProductName.setText(listItems.ProductName)
            tvBrand.setText(listItems.brand)
            tvPrice.setText(listItems.offerPrice.toString())
            tvFormat.setText(listItems.fomat)
            tvComments.setText(listItems.comments)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.recycler_view_item_review_offer_wholesalers_detail,
                        parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder?, position: Int) {

        if (mList != null) {

            val item = mList[position]

            holder?.bindItem(item, position)
        }
    }

    override fun getItemCount() = mList?.size ?: 0
}