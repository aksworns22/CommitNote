package com.example.commitnote.core

data class Commit(val parent: Commit?, val blob: Blob, val time: Long, val hasher: Hasher) {
    val hash = hasher.hash(toString())
    override fun toString(): String {
        if (parent == null) {
            return "blob ${blob.hash}\ntime $time"
        }
        return "parent ${parent.hash}\nblob ${blob.hash}\ntime $time"
    }

    companion object {
        fun of(parent: Commit?, blob: Blob, hasher: Hasher): Commit =
            Commit(parent, blob, System.currentTimeMillis(), hasher)
    }
}
