namespace java com.zero.thrift.protocol.response

#响应体通用结构
struct TResponseStatus {
    # 响应码 ，00000表示响应成功，默认值响应成功
    1: string code;
    # 错误时的错误描述信息
    2: string msg = "";
}