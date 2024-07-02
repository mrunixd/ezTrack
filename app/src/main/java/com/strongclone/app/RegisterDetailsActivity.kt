package com.strongclone.app

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.node.getOrAddAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.strongclone.app.databinding.ActivityRegisterDetailsBinding
import java.util.Calendar
import java.util.Locale

class RegisterDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterDetailsBinding
    private lateinit var fireStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterDetailsBinding.inflate(layoutInflater)
        fireStore = FirebaseFirestore.getInstance()

        setContentView(binding.root)

        createSpinner()
        binding.dobInput.setOnClickListener {
            createCalendar()
        }

        binding.nextBtn.setOnClickListener {
            addUserToDB()
        }
    }

    private fun createSpinner() {
        val spinnerSex = binding.sexInput
        val sexOptions = arrayOf("Male", "Female", "Non-Binary")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, sexOptions)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinnerSex.adapter = adapter
    }

    private fun addUserToDB() {
        val firstName = binding.firstNameInput.text.toString().trim()
        val lastName = binding.lastNameInput.text.toString().trim()
        val height = binding.heightInput.text.toString().trim().toInt()
        val weight = binding.weightInput.text.toString().trim().toInt()
        val sex = binding.sexInput.selectedItem.toString()
        val dob = binding.dobInput.text.toString().trim()

        val user = User(firstName, lastName, height, weight, sex, dob)

        fireStore.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d(TAG, "addUserToDB: Success")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d(TAG, "addUserToDB: Failure")
            }
    }

    private fun createCalendar() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val dob = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            binding.dobInput.setText(dob)
        }, year, month, day)

        datePickerDialog.show()
    }

}