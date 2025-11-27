package com.example.commitnote

import com.example.commitnote.core.Sha1Hasher
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class HashTest {
    @Test
    fun `문자열에 대해 SHA-1 해시값이 올바르게 생성되어야 한다`() {
        assertThat(Sha1Hasher.hash("hello"))
            .isEqualTo("aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d")
    }
}
