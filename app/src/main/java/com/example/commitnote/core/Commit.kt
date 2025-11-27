package com.example.commitnote.core

import com.example.commitnote.data.ObjectPath
import kotlin.collections.toString

data class Commit(val parent: Commit?, val blob: Blob, val time: Long, val hasher: Hasher) {
    val hash = hasher.hash(toString())
    val path = ObjectPath.of(this, hasher)
    override fun toString(): String {
        if (parent == null) {
            return "blob ${blob.hash}\\ntime $time"
        }
        return "parent ${parent.hash}\\nblob ${blob.hash}\\ntime $time"
    }

    fun getCompressedData(compressor: Compressor) =
        compressor.compress(toString().toByteArray())

    companion object {
        fun of(parent: Commit?, blob: Blob, hasher: Hasher): Commit =
            Commit(parent, blob, System.currentTimeMillis(), hasher)
    }
}
