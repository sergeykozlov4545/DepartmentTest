package com.example.sergey.departmenttest.feature.main.treeView

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.domain.model.Department
import com.example.sergey.departmenttest.domain.model.Employee
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_department.*

typealias EmployeeClick = (employee: Employee) -> Unit
typealias DepartmentClick = (department: Department) -> Unit

interface ItemCallback {
    fun getTitle(): String
    fun getSubItems(): List<Item> = emptyList()
}

sealed class Item(depth: Int = 0) : ItemCallback

class DepartmentItem(val department: Department, var opened: Boolean = false) : Item() {
    override fun getTitle() = department.name

    override fun getSubItems(): List<Item> {
        if (department.subDepartments.isEmpty()
                && department.employees.isEmpty()) {
            return emptyList()
        }
        if (department.subDepartments.isNotEmpty()) {
            return department.subDepartments.map { DepartmentItem(it) }
        }
        return department.employees.map { EmployeeItem(it) }
    }
}

class EmployeeItem(val employee: Employee) : Item() {
    override fun getTitle() = employee.name
}

class ItemAdapter : RecyclerView.Adapter<ItemHolder>() {

    var departmentClick: DepartmentClick? = null
    var employeeClick: EmployeeClick? = null
    var data: List<Item> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_department, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.employeeClick = employeeClick
        holder.departmentClick = departmentClick
        holder.data = data[position]
    }
}

class ItemHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val innerDepartmentClick: DepartmentClick = {
        departmentClick?.invoke(it)
    }

    var departmentClick: DepartmentClick? = null
    var employeeClick: EmployeeClick? = null

    var data: Item? = null
        set(value) {
            configIcon(value)

            itemView.setOnClickListener {
                when (value) {
                    is DepartmentItem -> innerDepartmentClick(value.department)
                    is EmployeeItem -> employeeClick?.invoke(value.employee)
                }
            }
        }

    private fun configIcon(value: Item?) {
        getDrawable(value).takeIf { it != null }
                ?.let {
                    iconView.setImageDrawable(it)
                    iconView.visibility = View.VISIBLE
                } ?: run { iconView.visibility = View.INVISIBLE }
    }

    private fun getDrawable(value: Item?): Drawable? {
        if (value == null) return null
        val drawableId = when (value) {
            is DepartmentItem ->
                if (value.opened) R.drawable.ic_expand_more_black_24dp
                else R.drawable.ic_chevron_right_black_24dp
            is EmployeeItem -> R.drawable.ic_person_black_24dp
        }
        return ContextCompat.getDrawable(itemView.context, drawableId)
    }
}
