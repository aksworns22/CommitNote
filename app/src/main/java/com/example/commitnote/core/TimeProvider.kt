package com.example.commitnote.core

interface TimeProvider {
    fun currentTimeMillis(): Long
}