package com.example.commitnote

import android.content.Context
import com.example.commitnote.core.Blob
import com.example.commitnote.core.Commit
import com.example.commitnote.core.Note
import com.example.commitnote.core.Sha1Hasher
import com.example.commitnote.core.ZlibCompressor
import com.example.commitnote.data.FileController
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.nio.file.Paths

class FileControllerTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    @Test
    fun verifyNoteFileIsCreated() {
        val mockContext = Mockito.mock(Context::class.java)
        `when`(mockContext.filesDir).thenReturn(tempFolder.root)
        val note = Note(title = "hello", content = "world")
        val blob = Blob.of(note, ZlibCompressor)

        val file = FileController.write(mockContext.filesDir, blob)
        assertThat(file.path).contains(
            Paths.get("63", "b77d3e36388e6c463f85726a4bb4585479d96e").toString()
        )
        assertThat(ZlibCompressor.decompress(file.readBytes())).isEqualTo("hello\\0world")
    }

    @Test
    fun `커밋 객체가 올바른 위치에 저장된다`() {
        val mockContext = Mockito.mock(Context::class.java)
        `when`(mockContext.filesDir).thenReturn(tempFolder.root)
        val note = Note(title = "hello", content = "world")
        val blob = Blob.of(note, ZlibCompressor)
        val time: Long = 0L
        val commit = Commit(null, blob, time, Sha1Hasher)
        val file = FileController.write(
            mockContext.filesDir,
            commit,
            ZlibCompressor
        )
        assertThat(file.path).contains(
            Paths.get("3a", "39bca4821d6a2c02b64bbb54ebe3b6e032d77c").toString()
        )
        assertThat(ZlibCompressor.decompress(file.readBytes()))
            .isEqualTo("blob ${blob.hash}\\ntime $time")
    }
}
