package com.example.taskmanagement.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.TaskItemBinding
import com.example.taskmanagement.domain.Task
import com.example.taskmanagement.presentation.Constants

class TaskAdapter(
    private val tasks:List<Task>,
    private val onTaskClick:OnTaskClickListener,
    private val onTaskStatusButtonClick:OnTaskStatusButtonListener,
):RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var toggleStatus:Boolean= false
    inner class TaskViewHolder(val taskBinding:TaskItemBinding):RecyclerView.ViewHolder(taskBinding.root){

        fun bind(task:Task){
            taskBinding.tvTaskTitle.text = task.title
            taskBinding.tvTaskDescription.text= task.description
            taskBinding.btnStatus.text = task.status
            taskBinding.root.setOnClickListener{
                 onTaskClick.onTaskClick(task)
            }
            taskBinding.btnStatus.setOnClickListener {
                changeButtonStatus()
                onTaskStatusButtonClick.onTaskButtonClick(task,toggleStatus)
            }
        }

        private fun changeButtonStatus() {
            if(toggleStatus){
                taskBinding.btnStatus.text= Constants.PENDING
                taskBinding.btnStatus.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.blue))
                toggleStatus=!toggleStatus
            }else{
                taskBinding.btnStatus.text= Constants.DONE
                taskBinding.btnStatus.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.green))
                toggleStatus=!toggleStatus
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount()= tasks.size

    fun interface OnTaskClickListener{
        fun onTaskClick(task:Task)
    }
    fun interface OnTaskStatusButtonListener{
        fun onTaskButtonClick(task:Task,toggleStatus:Boolean)
    }
}