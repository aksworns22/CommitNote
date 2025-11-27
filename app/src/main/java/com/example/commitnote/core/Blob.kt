package com.example.commitnote.core

import com.example.commitnote.data.NotePath
import java.io.ByteArrayOutputStream
import java.util.zip.Deflater
import java.util.zip.Inflater

interface Compressor {
    fun compress(inputData: ByteArray): ByteArray
    fun decompress(inputData: ByteArray): String
}

object ZlibCompressor : Compressor {
    override fun compress(inputData: ByteArray): ByteArray {
        val deflater = Deflater()
        deflater.setInput(inputData)
        deflater.finish()

        val outputStream = ByteArrayOutputStream(inputData.size)
        val buffer = ByteArray(1024)

        while (!deflater.finished()) {
            val count = deflater.deflate(buffer)
            outputStream.write(buffer, 0, count)
        }

        outputStream.close()
        deflater.end()
        return outputStream.toByteArray()
    }

    override fun decompress(inputData: ByteArray): String {
        val inflater = Inflater()
        inflater.setInput(inputData)

        val outputStream = ByteArrayOutputStream(inputData.size * 2) // Estimate initial size
        val buffer = ByteArray(1024)

        while (!inflater.finished()) {
            val count = inflater.inflate(buffer)
            outputStream.write(buffer, 0, count)
        }

        outputStream.close()
        inflater.end()
        return outputStream.toString(Charsets.UTF_8)
    }
}

data class Blob(val notePath: NotePath, val content: ByteArray) {
    companion object {
        fun of(note: Note, compressor: Compressor): Blob {
            return Blob(
                NotePath.of(note, Sha1Hasher),
                compressor.compress(note.toByteArray())
            )
        }
    }
}
