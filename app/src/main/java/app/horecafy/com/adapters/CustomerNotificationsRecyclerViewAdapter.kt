package app.horecafy.com.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import app.horecafy.com.R
import app.horecafy.com.activities.customers.fragments.CustomerNotificationsFragment
import app.horecafy.com.models.NotificationsDetails


class CustomerNotificationsRecyclerViewAdapter(val mNotificationsList: MutableList<NotificationsDetails>?,
                                               val mFragment: CustomerNotificationsFragment)
    : RecyclerView.Adapter<CustomerNotificationsRecyclerViewAdapter.CategoryViewHolder>() {

    // ViewHolder
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWholesalerName = itemView.findViewById<TextView>(R.id.tvWholesalerName)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val llButtonsLayout = itemView.findViewById<LinearLayout>(R.id.llButtonsLayout)
        val btnReject = itemView.findViewById<Button>(R.id.btnReject)
        val btnAccept = itemView.findViewById<Button>(R.id.btnAccept)
        val llAcceptedLayout = itemView.findViewById<LinearLayout>(R.id.llAcceptedLayout)
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
        val tvAccepted = itemView.findViewById<TextView>(R.id.tvAccepted)

        fun bindItem(listItems: NotificationsDetails, position: Int) {

            tvWholesalerName.text = listItems.Wholesaler!!.name
            tvDescription.text = listItems.comments
            if (!listItems.timeslot.isNullOrBlank()) {
                tvTime.setText(listItems.timeslot)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.recycler_view_item_customer_notifications_layout,
                        parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder?, position: Int) {

        if (mNotificationsList != null) {

            val item = mNotificationsList[position]

            holder?.bindItem(item, position)

            if (item.status) {
                holder!!.llButtonsLayout.setVisibility(View.GONE)
                holder!!.llAcceptedLayout.setVisibility(View.VISIBLE)

                if (item.timeslot.isNullOrEmpty()) {

                    holder!!.tvTime.setVisibility(View.GONE)
                } else {

                    holder!!.tvTime.setVisibility(View.VISIBLE)
                }
            } else {
                holder!!.llButtonsLayout.setVisibility(View.VISIBLE)
                holder!!.llAcceptedLayout.setVisibility(View.GONE)
            }

            holder!!.btnAccept.setOnClickListener({ v: View? ->

                if (mFragment is CustomerNotificationsFragment) {
                    mFragment.acceptNotification(item.id.toString())
                }
            })

            holder!!.btnReject.setOnClickListener({ v: View? ->
                if (mFragment is CustomerNotificationsFragment) {
                    mFragment.rejectNotification(item.id.toString())
                }
            })
        }
    }

    override fun getItemCount() = mNotificationsList?.size ?: 0
}