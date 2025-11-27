package com.example.commitnote.core

data class Note(val title: String, val content: String) {
    override fun toString(): String = "$title$CONTENT_DELIMITER$content"
    fun toByteArray(): ByteArray = toString().toByteArray(Charsets.UTF_8)
    companion object {
        const val CONTENT_DELIMITER = "\\0"
    }
}
