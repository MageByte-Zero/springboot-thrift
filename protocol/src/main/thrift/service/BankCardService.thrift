namespace java com.zero.thrift.protocol.service
include "../struct/response/BankCardResponse.thrift"

 service ThriftBankCardService
 {
     BankCardResponse.TGetBankCardListResult getBankCardList(),

 }