namespace java com.zero.thrift.protocol.service
include "../struct/request/UserRequest.thrift"
include "../struct/response/UserResponse.thrift"

 service ThriftUserService
 {
     UserResponse.TUserResult userInfo(1:UserRequest.TUserParam request),

     UserResponse.TCompanyResult getCompanyUserList(1:UserRequest.TCompanyParam request),
 }