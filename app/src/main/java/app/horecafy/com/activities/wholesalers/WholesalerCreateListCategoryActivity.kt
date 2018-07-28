package app.horecafy.com.activities.wholesalers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import app.horecafy.com.R
import app.horecafy.com.adapters.CategoryRecyclerViewAdapter
import app.horecafy.com.models.Category
import app.horecafy.com.services.CommonService
import kotlinx.android.synthetic.main.activity_wholesaler_create_list_category.*

class WholesalerCreateListCategoryActivity : AppCompatActivity() {

    var categories: MutableList<Category> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wholesaler_create_list_category)

        // Configure recyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.itemAnimator = DefaultItemAnimator()
        setAdapter()

        // Load mAvailabilityList
        CommonService.categories(this, { status, data ->
            if (status && data != null) {
//                mAvailabilityList = data.filter { it.id != -1 }
                categories = data
                categories.removeAt(0)
                setAdapter()
            } else {
                // TODO Error loading mAvailabilityList
            }
        })
    }

    private fun setAdapter() {
        // Set the adapter
        val adapter = CategoryRecyclerViewAdapter(this@WholesalerCreateListCategoryActivity, categories)
        recyclerView.adapter = adapter

        // Handle click
        adapter.onClickListener = View.OnClickListener { v: View? ->
            // Get selected category
            val position = recyclerView.getChildAdapterPosition(v)
            val category = categories[position]

            // Start family activity
            val intent = WholesalerCreateListOffersActivity.intent(this, category)
            startActivity(intent)
        }
    }
}
