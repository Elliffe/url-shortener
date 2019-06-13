### URL Shortener

The service takes full length URLs and converts them to a shortened version.
This is achieved by generating a unique Base10 ID for the URL and converting it to Base62. 
The original URL is stored in the database with the Base10
ID.
 
When a GET request containing an ID is received, the Base62 String is converted back to Base10
and the database is queried for the URL with the corresponding ID and a redirect is sent 
to the client. Each request is recorded for statistic reporting.

-----
#### Endpoints
`api/v1/shorten` accepts POST requests in the format: 
``` $json
{
    "url": "{LONG_URL}"
}
```
 
---
Statistics for a given short URL can be accessed using a GET request to `api/v1/stats/{Base62ID}`

The response to this request is in the format:
```$json
{
    "originalUrl": "{LONG_URL}",
    "totalClicks": "{X}",
    "last7Days": "{X}",
    "last30Days": "{X}"
}
```
---
##### **Running**
To build and run the application and a MongoDB instance:
```$xslt
mvnw install dockerfile:build
docker-compose up
```

##### On Windows
If running on Windows it may be necessary to enable the option to `Expose daemon on tcp://localhost:2375 without TLS` within Docker settings

---
##### **Environment Variables**
There are 3 environment variables used by the system:
- BASE_URL
  - This is the hostname where the service is deployed - `localhost:8080` when running locally 
- spring.data.mongodb.uri
  - The URI for your Mongo instance - `mongodb://mongo:27017/url-shortener` when running locally using the docker-compose YAML
- RANGE_SIZE
  - Blocks of Base10 ID's are reserved by each service to reduce load on the database. This env var defines how many ID's each service will reserve at a time