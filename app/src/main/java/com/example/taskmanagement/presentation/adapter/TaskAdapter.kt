package com.example.taskmanagement.presentation.adapter

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.TaskItemBinding
import com.example.taskmanagement.domain.model.Task
import com.example.taskmanagement.presentation.Constants

class TaskAdapter(
    private var tasks:List<Task>,
    private val onTaskClick:OnTaskClickListener,
    private val onTaskStatusButtonClick:OnTaskStatusButtonListener,
):RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(val taskBinding:TaskItemBinding):RecyclerView.ViewHolder(taskBinding.root){

        fun bind(task: Task){
            taskBinding.tvTaskTitle.text = task.title
            taskBinding.tvTaskDescription.text= task.description
            taskBinding.btnStatus.text = task.status
            taskBinding.btnStatus.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(itemView.context,task.statusColor.toInt()))

            taskBinding.root.setOnClickListener{
                 onTaskClick.onTaskClick(task)
            }
            taskBinding.btnStatus.setOnClickListener {
                onTaskStatusButtonClick.onTaskButtonClick(task)
            }
        }


    }

    fun setAdapter(newTasks:List<Task>){
        tasks= newTasks
        notifyDataSetChanged()
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
        fun onTaskClick(task: Task)
    }
    fun interface OnTaskStatusButtonListener{
        fun onTaskButtonClick(task: Task)
    }
}