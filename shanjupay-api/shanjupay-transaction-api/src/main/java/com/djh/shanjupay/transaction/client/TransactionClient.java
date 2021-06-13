package com.djh.shanjupay.transaction.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 交易客户端
 *
 * @author MyMrDiao
 * @date 2021/06/11
 */
@FeignClient("sj-transaction")
public interface TransactionClient {

}
