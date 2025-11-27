package com.example.commitnote

import com.example.commitnote.core.Note
import com.example.commitnote.core.Sha1Hasher
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NoteTest {
    @Test
    fun `제목과 내용이 포함된 노트를 구분자로 연결된 단일 문자열로 만든다`() {
        assertThat(Note(title = "hello", content = "world").toString())
            .isEqualTo("hello\\0world")
    }

    @Test
    fun `노트에 대해 SHA-1 해싱을 적용한다`() {
        assertThat(Sha1Hasher.hash(Note(title="hello", content = "world").toString()))
            .isEqualTo("63b77d3e36388e6c463f85726a4bb4585479d96e")
    }
}
