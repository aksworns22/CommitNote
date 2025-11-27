package com.example.commitnote.data

import com.example.commitnote.core.Hasher
import com.example.commitnote.core.Note

class NotePath(val directory: String, val filename: String) {
    override fun toString(): String = "$directory/$filename"
    companion object {
        fun of(note: Note, hasher: Hasher): NotePath {
            val fullPath = hasher.hash(note.toString())
            return NotePath(getDirectoryPath(fullPath), getFilePath(fullPath))
        }

        private fun getDirectoryPath(fullPath: String) = fullPath.take(2)
        private fun getFilePath(fullPath: String) = fullPath.substring(2)
    }
}
