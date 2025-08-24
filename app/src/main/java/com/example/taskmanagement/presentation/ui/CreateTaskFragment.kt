package com.example.taskmanagement.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.renderscript.RenderScript.Priority
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.corefeatures.notification.NotificationContent
import com.example.corefeatures.notification.NotificationHelper
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.FragmentCreateTaskBinding
import com.example.taskmanagement.domain.model.Task
import com.example.taskmanagement.presentation.Constants
import com.example.taskmanagement.presentation.utils.showCurrentNotification
import com.example.taskmanagement.presentation.viewModel.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.security.Permission
import java.security.Permissions

@AndroidEntryPoint
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
        if(viewModel.isEditMode){
            updateUiEditTaskMode(args.task)
         }
        onEditableButtonClick()



    }


    private fun onEditableButtonClick() {
        editFragmentBinding?.editAddBtn?.setOnClickListener{
            if(isValidInput() && viewModel.isEditMode){
                Log.e("edit","${args.task.id}")
                //edit Task
                viewModel.updateTask(
                    args.task.copy(
                        title = editFragmentBinding?.textInputEditTextTaskTitle?.text.toString(),
                        description = editFragmentBinding?.textInputEditTextTaskDescription?.text.toString()
                    )
                ){
                    showCurrentNotification(
                        context=requireContext(),
                        title = "task update",
                        description = "task updated succesfully"
                    )
                    findNavController().popBackStack()
                }

            }
            else if(isValidInput()){
                //Add Task
                viewModel.insertTask(
                    Task(
                        id=0,
                        title = editFragmentBinding?.textInputEditTextTaskTitle?.text.toString(),
                        description = editFragmentBinding?.textInputEditTextTaskDescription?.text.toString(),
                        status = Constants.PENDING,
                        statusColor = R.color.blue.toString()
                    ),
                    onComplete = {
                        showCurrentNotification(
                            context= requireContext(),
                            title = "task added",
                            description = "task added succesfully"
                        )


                        findNavController().popBackStack()
                    }
                )
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