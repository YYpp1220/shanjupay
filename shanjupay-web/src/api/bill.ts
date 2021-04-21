import request from '@/utils/request'
import { UserModule } from '@/store/modules/user'

//往来对账
export const useMoney =  () => 
    request ({
        url:`/operation/m/reconciliations`,
        method:'post'
    })

//运营平台交易明细查询
export const useMoneyDetail =  (transactionTime:string) => 
request ({
    url:`/operation/m/transactiondetails?transactionTime=${transactionTime}`,
    method:'post'
})