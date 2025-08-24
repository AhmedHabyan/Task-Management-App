package com.example.taskmanagement.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
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
import com.example.taskmanagement.presentation.utils.showCurrentNotification
import com.example.taskmanagement.presentation.viewModel.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
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
                            if(it.tasks.isNotEmpty()) {
                                viewBinding?.tvEmpty?.clearVisiblity()
                                viewBinding?.recyclerViewTasks?.setVisiblity()
                                taskAdapter?.setAdapter(it.tasks)
                            }
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
            mutableListOf(),
            onTaskClick = this,
            onTaskStatusButtonClick = this
        )
        viewBinding?.recyclerViewTasks?.adapter = taskAdapter
        viewBinding?.recyclerViewTasks?.layoutManager= LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)

        val helper:ItemTouchHelper= ItemTouchHelper(callBack)
        helper.attachToRecyclerView(viewBinding?.recyclerViewTasks)
    }

    override fun onTaskClick(task: Task) {
        viewModel.isEditMode = true
        val action= TaskListFragmentDirections.actionTaskListFragmentToCreateTaskFragment(task)
        findNavController().navigateSafeDirections(R.id.action_taskListFragment_to_createTaskFragment,action)

    }


    override fun onTaskStatusButtonClick(task: Task) {
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
                 }
            }

    }

    val callBack: ItemTouchHelper.SimpleCallback= object: ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.RIGHT)
    {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val taskRemoved= taskAdapter?.deleteItem(viewHolder.adapterPosition)
            taskRemoved?.let {  viewModel.deleteTask(it,
                onComplete = {
                    showCurrentNotification(
                        context = requireContext(),
                        title = "task deleted",
                        description = "task deleted succesfully"
                    )
                    if(taskAdapter?.getTasks()?.isEmpty() == true){
                        viewBinding?.tvEmpty?.setVisiblity()
                        viewBinding?.recyclerViewTasks?.clearVisiblity()
                    }
                })}

        }



        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )


            RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                .addBackgroundColor(
                    ContextCompat.getColor(requireContext(),
                    R.color.red
                ))
                .addActionIcon(R.drawable.ic_delete)
                .setSwipeRightLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                .setSwipeRightLabelTypeface(Typeface.DEFAULT_BOLD)
                .addSwipeRightLabel(Constants.DELETE)
                .create()
                .decorate()


        }
    }
}