namespace java com.zero.thrift.protocol.response

struct TUserResult
{
    1:string code;
    2:map<string,string> params;
}

struct TCompanyResult
{
    1: i32 id;
    2: string code;
    3: list<TUser> userList;
    4: i32 page;
    5: i32 pageSize;
}

struct TUser
{
    1: i64 id;
    2: string name;
    3: i32 age;
    4: list<TStatus> statusList;
}

struct TStatus
{
    1: i32 state;
    2: string msg;
}