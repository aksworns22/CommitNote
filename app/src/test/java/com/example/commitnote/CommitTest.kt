package com.example.commitnote

import com.example.commitnote.core.Blob
import com.example.commitnote.core.Commit
import com.example.commitnote.core.Note
import com.example.commitnote.core.Sha1Hasher
import com.example.commitnote.core.ZlibCompressor
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.Test

class CommitTest {
    @Test
    fun `blob 역할 파일의 해시값과 수정 시간, 부모 커밋 객체를 가지는 데이터를 만든다`() {
        val blob = Blob.of(Note("hello", "world"), ZlibCompressor)
        assertThatCode {
            Commit.of(null, blob, Sha1Hasher)
        }.doesNotThrowAnyException()
    }
}
