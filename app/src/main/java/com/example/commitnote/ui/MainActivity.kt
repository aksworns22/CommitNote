package com.example.commitnote.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.commitnote.R
import com.example.commitnote.core.Blob
import com.example.commitnote.core.Commit
import com.example.commitnote.core.Note
import com.example.commitnote.core.Sha1Hasher
import com.example.commitnote.core.TimeProvider
import com.example.commitnote.core.ZlibCompressor
import com.example.commitnote.data.FileController
import com.example.commitnote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var timeProvider: TimeProvider = object : TimeProvider {
        override fun currentTimeMillis() = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.commitButton.setOnClickListener {
            val noteTitle = binding.noteTitle.text.toString()
            val noteContent = binding.noteContent.text.toString()
            val note = Note(noteTitle, noteContent)
            val blob = Blob.of(note, ZlibCompressor)
            val commit = Commit(null, blob, Sha1Hasher, timeProvider)
            FileController.write(this.filesDir, blob)
            FileController.write(this.filesDir, commit, ZlibCompressor)
        }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
