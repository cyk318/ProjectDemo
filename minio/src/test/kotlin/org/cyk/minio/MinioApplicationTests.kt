package org.cyk.minio

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.math.min

@SpringBootTest
class MinioApplicationTests {

    @Resource
    private lateinit var minioClient: MinioClient

    /**
     * 如果文件夹 dir1 不存在就创建
     */
    @Test
    fun test1() {
        val exists = minioClient.bucketExists(
            BucketExistsArgs.builder()
                .bucket("dir1")
                .build()
        )
        if (!exists) {
            minioClient.makeBucket(
                MakeBucketArgs.builder()
                    .bucket("dir1")
                    .build()
            )
        } else {
            println("文件夹已经存在！")
        }
    }

    @Test
    fun test2() {
        val bucketList = minioClient.listBuckets()
        bucketList.forEach { bucket ->
            println("${bucket.name()} -- ${bucket.creationDate()}")
        }
    }


}
