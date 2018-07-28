package app.horecafy.com.activities.customers.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import app.horecafy.com.R
import app.horecafy.com.adapters.CustomerNotificationsRecyclerViewAdapter
import app.horecafy.com.models.NotificationsDetails
import app.horecafy.com.services.AuthService
import app.horecafy.com.services.CustomerService
import app.horecafy.com.util.Constants
import app.horecafy.com.util.UiHelpers
import kotlinx.android.synthetic.main.fragment_customer_notifications.*


class CustomerNotificationsFragment : Fragment() {

    companion object {
        fun newInstance(): CustomerNotificationsFragment {
            val fragment = CustomerNotificationsFragment()
            /*val bundle = Bundle()
            bundle.putString("Text", text)
            fragment.arguments = bundle*/
            return fragment
        }
    }

    var mNotificationslist: MutableList<NotificationsDetails> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_notifications, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure recyclerView
        rvCustomerNotificationsList.layoutManager = LinearLayoutManager(activity)
        rvCustomerNotificationsList.itemAnimator = DefaultItemAnimator()

        getCustomersNotificationsList()
    }

    private fun getCustomersNotificationsList() {

        UiHelpers.showProgessBar(activity.window, rlProgressBarCustomersNotifications)

        val customerId = AuthService.customer!!.id!!

        CustomerService.getCustomersNotifications(activity, customerId) { status, data, error ->

            UiHelpers.hideProgessBar(activity.window, rlProgressBarCustomersNotifications)

            if (status) {

                mNotificationslist = data.toMutableList()

                if (mNotificationslist.size > 0) {

                    rvCustomerNotificationsList.setVisibility(View.VISIBLE)
                    rlNoNotificationsAvailable.setVisibility(View.GONE)

                    // Set the adapter
                    val mAdapter = CustomerNotificationsRecyclerViewAdapter(mNotificationslist, this)
                    rvCustomerNotificationsList.adapter = mAdapter
                } else {

                    rvCustomerNotificationsList.setVisibility(View.GONE)
                    rlNoNotificationsAvailable.setVisibility(View.VISIBLE)
                }
            } else {
                Toast.makeText(activity, "" + Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun acceptNotification(notificationId: String) {

        CustomerService.acceptNotification(activity, notificationId) { status, error ->

            UiHelpers.hideProgessBar(activity.window, rlProgressBarCustomersNotifications)

            if (status) {

                getCustomersNotificationsList()
            } else {
                Toast.makeText(activity, "" + Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun rejectNotification(notificationId: String) {

        CustomerService.rejectNotification(activity, notificationId) { status, error ->

            UiHelpers.hideProgessBar(activity.window, rlProgressBarCustomersNotifications)

            if (status) {

                getCustomersNotificationsList()
            } else {
                Toast.makeText(activity, "" + Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
            }
        }
    }
}