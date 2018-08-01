package app.horecafy.com.activities.wholesalers.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import app.horecafy.com.R
import app.horecafy.com.models.ProposalItems
import app.horecafy.com.models.RestaurantsTypes
import app.horecafy.com.services.AuthService
import app.horecafy.com.services.WholesalerService
import app.horecafy.com.util.Constants
import app.horecafy.com.util.UiHelpers
import kotlinx.android.synthetic.main.fragment_wholesaler_submit_proposals.*


class WholesalerSubmitProposalsFragment : Fragment() {

    var mBusinessTypeslist: MutableList<RestaurantsTypes> = mutableListOf()
    var mRestaurantsNamesList: MutableList<String> = mutableListOf()
    var mSelectedRestaurantsId: String = ""

    companion object {
        fun newInstance(): WholesalerSubmitProposalsFragment {
            val fragment = WholesalerSubmitProposalsFragment()
            /*val bundle = Bundle()
            bundle.putString("Text", text)
            fragment.arguments = bundle*/
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wholesaler_submit_proposals, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRestaurantsNamesList.add(Constants.CONSTANT_SELECT_THE_TYPE_OF_RESTAURANTS)

        if (acsTypeofRestaurants != null) {
            acsTypeofRestaurants.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    if (mRestaurantsNamesList[position].equals(Constants.CONSTANT_SELECT_THE_TYPE_OF_RESTAURANTS)) {

                        mSelectedRestaurantsId = ""
                    } else {

                        mSelectedRestaurantsId = mBusinessTypeslist[position - 1].id.toString()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    mSelectedRestaurantsId = ""
                }
            }
        }

        loadBusinessTypesList()

        btnSubmitProposal.setOnClickListener({ v: View? ->

            if (isValid()) {

                val strZipCode = etZipCode.text.toString()
                val strProposalDescription = etProposalDescription.text.toString()

                UiHelpers.showProgessBar(activity.window, rlProgressBarSubmitProposal)

                val wholesalerId = AuthService.wholesaler!!.hiddenId!!
//                Log.e("Wholesaler HiddenId", "" + wholesalerId)

                val id = AuthService.wholesaler!!.id!!
//                Log.e("Wholesaler Id", "" + id)

                val inputStrProposal = ProposalItems(wholesalerId,
                        strZipCode, mSelectedRestaurantsId, strProposalDescription)

                WholesalerService.submitProposal(activity, inputStrProposal) { status, error ->

                    UiHelpers.hideProgessBar(activity.window, rlProgressBarSubmitProposal)

                    if (status) {

//                        Toast.makeText(activity, "Su propuesta enviada con éxito.", Toast.LENGTH_LONG).show()
                        Toast.makeText(activity, "propuesta enviada correctamente.", Toast.LENGTH_LONG).show()

                        etZipCode.setText(Constants.CONSTANT_EMPTY_STRING)
                        acsTypeofRestaurants.setSelection(0)
                        etProposalDescription.setText(Constants.CONSTANT_EMPTY_STRING)
                    } else {

                        Toast.makeText(activity, "Algo salió mal. Inténtalo de nuevo.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }


    private fun loadBusinessTypesList() {
        UiHelpers.showProgessBar(activity.window, rlProgressBarSubmitProposal)
        WholesalerService.getBusinessTypeList(activity) { status, data, error ->
            UiHelpers.hideProgessBar(activity.window, rlProgressBarSubmitProposal)
            if (status) {

                mBusinessTypeslist = data.toMutableList()
                mBusinessTypeslist.removeAt(0)

                for (i in mBusinessTypeslist.indices) {

                    mRestaurantsNamesList.add(mBusinessTypeslist[i].name)
                }

                if (mRestaurantsNamesList.isEmpty()) {
                    Log.e("List", "Empty")
                } else {
                    Log.e("List Size", "" + mRestaurantsNamesList.size)
                    val arrayAdapter = ArrayAdapter(activity,
                            android.R.layout.simple_spinner_dropdown_item, mRestaurantsNamesList)
                    acsTypeofRestaurants.adapter = arrayAdapter as SpinnerAdapter?
                }
            }
        }
    }

    private fun isValid(): Boolean {
        var isValid = true

        val strZipCode = etZipCode.text.toString()
        val strProposalDescription = etProposalDescription.text.toString()

        /*if (mSelectedRestaurantsId.isEmpty()) {
            Toast.makeText(activity,
                    "Seleccione el tipo de restaurantes.", Toast.LENGTH_SHORT).show()
            isValid = false
        }*/

        if (strZipCode.isNullOrEmpty()) {
            etZipCode.setError("Este campo es obligatorio")
            isValid = false
        }

        if (strProposalDescription.isNullOrEmpty()) {
            etProposalDescription.setError("Este campo es obligatorio")
            isValid = false
        }

        return isValid
    }
}