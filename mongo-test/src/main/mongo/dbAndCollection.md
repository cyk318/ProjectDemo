```mongodb-json

show databases

use article

db

db.createCollection('tmp')

show table

show tables

db.tmp.drop()

db.createCollection('article',
    {
        max: 100,
        size: 10240,
        capped: true
    }
)

db.user.insertOne(
    {
        "_id": 1,
        "username": "tmp",
        "password": "tmp",
        "c_time": Date()
    }
)

db.user.findOne({username: 'tmp'})

db.user.deleteOne({username: 'tmp'})

// 查询 _id >= 1、 _id < 10、 title 的前缀是 "使用"、
db.article.insertMany([
    { _id: 1, title: "理解 Node.js", content: "Node.js 是一个基于 Chrome V8 引擎的 JavaScript 运行时。", c_time: Date() },
    { _id: 2, title: "Express.js 入门", content: "Express.js 是一个基于 Node.js 的 web 应用框架，用于构建 web 应用和 API。", c_time: Date() },
    { _id: 3, title: "MongoDB 入门", content: "MongoDB 是一种面向文档的 NoSQL 数据库，用于高容量数据存储。", c_time: Date() },
    { _id: 4, title: "RESTful API 设计", content: "RESTful API 设计模式有助于创建可扩展和可维护的 web 服务。", c_time: Date() },
    { _id: 5, title: "在 MongoDB 中使用 Mongoose", content: "Mongoose 是一个适用于 MongoDB 和 Node.js 的 ODM 库，提供了一种基于 schema 的解决方案来建模应用数据。", c_time: Date() },
    { _id: 6, title: "理解 JWT 认证", content: "JWT（JSON Web Token）是一种用于在各方之间传递声明的紧凑且自包含的方式。", c_time: Date() },
    { _id: 7, title: "使用 Docker 部署应用", content: "Docker 是一个开源平台，用于自动化应用程序在容器中的部署、扩展和管理。", c_time: Date() },
    { _id: 8, title: "Nginx 反向代理设置", content: "Nginx 是一个高性能的 HTTP 和反向代理服务器，用于处理高并发连接。", c_time: Date() },
    { _id: 9, title: "使用 Redis 进行缓存", content: "Redis 是一个开源的内存数据结构存储，用作数据库、缓存和消息代理。", c_time: Date() },
    { _id: 10, title: "GraphQL 基础", content: "GraphQL 是一种用于 API 的查询语言，以及服务器端运行的用于执行查询的运行时。", c_time: Date() }
])

db.article.updateOne(
    { _id: 1 },
    {$set: {title: "深入立即 Node.js"}}
)

db.article.updateMany(
    { title: "理解 JWT 认证" },
    {$set: {title: "深入学习 JWT 认证底层原理"}}
)


db.article.find({_id: 1})

db.article.find()

```