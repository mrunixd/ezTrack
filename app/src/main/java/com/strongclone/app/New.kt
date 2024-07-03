package com.strongclone.app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.strongclone.app.databinding.ActivityNewWorkoutBinding
import com.strongclone.app.databinding.FragmentNewBinding

class New : Fragment(R.layout.fragment_new) {
    private lateinit var binding: FragmentNewBinding
    private lateinit var firestore: FirebaseFirestore
    private val workouts = mutableListOf<String>()
    private lateinit var exerciseListAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()

        exerciseListAdapter = WorkoutListAdapter(requireContext(), workouts)

        binding.newWorkoutBtn.setOnClickListener {
            val intent = Intent(activity, NewWorkout::class.java)
            startActivity(intent)
            activity?.finish()
        }

        getWorkouts()
        return binding.root
    }

    private fun getWorkouts() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            firestore.collection("users")
                .document(userId)
                .collection("workoutTemplates")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val workoutName = document.getString("name")
                        if (workoutName != null) {
                            workouts.add(workoutName)
                        }
                    }
                    // Notify the adapter that data set has changed
                    exerciseListAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Log.d(TAG, "retrieveWorkoutList: failed")
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set adapter to the ListView or RecyclerView
        binding.workoutsList.adapter = exerciseListAdapter
    }

    // Optional: Add onDestroyView() to clear references to views
    override fun onDestroyView() {
        super.onDestroyView()
        // Optionally, clear references to avoid memory leaks
        // e.g., binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = New()
    }
}
