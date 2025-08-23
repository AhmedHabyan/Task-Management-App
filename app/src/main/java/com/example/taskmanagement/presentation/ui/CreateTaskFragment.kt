package com.example.taskmanagement.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.taskmanagement.databinding.FragmentCreateTaskBinding
import com.example.taskmanagement.domain.Task
import com.example.taskmanagement.presentation.Constants
import com.example.taskmanagement.presentation.viewModel.ActivityViewModel


class CreateTaskFragment : Fragment() {

    private var editFragmentBinding: FragmentCreateTaskBinding? =null
    private val viewModel by activityViewModels<ActivityViewModel>()
    private val args by navArgs<CreateTaskFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(editFragmentBinding ==null) {
            editFragmentBinding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        }
        return editFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //if new task --> blank title and description and Add Task button
        //if edit task -> write title and description and Edit task Button
        if(viewModel.isEditMode){
            updateUiEditTaskMode(args.task)
         }
        onEditableButtonClick()



    }


    private fun onEditableButtonClick() {
        editFragmentBinding?.editAddBtn?.setOnClickListener{
            if(isValidInput() && viewModel.isEditMode){
                //edit Task
            }else if(isValidInput()){
                //Add Task
            }
        }
    }

    private fun updateUiEditTaskMode(task: Task) {
        editFragmentBinding?.textInputEditTextTaskTitle?.setText(task.title)
        editFragmentBinding?.textInputEditTextTaskDescription?.setText(task.description)
        editFragmentBinding?.editAddBtn?.text = Constants.EDIT_TASK
    }

    private fun isValidInput():Boolean{

        editFragmentBinding?.apply {
            if(textInputEditTextTaskTitle.text?.isNotEmpty() == true &&
                textInputEditTextTaskDescription.text?.isNotEmpty() == true){
                return true
            }
            if (textInputEditTextTaskTitle.text?.isEmpty() == true){
                textInputEditTextTaskTitle.error = Constants.FIELD_EMPTY_ERROR

            }
            if(textInputEditTextTaskDescription.text?.isEmpty() == true) {
                textInputEditTextTaskDescription.error = Constants.FIELD_EMPTY_ERROR
            }

            }
        return false

    }

}