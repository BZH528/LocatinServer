# LocatinServer
数据需要去实时查询最新的定位数据。这里通过hbase去存最新的一条数据，然后通过webserver的方式向客户端提供服务接口

接口地址：http://192.168.10.101:8081/api/location/search
请求方式：post
接口参数：code
返回值:logitude,
      latitude,
      locatetime,
      code
 
 
postman请求：http://192.168.10.101:8081/api/location/search?code=A8A5EDE952B7AE3088FB16A96D31F555
返回值：
{
    "logitude": "113320578",
    "latitude": "23202051",
    "locatetime": "2021-08-13 09:54:32",
    "code": "A8A5EDE952B7AE3088FB16A96D31F555"
}
