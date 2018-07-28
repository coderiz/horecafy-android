package app.horecafy.com.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.horecafy.com.R
import app.horecafy.com.activities.customers.CustomerCreateListCategoryActivity
import app.horecafy.com.activities.wholesalers.WholesalerCreateListCategoryActivity
import app.horecafy.com.models.Category
import com.squareup.picasso.Picasso


class CategoryRecyclerViewAdapter(val mContext: Context, val categories: MutableList<Category>?) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    // ViewHolder
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val name = itemView.findViewById<TextView>(R.id.text)
        val totalFamiliesCount = itemView.findViewById<TextView>(R.id.tv_total_families_count)

        fun bindDish(category: Category) {

            if (category.image.equals("First")) {

                Picasso.with(itemView.context).load(R.drawable.ic_create_your_lists).into(image)
                totalFamiliesCount.visibility = View.GONE
            } else {
                val uri = "${itemView.context.getString(R.string.IMAGE_URL)}${category.image}"
                Picasso.with(itemView.context).load(uri).into(image)

                if (mContext is CustomerCreateListCategoryActivity) {
                    totalFamiliesCount.visibility = View.VISIBLE
                } else if (mContext is WholesalerCreateListCategoryActivity) {
                    totalFamiliesCount.visibility = View.VISIBLE
                } else {
                    totalFamiliesCount.visibility = View.GONE
                }
            }

            if (category.familyCount == 0) {
                name.text = category.name
            } else {
                name.text = "${category.familyCount} solicitudes"
            }

            totalFamiliesCount.text = category.totalFamilies.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_view_item_category,
                parent, false)
        view.setOnClickListener(onClickListener)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder?, position: Int) {
        if (categories != null) {
            val category = categories[position]
            if (category.id != -1) {
                holder?.bindDish(category)
            }
        }
    }

    override fun getItemCount() = categories?.size ?: 0
}