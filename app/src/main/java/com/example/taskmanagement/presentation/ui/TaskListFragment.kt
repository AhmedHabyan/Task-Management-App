package com.example.taskmanagement.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.FragmentTaskListBinding
import com.example.taskmanagement.domain.Task
import com.example.taskmanagement.presentation.adapter.TaskAdapter
import com.example.taskmanagement.presentation.utils.navigateSafeDirections
import com.example.taskmanagement.presentation.viewModel.ActivityViewModel


class TaskListFragment : Fragment(),
    TaskAdapter.OnTaskClickListener,
TaskAdapter.OnTaskStatusButtonListener{


    private var viewBinding:FragmentTaskListBinding?=null


    private val viewModel by activityViewModels<ActivityViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(viewBinding ==null) {
            viewBinding = FragmentTaskListBinding.inflate(inflater, container, false)
        }
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTaskRecyclerView()
        onButtonsClick()
    }

    private fun onButtonsClick() {
        onFloatingActionButtonClicked()
        onAddTaskClicked()
    }

    private fun onAddTaskClicked() {
        //save it in room database
        //return back to the list screen and see the task
    }

    private fun onFloatingActionButtonClicked() {
        viewBinding?.floatingActionBtn?.setOnClickListener{
            viewModel.isEditMode= false
            findNavController().navigate(R.id.action_taskListFragment_to_createTaskFragment)
        }
    }

    private fun initTaskRecyclerView() {
        val taskAdapter = TaskAdapter(
            listOf(
                Task(
                    title = "Task title",
                    description = "Task Description",
                    status = "Pending"
                ),
                Task(
                    title = "Task title",
                    description = "Task Description",
                    status = "Pending"
                ),
                Task(
                    title = "Task title",
                    description = "Task Description",
                    status = "Pending"
                ),
                Task(
                    title = "Task title",
                    description = "Task Description",
                    status = "Pending"
                ),
            ),
            onTaskClick = this,
            onTaskStatusButtonClick = this
        )
        viewBinding?.recyclerViewTasks?.adapter = taskAdapter
        viewBinding?.recyclerViewTasks?.layoutManager= LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
    }

    override fun onTaskClick(task: Task) {
        viewModel.isEditMode = true
        val action= TaskListFragmentDirections.actionTaskListFragmentToCreateTaskFragment(task)
        findNavController().navigateSafeDirections(R.id.action_taskListFragment_to_createTaskFragment,action)

    }

    override fun onTaskButtonClick(task: Task, toggleStatus: Boolean) {
        if(toggleStatus == false) {
            /*viewModel.editTask(
                task.copy(
                    status = Constants.PENDING
                )
            )*/
        }
        else{
                /*viewModel.editTask(task.copy(
                    status = Constants.DONE
                )*/
            }

    }
}