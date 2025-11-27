package com.example.commitnote.data

import com.example.commitnote.core.Blob
import com.example.commitnote.core.Commit
import com.example.commitnote.core.Compressor
import com.example.commitnote.core.Sha1Hasher
import java.io.File

object FileController {
    fun write(basePath: File,  blob: Blob): File {
        val notePath = blob.objectPath
        val noteFullPath = File(basePath, "objects/${notePath.directory}")
        if (!noteFullPath.exists()) {
            noteFullPath.mkdirs()
        }
        val file = File(noteFullPath, notePath.filename)
        file.writeBytes(blob.content)
        return file
    }

    fun write(basePath: File, commit: Commit, compressor: Compressor): File {
        val commitPath = commit.path
        val commitContent = commit.getCompressedData(compressor)
        val commitFullPath = File(basePath, "objects/${commitPath.directory}")
        if (!commitFullPath.exists()) {
            commitFullPath.mkdirs()
        }
        val file = File(commitFullPath, commitPath.filename)
        file.writeBytes(commitContent)
        return file
    }
}
