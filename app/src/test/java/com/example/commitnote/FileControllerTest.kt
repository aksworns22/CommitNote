package com.example.commitnote

import android.content.Context
import com.example.commitnote.core.Blob
import com.example.commitnote.core.Note
import com.example.commitnote.core.ZlibCompressor
import com.example.commitnote.data.FileController
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito
import org.mockito.Mockito.`when`

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
        assertThat(file.path).contains("63/b77d3e36388e6c463f85726a4bb4585479d96e")
        assertThat(ZlibCompressor.decompress(file.readBytes())).isEqualTo("hello\\0world")
    }
}
