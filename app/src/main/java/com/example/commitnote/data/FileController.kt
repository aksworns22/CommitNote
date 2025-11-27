package com.example.commitnote.data

import com.example.commitnote.core.Blob
import java.io.File

object FileController {
    fun write(basePath: File,  blob: Blob): File {
        val notePath = blob.notePath
        val noteFullPath = File(basePath, "objects/${notePath.directory}")

        if (!noteFullPath.exists()) {
            noteFullPath.mkdirs()
        }
        val file = File(noteFullPath, notePath.filename)
        file.writeBytes(blob.content)
        return file
    }
}
