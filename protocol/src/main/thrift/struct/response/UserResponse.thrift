namespace java com.zero.thrift.protocol.response

struct TUserResult
{
    1:string code;
    2:map<string,string> params;
}