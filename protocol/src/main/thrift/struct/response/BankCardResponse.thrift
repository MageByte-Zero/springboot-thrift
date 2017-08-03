namespace java com.zero.thrift.protocol.response
include "../CommonStruct.thrift"

/**
* 银行信息
*
*/
struct TBankInfo{
	 /**
	 * 银行代码
	 */
    1:string bankCode;
     /**
	 * 银行名称
	 */
    2:string bankName;
     /**
	 * 单笔限额
	 */
    3:string singlePaymentLimit;
     /**
	 *日限额
	 */
    4:string dayPaymentLimit;
     /**
	  *月限额
	  */
	5:string monthPaymentLimit;

	 /**
	  *银行logo图片地址
	  */
	 6:string bankLogo;
 }

 struct TGetBankCardListResult{
 		 /**
 		 *响应状态信息
 		 */
 		1: CommonStruct.TResponseStatus responseStatus;
 		/**
 		* 可用银行列表
 		*/
 		 2:list<TBankInfo> dataList;
 		 /**
 		 *返回结果大小
 		 */
 		 3: i32 dataListCapacity;
 }