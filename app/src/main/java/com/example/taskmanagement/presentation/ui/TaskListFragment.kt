package com.example.taskmanagement.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.FragmentTaskListBinding
import com.example.taskmanagement.domain.model.Task
import com.example.taskmanagement.presentation.Constants
import com.example.taskmanagement.presentation.adapter.TaskAdapter
import com.example.taskmanagement.presentation.utils.clearVisiblity
import com.example.taskmanagement.presentation.utils.navigateSafeDirections
import com.example.taskmanagement.presentation.utils.setVisiblity
import com.example.taskmanagement.presentation.viewModel.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskListFragment : Fragment(),
    TaskAdapter.OnTaskClickListener,
TaskAdapter.OnTaskStatusButtonListener{


    private var viewBinding:FragmentTaskListBinding?=null
    private var taskAdapter:TaskAdapter?=null

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
        viewModel.getAllTasks()
        observeData()
        initTaskRecyclerView()
        onButtonsClick()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect{
                    when(it){
                        is UiState.Error -> {
                            viewBinding?.progressBar?.clearVisiblity()
                        }
                        is UiState.Ideal ->{}
                        is UiState.Loading -> {
                            viewBinding?.progressBar?.setVisiblity()
                        }
                        is UiState.Success -> {
                            viewBinding?.progressBar?.clearVisiblity()
                            taskAdapter?.setAdapter(it.tasks)
                        }
                    }
                }
            }
        }

    }

    private fun onButtonsClick() {
        onFloatingActionButtonClicked()
    }



    private fun onFloatingActionButtonClicked() {
        viewBinding?.floatingActionBtn?.setOnClickListener{
            viewModel.isEditMode= false
            findNavController().navigate(R.id.action_taskListFragment_to_createTaskFragment)
        }
    }

    private fun initTaskRecyclerView() {

       taskAdapter = TaskAdapter(
            emptyList(),
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


    override fun onTaskButtonClick(task: Task) {
        if(task.status == Constants.DONE) {
            viewModel.updateTask(
                task.copy(
                    status = Constants.PENDING,
                    statusColor = R.color.blue.toString()
                )
            ){
                viewModel.getAllTasks()
            }
        }
        else{
                viewModel.updateTask(task.copy(
                    status = Constants.DONE,
                    statusColor = R.color.green.toString()
                )
                ){
                    viewModel.getAllTasks()
                    Log.e("button","second")

                }
            }

    }
}