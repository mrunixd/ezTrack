package com.strongclone.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.strongclone.app.databinding.ListItemExerciseBinding

class SelectedExerciseAdapter(context: Context, private val selectedExercises: MutableList<String>) :
    ArrayAdapter<String>(context, 0, selectedExercises) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = convertView?.tag as? ListItemExerciseBinding
            ?: ListItemExerciseBinding.inflate(LayoutInflater.from(context), parent, false)

        binding.selectedExerciseNameTextView.text = selectedExercises[position]

        binding.removeExerciseBtn.setOnClickListener {
            selectedExercises.removeAt(position)
            notifyDataSetChanged()
        }

        return binding.root.apply { tag = binding }
    }
}