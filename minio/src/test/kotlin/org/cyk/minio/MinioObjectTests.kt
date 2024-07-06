package org.cyk.minio

import io.minio.*
import io.minio.errors.ErrorResponseException
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.io.FileInputStream

@SpringBootTest
class MinioObjectTests {

    @Resource
    private lateinit var minioClient: MinioClient

    @Test
    fun test1() {
        val file = File("D:/tmp/滑稽.gif")
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket("dir1")
                .`object`("test.gif")
                //stream:
                //第二个参数(long objectSize): 要上传的文件大小
                //第三个参数(long partSize): 指定缓冲区的大小
                //这两个如果不知道，都可以使用 -1
                .stream(FileInputStream(file), file.length(), -1)
                .build()
        )
    }

    @Test
    fun test2() {
        minioClient.uploadObject(
            UploadObjectArgs.builder()
                .bucket("dir1")
                .`object`("test.gif")
                .filename("D:/tmp/滑稽.gif")
                .build()
        )
    }

    @Test
    fun test3() {
        try {
            val result = minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket("dir1")
                    .`object`("testa1.gif")
                    .build()
            )
            println(result)
        } catch (e: ErrorResponseException) {
            println("文件不存在！")
            println(e.message)
        }

    }



}
