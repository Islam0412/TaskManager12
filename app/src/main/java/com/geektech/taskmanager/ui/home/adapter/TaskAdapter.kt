package com.geektech.taskmanager.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geektech.taskmanager.R
import com.geektech.taskmanager.databinding.ItemTaskBinding
import com.geektech.taskmanager.model.Task

class TaskAdapter(private val onClick: (pos: Int) -> Unit) : Adapter<TaskAdapter.TaskViewHolder>() {
    private val data = arrayListOf<Task>()
    private var color = true
    fun addTask(tasks: List<Task>) {
        data.clear()
        data.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task) {
            with(binding) {
                tittle.text = task.title
                description.text = task.description
                itemView.setOnLongClickListener {
                    onClick(adapterPosition)
                    return@setOnLongClickListener false
                }
            }
            if (color) {
                binding.container.setBackgroundColor(Color.BLACK)
                binding.description.setTextColor(Color.WHITE)
                binding.tittle.setTextColor(Color.WHITE)
                color = false
            } else {
                binding.container.setBackgroundColor(Color.WHITE)
                binding.description.setTextColor(Color.BLACK)
                binding.tittle.setTextColor(Color.BLACK)
                color = true
            }

        }
    }

}
