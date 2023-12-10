package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private val addCourseViewModel by viewModels<AddCourseViewModel> {
        AddCourseViewModelFactory.createFactory(this)
    }
    private lateinit var selectedTime: View

    private var _binding: ActivityAddCourseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCourseViewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == true)
                finish()
            else {
                Toast.makeText(this, R.string.input_empty_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = binding.edCourseName.text.toString()
                val day = binding.spinnerDay.selectedItemPosition
                val startTime = binding.edStartTime.text.toString()
                val endTime = binding.edEndTime.text.toString()
                val lecturer = binding.edLecturer.text.toString()
                val note = binding.edNote.text.toString()

                addCourseViewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                Toast.makeText(this, "Course Added", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showStartTimePicker(view: View) {
        TimePickerFragment().show(
            supportFragmentManager, "startTime"
        )
        selectedTime = view
    }

    fun showEndTimePicker(view: View) {
        TimePickerFragment().show(
            supportFragmentManager, "endTime"
        )
        selectedTime = view
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val selectedImageButton = when (selectedTime.id) {
            R.id.ib_start_time -> binding.edStartTime
            R.id.ib_end_time -> binding.edEndTime
            else -> null
        }

        selectedImageButton?.text = timeFormat.format(calendar.time)
    }
}