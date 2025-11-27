package com.example.commitnote

import com.example.commitnote.core.Note
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NoteTest {
    @Test
    fun `제목과 내용이 포함된 노트를 구분자로 연결된 단일 문자열로 만든다`() {
        assertThat(Note(title = "hello", content = "world").toString())
            .isEqualTo("hello\\0world")
    }
}
