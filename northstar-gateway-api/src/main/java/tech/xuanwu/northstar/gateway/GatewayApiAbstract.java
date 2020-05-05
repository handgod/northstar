package tech.xuanwu.northstar.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tech.xuanwu.northstar.engine.FastEventEngine;
import xyz.redtorch.pb.CoreEnum.ConnectStatusEnum;
import xyz.redtorch.pb.CoreField.AccountField;
import xyz.redtorch.pb.CoreField.ContractField;
import xyz.redtorch.pb.CoreField.GatewayField;
import xyz.redtorch.pb.CoreField.GatewaySettingField;
import xyz.redtorch.pb.CoreField.NoticeField;
import xyz.redtorch.pb.CoreField.OrderField;
import xyz.redtorch.pb.CoreField.PositionField;
import xyz.redtorch.pb.CoreField.TickField;
import xyz.redtorch.pb.CoreField.TradeField;

public abstract class GatewayApiAbstract implements GatewayApi {

	private static Logger log = LoggerFactory.getLogger(GatewayApiAbstract.class);

	protected String gatewayId;
	protected String gatewayName;
	protected String logInfo;
	private boolean autoErrorFlag = false;
	protected long lastConnectBeginTimestamp = 0;
	
	protected String gatewayTradingDay;

	protected GatewaySettingField gatewaySetting;

	private FastEventEngine fastEventService;

	private GatewayField gateway;

	public GatewayApiAbstract(FastEventEngine fastEventService, GatewaySettingField gatewaySetting) {
		this.fastEventService = fastEventService;
		this.gatewaySetting = gatewaySetting;
		this.gatewayId = gatewaySetting.getGatewayId();
		this.gatewayName = gatewaySetting.getGatewayName();
		this.logInfo = "网关ID-[" + gatewayId + "] 名称-[" + gatewayName + "] [→] ";

		GatewayField.Builder gatewayBuilder = GatewayField.newBuilder();
		gatewayBuilder.setDescription(gatewaySetting.getGatewayDescription());
		gatewayBuilder.setGatewayAdapterType(gatewaySetting.getGatewayAdapterType());
		gatewayBuilder.setGatewayType(gatewaySetting.getGatewayType());
		gatewayBuilder.setGatewayId(gatewaySetting.getGatewayId());
		gatewayBuilder.setName(gatewaySetting.getGatewayName());
		gatewayBuilder.setStatus(ConnectStatusEnum.CS_Disconnected);
		gateway = gatewayBuilder.build();
		log.info(logInfo + "开始初始化");

	}

	@Override
	public boolean getAuthErrorFlag() {
		return autoErrorFlag;
	}

	@Override
	public void setAuthErrorFlag(boolean authErrorFlag) {
		this.autoErrorFlag = authErrorFlag;
	}

	@Override
	public long getLastConnectBeginTimestamp() {
		return this.lastConnectBeginTimestamp;
	}

	@Override
	public GatewaySettingField getGatewaySetting() {
		return gatewaySetting;
	}

	@Override
	public String getGatewayId() {
		return gatewayId;
	}

	@Override
	public String getGatewayName() {
		return gatewayName;
	}
	
	@Override
	public String getTradingDay() {
		return gatewayTradingDay;
	}
	
	public void setTradingDay(String tradingDay) {
		this.gatewayTradingDay = tradingDay;
	}

	protected String getLogInfo() {
		return this.logInfo;
	}

	@Override
	public GatewayField getGateway() {
		return gateway;
	}
	
	@Override
	public FastEventEngine getEventEngine() {
		return fastEventService;
	}

	@Override
	public void emitTick(TickField tick) {
		fastEventService.emitTick(tick);
	}
}
