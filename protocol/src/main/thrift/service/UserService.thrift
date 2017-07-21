namespace java com.zero.thrift.protocol.service
include "../struct/request/UserRequest.thrift"
include "../struct/response/UserResponse.thrift"

 service UserService
 {
     UserResponse.TUserResult userInfo(1:UserRequest.TUserParam request),
 }