package app.horecafy.com.activities.wholesalers.fragments


import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import app.horecafy.com.R
import app.horecafy.com.adapters.WholesalerNotificationsRecyclerViewAdapter
import app.horecafy.com.models.NotificationsDetails
import app.horecafy.com.models.TimeSlotItems
import app.horecafy.com.services.AuthService
import app.horecafy.com.services.WholesalerService
import app.horecafy.com.util.Constants
import app.horecafy.com.util.UiHelpers
import kotlinx.android.synthetic.main.fragment_wholesaler_notifications.*

class WholesalerNotificationsFragment : Fragment() {

    companion object {
        fun newInstance(): WholesalerNotificationsFragment {
            val fragment = WholesalerNotificationsFragment()
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
        return inflater.inflate(R.layout.fragment_wholesaler_notifications, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure recyclerView
        rvWholesalerNotificationsList.layoutManager = LinearLayoutManager(activity)
        rvWholesalerNotificationsList.itemAnimator = DefaultItemAnimator()

        getWholesalerNotificationsList()
    }

    private fun getWholesalerNotificationsList() {

        UiHelpers.showProgessBar(activity.window, rlProgressBarWholesalerNotifications)

        val wholesalerId = AuthService.wholesaler!!.id!!

        WholesalerService.getWholesalerNotifications(activity, wholesalerId) { status, data, error ->

            UiHelpers.hideProgessBar(activity.window, rlProgressBarWholesalerNotifications)

            if (status && data != null) {

                mNotificationslist = data.toMutableList()

                if (mNotificationslist.size > 0) {

                    rvWholesalerNotificationsList.setVisibility(View.VISIBLE)
                    rlNoWholesalerNotificationsAvailable.setVisibility(View.GONE)

                    // Set the adapter
                    val mAdapter = WholesalerNotificationsRecyclerViewAdapter(mNotificationslist, this)
                    rvWholesalerNotificationsList.adapter = mAdapter
                } else {

                    rvWholesalerNotificationsList.setVisibility(View.GONE)
                    rlNoWholesalerNotificationsAvailable.setVisibility(View.VISIBLE)
                }
            } else {
                Toast.makeText(activity, "" + Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getCustomerAvailabilityList(customerId: String, notificationId: String) {

        UiHelpers.showProgessBar(activity.window, rlProgressBarWholesalerNotifications)

        WholesalerService.getCustomerAvailability(activity, customerId) { status, data, error ->

            UiHelpers.hideProgessBar(activity.window, rlProgressBarWholesalerNotifications)

            if (status) {

//                Log.e("Availability Response", "" + data)
                val strAvailability = data.availability
                setTimeAsPerAvailability(notificationId, strAvailability.toString())
            } else {
                Toast.makeText(activity, "" + Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setTimeAsPerAvailability(notificationId: String, strAvailability: String) {

        val dialogBuilder = AlertDialog.Builder(activity)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_wholesaler_set_availability_time, null)
        dialogBuilder.setView(dialogView)

        val rlProgressBarSetAvailability = dialogView.findViewById<View>(R.id.rlProgressBarSetAvailability) as RelativeLayout
        val acsAvailableTimeSlot = dialogView.findViewById<View>(R.id.acsAvailableTimeSlot) as AppCompatSpinner
        val tvNoTimeslotsAvailable = dialogView.findViewById<View>(R.id.tvNoTimeslotsAvailable) as TextView

        if (!strAvailability.isEmpty()
                && strAvailability.trim().length > 0) {

            tvNoTimeslotsAvailable.setVisibility(View.GONE)
            acsAvailableTimeSlot.setVisibility(View.VISIBLE)
            Log.e("Availability ", "" + strAvailability)
            val result: List<String> = strAvailability!!.split(",").map { it.trim() }
            val arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, result)
            acsAvailableTimeSlot.adapter = arrayAdapter as SpinnerAdapter?
        } else {

            tvNoTimeslotsAvailable.setVisibility(View.VISIBLE)
            acsAvailableTimeSlot.setVisibility(View.GONE)
        }


        var strSelectedValue: String = ""

        acsAvailableTimeSlot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                strSelectedValue = parent.selectedItem.toString().trim()

//                Toast.makeText(activity, "" + strSelectedValue, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        dialogBuilder.setTitle("Seleccionar el intervalo de tiempo disponible")

        if (!strAvailability.isEmpty()
                && strAvailability.trim().length > 0) {
            dialogBuilder.setPositiveButton("Enviar", { dialog, whichButton ->

                //            Toast.makeText(activity, "Submit", Toast.LENGTH_SHORT).show()
                selectCustomerAvailabilityTimeslot(notificationId, strSelectedValue, rlProgressBarSetAvailability, dialog)
//            dialog.dismiss()
            })
        }

        dialogBuilder.setNegativeButton("Cancelar", { dialog, whichButton ->
            dialog.dismiss()
        })

        val b = dialogBuilder.create()
        b.setCanceledOnTouchOutside(false)
        b.show()
    }

    private fun selectCustomerAvailabilityTimeslot(notificationId: String, strSelectedValue: String,
                                                   rlProgressBarSetAvailability: RelativeLayout, dialog: DialogInterface) {

        UiHelpers.showProgessBar(activity.window, rlProgressBarSetAvailability)

        val inputStrTimeslot = TimeSlotItems(strSelectedValue)

        WholesalerService.selectCustomerAvailabilityTimeslot(activity, notificationId, inputStrTimeslot) { status, error ->

            UiHelpers.hideProgessBar(activity.window, rlProgressBarSetAvailability)

            if (status) {

                dialog.dismiss()

                getWholesalerNotificationsList()
            } else {
                Toast.makeText(activity, "" + Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
            }
        }
    }

}