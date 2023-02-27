package com.geektech.taskmanager.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geektech.taskmanager.App
import com.geektech.taskmanager.R
import com.geektech.taskmanager.databinding.FragmentHomeBinding
import com.geektech.taskmanager.model.Task
import com.geektech.taskmanager.ui.home.adapter.TaskAdapter
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TaskAdapter
    private lateinit var task  : List<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(this::onLongClick)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
        setData()
        binding.recyclerView.adapter = adapter

    }
    private fun onLongClick(position: Int) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Вы уверенны что хотите удалить?")
            builder.setMessage("Если вы удалите данную строку его нельзя будет восстановить!")
            builder.setPositiveButton("Да") { dialogInterface: DialogInterface, i: Int ->
                App.db.taskDao().delete(task[position])
                setData()
            }
            builder.setNegativeButton("Нет") { dialogInterface: DialogInterface, i: Int ->
            }
            builder.show()
    }

    private fun setData() {
        task = App.db.taskDao().getAll()
        adapter.addTask(task)
    }

    companion object {
        const val RESULT_KEY = "request key"
        const val TASK_KEY = "task key"
    }
}