package com.example.commitnote.data

import com.example.commitnote.core.Hasher
import com.example.commitnote.core.Commit
import com.example.commitnote.core.Note

class ObjectPath(val directory: String, val filename: String) {
    val fullHash = directory + filename
    override fun toString(): String = "$directory/$filename"
    companion object {
        fun of(note: Note, hasher: Hasher): ObjectPath {
            val fullPath = hasher.hash(note.toString())
            return ObjectPath(getDirectoryPath(fullPath), getFilePath(fullPath))
        }

        fun of(commit: Commit, hasher: Hasher): ObjectPath {
            val fullPath = hasher.hash(commit.toString())
            return ObjectPath(getDirectoryPath(fullPath), getFilePath(fullPath))
        }

        private fun getDirectoryPath(fullPath: String) = fullPath.take(2)
        private fun getFilePath(fullPath: String) = fullPath.substring(2)
    }
}
