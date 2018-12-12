package com.example.sergey.departmenttest.feature.treeView

import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.domain.model.DepartmentElement
import com.example.sergey.departmenttest.domain.model.EmployeeElement
import com.example.sergey.departmenttest.domain.model.TreeElement
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_department.*

typealias ItemClick = (treeElement: TreeElement) -> Unit

class ItemAdapter : RecyclerView.Adapter<ItemHolder>() {
    var itemClick: ItemClick? = null
    var data: List<TreeElement> = emptyList()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(ItemDiffUtilCallback(data, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_department, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemClick = itemClick
        holder.data = data[position]
    }
}

class ItemHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    var itemClick: ItemClick? = null

    var data: TreeElement? = null
        set(value) {
            configIcon(value)

            nameView.text = getName(value)
            itemView.setOnClickListener {
                value.takeIf { element -> element != null }
                        ?.let { element -> itemClick?.invoke(element) }
            }

            configLeftMargin(value)
        }

    private fun configIcon(value: TreeElement?) {
        getDrawable(value).takeIf { it != null }
                ?.let {
                    iconView.setImageDrawable(it)
                    iconView.visibility = View.VISIBLE
                } ?: run { iconView.visibility = View.INVISIBLE }
    }

    private fun getDrawable(value: TreeElement?): Drawable? {
        if (value == null) return null
        val drawableId = when (value) {
            is DepartmentElement ->
                if (value.isOpened) R.drawable.ic_expand_more_black_24dp
                else R.drawable.ic_chevron_right_black_24dp
            is EmployeeElement -> R.drawable.ic_person_black_24dp
        }
        return ContextCompat.getDrawable(itemView.context, drawableId)
    }

    private fun getName(value: TreeElement?): String {
        return when (value) {
            is DepartmentElement -> value.name
            is EmployeeElement -> value.employee.name
            else -> ""
        }
    }

    private fun configLeftMargin(value: TreeElement?) {
        val depth = value?.depth ?: 0

        with(ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)) {
            val dm = itemView.context.resources.displayMetrics
            val leftMargin = TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f * depth, dm).toInt()
            setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
            itemView.layoutParams = this
        }
    }
}

class ItemDiffUtilCallback(
        private val oldData: List<TreeElement>,
        private val newData: List<TreeElement>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int) =
            oldData[oldPosition].id == newData[newPosition].id

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int) = false

}