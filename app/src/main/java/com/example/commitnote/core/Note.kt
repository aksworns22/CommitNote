package com.example.commitnote.core

data class Note(val title: String, val content: String) {
    override fun toString(): String = "$title$CONTENT_DELIMITER$content"
    companion object {
        const val CONTENT_DELIMITER = "\\0"
    }
}
