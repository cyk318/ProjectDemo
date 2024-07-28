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


```