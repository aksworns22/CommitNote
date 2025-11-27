package com.example.commitnote.data

import com.example.commitnote.core.Note
import com.example.commitnote.core.Sha1Hasher
import java.io.File

object FileController {
    fun write(basePath: File,  note: Note): File {
        val notePath = NotePath.of(note, Sha1Hasher)
        val noteFullPath = File(basePath, "objects/${notePath.directory}")

        if (!noteFullPath.exists()) {
            noteFullPath.mkdirs()
        }
        val file = File(noteFullPath, notePath.filename)
        file.writeText(note.toString())
        return file
    }
}
