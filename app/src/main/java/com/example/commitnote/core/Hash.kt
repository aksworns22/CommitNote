package com.example.commitnote.core

import java.security.MessageDigest

interface Hasher {
    fun hash(rawString: String): String
}

object Sha1Hasher : Hasher {
    override fun hash(rawString: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val bytes = digest.digest(rawString.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
