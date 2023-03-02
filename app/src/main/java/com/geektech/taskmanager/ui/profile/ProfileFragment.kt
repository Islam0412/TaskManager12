package com.geektech.taskmanager.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.geektech.taskmanager.R
import com.geektech.taskmanager.data.local.Pref
import com.geektech.taskmanager.databinding.FragmentProfileBinding
import com.geektech.taskmanager.utils.loadImage
import java.net.URI

class ProfileFragment : Fragment() {

private lateinit var binding: FragmentProfileBinding
private lateinit var pref: Pref

private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    if (it.resultCode == Activity.RESULT_OK && it.data != null){
        val uri: Uri? = it.data?.data
        pref.setImage(uri.toString())
        binding.imvProfile.loadImage(uri.toString())
    }
}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        saveName()
    }

    private fun saveName() {
        binding.etText.setText(pref.getName())

        binding.etText.addTextChangedListener {
            pref.saveName(binding.etText.text.toString())
        }
        binding.imvProfile.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcher.launch(intent)
        }
    }
}