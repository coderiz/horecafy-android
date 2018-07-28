package app.horecafy.com.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import app.horecafy.com.R
import app.horecafy.com.activities.wholesalers.fragments.WholesalerNotificationsFragment
import app.horecafy.com.models.NotificationsDetails


class WholesalerNotificationsRecyclerViewAdapter(val mNotificationsList: MutableList<NotificationsDetails>?,
                                                 val mFragment: WholesalerNotificationsFragment)
    : RecyclerView.Adapter<WholesalerNotificationsRecyclerViewAdapter.CategoryViewHolder>() {

    // ViewHolder
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRestaurantsName = itemView.findViewById<TextView>(R.id.tvRestaurantsName)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val btnSetTime = itemView.findViewById<Button>(R.id.btnSetTime)
        val tvSelectedTime = itemView.findViewById<TextView>(R.id.tvSelectedTime)
        val tvAccepted = itemView.findViewById<TextView>(R.id.tvAccepted)

        fun bindItem(listItems: NotificationsDetails, position: Int) {

            tvRestaurantsName.text = listItems.Customer!!.name
            tvDescription.text = listItems.comments
            if (!listItems.timeslot.isNullOrEmpty()) {
                tvSelectedTime.setText(listItems.timeslot)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.recycler_view_item_wholesaler_notifications_layout,
                        parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder?, position: Int) {

        if (mNotificationsList != null) {

            val item = mNotificationsList[position]

            holder?.bindItem(item, position)


            if (item.timeslot.isNullOrEmpty()) {

                holder!!.btnSetTime.setVisibility(View.VISIBLE)
                holder!!.tvSelectedTime.setVisibility(View.GONE)
            } else {

                holder!!.btnSetTime.setVisibility(View.GONE)
                holder!!.tvSelectedTime.setVisibility(View.VISIBLE)
            }

            holder!!.btnSetTime.setOnClickListener({ v: View? ->

                if (mFragment is WholesalerNotificationsFragment) {
//                    mFragment.setTimeAsPerAvailability(item.Customer!!.id.toString())

                    mFragment.getCustomerAvailabilityList(item.Customer!!.id.toString(), item.id.toString())
                }
            })
        }
    }

    override fun getItemCount() = mNotificationsList?.size ?: 0
}