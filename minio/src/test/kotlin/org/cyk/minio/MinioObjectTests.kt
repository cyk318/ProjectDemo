package org.cyk.minio

import io.minio.*
import io.minio.errors.ErrorResponseException
import io.minio.http.Method
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
import kotlin.math.min

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
                .`object`("test2.gif")
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

    @Test
    fun test4() {
        val url = minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .bucket("dir1")
                .`object`("test.gif")
                .method(Method.GET) //指定生成的 url 请求方式
                // .expiry(3, TimeUnit.SECONDS) 设置签名的过期时间
                .build()
        )
        println(url)
    }

    @Test
    fun test5() {
        //新建文件夹 dir2
        minioClient.makeBucket(
            MakeBucketArgs.builder()
                .bucket("dir2")
                .build()
        )

        //设置文件夹的访问权限为 只读
        val jsonConfig = """
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": {
                "AWS": [
                    "*"
                ]
            },
            "Action": [
                "s3:GetBucketLocation"
            ],
            "Resource": [
                "arn:aws:s3:::dir2"
            ]
        },
        {
            "Effect": "Allow",
            "Principal": {
                "AWS": [
                    "*"
                ]
            },
            "Action": [
                "s3:ListBucket"
            ],
            "Resource": [
                "arn:aws:s3:::dir2"
            ],
            "Condition": {
                "StringEquals": {
                    "s3:prefix": [
                        "*"
                    ]
                }
            }
        },
        {
            "Effect": "Allow",
            "Principal": {
                "AWS": [
                    "*"
                ]
            },
            "Action": [
                "s3:GetObject"
            ],
            "Resource": [
                "arn:aws:s3:::dir2/**"
            ]
        }
    ]
}
        """.trimIndent()

        minioClient.setBucketPolicy(
            SetBucketPolicyArgs.builder()
                .bucket("dir2")
                .config(jsonConfig)
                .build()
        )

        //上传图片
        minioClient.uploadObject(
            UploadObjectArgs.builder()
                .bucket("dir2")
                .`object`("test.gif")
                .filename("D:/tmp/滑稽.gif")
                .build()
        )

        val url = minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .bucket("dir2")
                .`object`("test.gif")
                .method(Method.GET)
                .build()
        ).split("?")[0] //这样就可以不需要 令牌 部分了
        println(url)
    }

    @Test
    fun test6() {
        val resp = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket("dir1")
                .`object`("test.gif")
                .build()
        )
        val file = FileOutputStream("D:/tmp/123.gif")
        val result = resp.transferTo(file)
        println(result)
    }

    @Test
    fun test7() {
        val objects = minioClient.listObjects(
            ListObjectsArgs.builder()
                .bucket("dir1")
                .build()
        )
        objects.forEach { result ->
            val item = result.get()
            println(item.objectName())
        }
    }

    @Test
    fun test8() {
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket("dir1")
                .`object`("test2.jpg")
                .build()
        )
    }

}
