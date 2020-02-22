package tech.xuanwu.northstar.domain;

import java.util.List;

import tech.xuanwu.northstar.entity.AccountInfo;
import xyz.redtorch.pb.CoreField.AccountField;

/**
 * 账户接口
 * @author kevinhuangwl
 *
 */
public interface IAccount extends Tradable {
	
	AccountInfo getAccountInfo();
	
	void updateAccount(AccountInfo account);
	
	List<IStrategy> getStrategyList();
	
	void regStrategy(String strategyName);
	
	void unregStrategy(String strategyName);
	
	void sellOutAllPosition();
	
	void connectGateway();
	
	void disconnectGateway();
}
