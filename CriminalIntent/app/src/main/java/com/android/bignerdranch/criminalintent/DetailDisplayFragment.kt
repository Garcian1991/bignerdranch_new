package com.android.bignerdranch.criminalintent

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

private const val ARG_PHOTO_PATH = "photo"

class DetailDisplayFragment : DialogFragment() {

    private lateinit var crimePhotoDetail: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //crimePhotoDetail = view!!.findViewById(R.id.crime_photo_detail)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_image, null)
        crimePhotoDetail = view!!.findViewById(R.id.crime_photo_detail)
        arguments?.getString(ARG_PHOTO_PATH).also { path ->
            val image = getScaledBitmap(path!!, requireActivity())
            crimePhotoDetail.setImageBitmap(image)
        }

        val builder = AlertDialog.Builder(activity)


        builder.setView(view).setPositiveButton("OK", null)

        return builder.create()
    }

    companion object {
        fun newInstance(path: String): DetailDisplayFragment {
            val args = Bundle().apply {
                putString(ARG_PHOTO_PATH, path)
            }
            return DetailDisplayFragment().apply {
                arguments = args
            }
        }
    }
}