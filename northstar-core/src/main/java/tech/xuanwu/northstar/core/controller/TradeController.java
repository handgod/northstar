package tech.xuanwu.northstar.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import tech.xuanwu.northstar.common.ResultBean;
import tech.xuanwu.northstar.core.service.TradeService;
import tech.xuanwu.northstar.exception.NoSuchAccountException;
import xyz.redtorch.pb.CoreEnum.DirectionEnum;
import xyz.redtorch.pb.CoreEnum.OffsetFlagEnum;

@Slf4j
@RestController
@RequestMapping("/trade")
@Api(tags="交易相关操作")
public class TradeController {
	
	@Autowired
	private TradeService tradeService;

	@RequestMapping(value="/order", method=RequestMethod.POST)
	@ApiOperation("账户发送委托单")
	public ResultBean<String> submitOrder(String accountGatewayId, String contractSymbol, double price, int volume, DirectionEnum direction,
			OffsetFlagEnum transactionType){
		
		try {
			return new ResultBean<>(tradeService.submitOrder(accountGatewayId, contractSymbol, price, volume, direction, transactionType));
		} catch (Exception e) {
			return new ResultBean<String>(ResultBean.ReturnCode.ERROR, e.getMessage());
		}
	}
	
	@RequestMapping(value="/cancel", method=RequestMethod.DELETE)
	@ApiOperation("账户撤销委托单")
	public ResultBean<Void> cancelOrder(String accountGatewayId, String originOrderId){
		try {
			tradeService.cancelOrder(accountGatewayId, originOrderId);
		} catch (NoSuchAccountException e) {
			return new ResultBean<>(ResultBean.ReturnCode.ERROR, e.getMessage());
		}
		return new ResultBean(Void.TYPE);
	}
	
	@RequestMapping(value="/sellout", method=RequestMethod.POST)
	@ApiOperation("账户一键全平【危险】")
	public ResultBean<Void> sellOutAllPosition(String accountGatewayId){
		log.info("【警告】账户一键全平");
		try {
			tradeService.sellOutAllPosition(accountGatewayId);
		} catch (NoSuchAccountException e) {
			return new ResultBean<>(ResultBean.ReturnCode.ERROR, e.getMessage());
		}
		return new ResultBean(Void.TYPE);
	}
}
