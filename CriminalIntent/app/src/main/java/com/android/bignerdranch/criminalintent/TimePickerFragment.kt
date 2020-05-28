package com.android.bignerdranch.criminalintent

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_TIME = "DateTime"

class TimePickerFragment : DialogFragment() {
    interface Callbacks {
        fun onTimePicked(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_TIME) as Date
        val calendar = GregorianCalendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val onTimePickedListener = TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
            val resultTime: Date = GregorianCalendar(year, month, day, hours, minutes).time

            targetFragment?.let { fragment ->
                (fragment as Callbacks).onTimePicked(resultTime)
            }
        }

        return TimePickerDialog(
            requireContext(),
            onTimePickedListener,
            hour,
            minute,
            true
        )
    }

    companion object {
        fun newInstance(date: Date): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, date)
            }

            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}