package com.geektech.taskmanager.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
        onSwipe()

    }
    private fun onLongClick(position: Int) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Вы уверенны что хотите удалить?")
            builder.setMessage("Если вы удалите данную строку его нельзя будет восстановить!")
            builder.setPositiveButton("Да") { _: DialogInterface, _: Int ->
                App.db.taskDao().delete(task[position])
            }
            builder.setNegativeButton("Нет") { _: DialogInterface, _: Int ->
            }
            builder.show()
    }

    private fun setData() {
        task = App.db.taskDao().getAll()
        adapter.addTask(task)
    }
    private fun onSwipe() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Вы уверенны что хотите удалить?")
                builder.setMessage("Если вы удалите данную строку его нельзя будет восстановить!")
                builder.setPositiveButton("Да") { dialogInterface: DialogInterface, i: Int ->
                    App.db.taskDao().delete(task[pos])
                    setData()
                }
                builder.setNegativeButton("Нет") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.cancel()
                }
                builder.show()

            }


        }).attachToRecyclerView(binding.recyclerView)
    }
    companion object {
        const val RESULT_KEY = "request key"
        const val TASK_KEY = "task key"
    }
}