package com.android.bignerdranch.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bignerdranch.beatbox.databinding.ActivityMainBinding
import com.android.bignerdranch.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var beatBoxViewModel: BeatBoxViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = BeatBoxViewModelFactory(assets, application)
        beatBoxViewModel = ViewModelProvider(this, factory).get(BeatBoxViewModel::class.java)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBoxViewModel.beatBox.sounds)
        }
        binding.lifecycleOwner = this
        binding.beatBoxViewModel = beatBoxViewModel
        binding.seekBar3.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
        val rate: Float = seekBar?.let {
            it.progress.toFloat() / 100.0f
        } ?: 0.0f
        beatBoxViewModel.onRateChange(rate)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    private inner class SoundHolder(
        private val binding: ListItemSoundBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = SoundViewModel(beatBoxViewModel.beatBox)
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>)
        : RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return SoundHolder(binding)
        }

        override fun getItemCount() = sounds.size

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }
    }
}