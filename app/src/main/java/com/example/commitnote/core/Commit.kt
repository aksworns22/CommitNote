package com.example.commitnote.core

import com.example.commitnote.data.ObjectPath

data class Commit(
    val parent: Commit?,
    val blob: Blob,
    val hasher: Hasher,
    val timeProvider: TimeProvider
) {
    val hash = hasher.hash(toString())
    val path = ObjectPath.of(this, hasher)
    override fun toString(): String {
        if (parent == null) {
            return "blob ${blob.hash}\\ntime ${timeProvider.currentTimeMillis()}"
        }
        return "parent ${parent.hash}\\nblob ${blob.hash}\\ntime ${timeProvider.currentTimeMillis()}"
    }

    fun getCompressedData(compressor: Compressor) =
        compressor.compress(toString().toByteArray())
}
