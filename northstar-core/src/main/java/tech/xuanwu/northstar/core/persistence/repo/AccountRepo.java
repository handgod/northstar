package tech.xuanwu.northstar.core.persistence.repo;

import tech.xuanwu.northstar.entity.AccountInfo;

public interface AccountRepo {

	/**
	 * 依据交易日保存更新账户信息
	 * @param account
	 * @param tradingDay
	 * @return
	 * @throws Exception
	 */
	boolean upsertByDate(AccountInfo account) throws Exception;
	
	/**
	 * 依据网关ID获取最近交易日账户信息
	 * @param accountName
	 * @return
	 * @throws Exception
	 */
	AccountInfo getLatestAccountInfoByAccountId(String accountId) throws Exception;
}
