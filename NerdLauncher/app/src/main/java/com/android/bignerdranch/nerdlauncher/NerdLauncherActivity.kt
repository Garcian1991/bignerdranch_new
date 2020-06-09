package com.android.bignerdranch.nerdlauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "NerdLauncherActivity"

class NerdLauncherActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nerd_launcher)

        recyclerView = findViewById(R.id.app_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        setupAdapter()
    }

    private fun setupAdapter() {
        val startupIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activities = packageManager.queryIntentActivities(startupIntent, 0)
        activities.sortWith(Comparator { a, b ->
            String.CASE_INSENSITIVE_ORDER.compare(
                a.loadLabel(packageManager).toString(),
                b.loadLabel(packageManager).toString()
            )
        })

        val adapter = ActivityAdapter(activities)
        recyclerView.adapter = adapter

        Log.i(TAG, "Found ${activities.size} activities")
    }

    private class ActivityHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var nameTextView: TextView
        private lateinit var imageView: ImageView
        private lateinit var resolveInfo: ResolveInfo

        fun bindActivity(resolveInfo: ResolveInfo) {
            nameTextView = itemView.findViewById(R.id.app_name)
            imageView = itemView.findViewById(R.id.app_icon)
            this.resolveInfo = resolveInfo
            val packageManager = itemView.context.packageManager
            val appName = resolveInfo.loadLabel(packageManager).toString()
            val appImage = resolveInfo.loadIcon(packageManager)
            nameTextView.apply {
                text = appName
                setOnClickListener(this@ActivityHolder)
            }
            imageView.apply {
                setImageDrawable(appImage)
                setOnClickListener(this@ActivityHolder)
            }
        }

        override fun onClick(view: View) {
            val activityInfo = resolveInfo.activityInfo

            val intent = Intent(Intent.ACTION_MAIN).apply {
                setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            val context = view.context
            context.startActivity(intent)
        }
    }

    private class ActivityAdapter(val activities: List<ResolveInfo>) :
        RecyclerView.Adapter<ActivityHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(
                R.layout.nerd_launcher_item,
                parent,
                false
            )
            return ActivityHolder(view)
        }

        override fun getItemCount() = activities.size

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            val resolveInfo = activities[position]
            holder.bindActivity(resolveInfo)
        }

    }
}