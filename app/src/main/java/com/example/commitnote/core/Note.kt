package com.example.commitnote.core

data class Note(val title: String, val content: String) {
    override fun toString(): String = "$title\\0$content"
}
