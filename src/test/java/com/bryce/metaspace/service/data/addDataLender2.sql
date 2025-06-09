lenderLoanMode	财务系统，放款模式	财务系统，放款模式	
lenderRepayMode	财务系统，还款模式	财务系统，还款模式	
lenderTunnelCode	财务系统，通道编码	财务系统，通道编码	
lenderBindcardMode	财务系统，绑卡方式	财务系统，绑卡方式	
lenderAccountType	财务系统，账户类型	财务系统，账户类型	
lenderBalanceResult	财务系统，对账状态	财务系统，对账状态	
lenderAuditStatus	财务系统，补录状态	财务系统，补录状态	
lenderSettleDirectionCode	财务系统，结算方向	财务系统，结算方向	
			
lenderLoanMode	value	放款模式编码	放款模式编码
lenderLoanMode	name	放款模式名称	放款模式名称
lenderRepayMode	value	还款模式编码	还款模式编码
lenderRepayMode	name	还款模式名称	还款模式名称
lenderTunnelCode	value	通道编码编码	通道编码编码
lenderTunnelCode	name	通道编码名称	通道编码名称
lenderBindcardMode	value	绑卡方式编码	绑卡方式编码
lenderBindcardMode	name	绑卡方式名称	绑卡方式名称
lenderAccountType	value	账户类型编码	账户类型编码
lenderAccountType	name	账户类型名称	账户类型名称
lenderBalanceResult	value	对账状态编码	对账状态编码
lenderBalanceResult	name	对账状态名称	对账状态名称
lenderAuditStatus	value	补录状态编码	补录状态编码
lenderAuditStatus	name	补录状态名称	补录状态名称
lenderSettleDirectionCode	value	结算方向编码	结算方向编码
lenderSettleDirectionCode	name	结算方向名称	结算方向名称

lenderPaymentChannelCost	支付通道费用规则	支付通道费用规则	
lenderPaymentChannelCost	value	支付通道费用规则编码	支付通道费用规则编码
lenderPaymentChannelCost	name	支付通道费用规则名称	支付通道费用规则名称


contentFlag

lenderLoanMode	bryce	我放
lenderLoanMode	capital	他放
		
lenderRepayMode	bryce	我收
lenderRepayMode	capital	他收
		
lenderTunnelCode	allinpay      	通联通道
lenderTunnelCode	umpay         	联动优势通道
lenderTunnelCode	cmbcdirect    	招行银企直连通道
lenderTunnelCode	wechat        	微信支付
lenderTunnelCode	alipay        	支付宝
lenderTunnelCode	zhongan       	众安
lenderTunnelCode	jincheng      	锦城
lenderTunnelCode	kaolalicai    	考拉
lenderTunnelCode	hzfi          	金投
lenderTunnelCode	ztssj         	随手记
lenderTunnelCode	zhenrongbao   	真融宝
lenderTunnelCode	tongbanjie    	铜板街
lenderTunnelCode	dingdong      	叮咚钱包
lenderTunnelCode	facebank      	笑脸
lenderTunnelCode	fcs           	富登
lenderTunnelCode	fsk           	富士康
lenderTunnelCode	other         	其他通道
lenderTunnelCode	yntrust	云南信托
		
lenderBindcardMode	sms	短信
lenderBindcardMode	thirdparty	网页
		
lenderAccountType	BR0001     	借款人现金账户
lenderAccountType	BR0001F    	借款人现金账户冻结账户
lenderAccountType	BR0002     	资金方现金账户
lenderAccountType	BR0002F    	资金方现金账户冻结账户
lenderAccountType	BR0003     	保证金账户
lenderAccountType	BR0003F    	保证金账户冻结账户
lenderAccountType	BR0004     	平台管理费账户
lenderAccountType	BR0004F    	平台管理费账户冻结账户
lenderAccountType	BR0005     	平台减免账户
lenderAccountType	BR0005F    	平台减免账户冻结账户
lenderAccountType	BR0006     	平台红包账户
lenderAccountType	BR0006F    	平台红包账户冻结账户
lenderAccountType	BR0007     	平台风险垫付账户
lenderAccountType	BR0007F    	平台风险垫付账户冻结账户
lenderAccountType	BR0008     	平台线下还款账户
lenderAccountType	BR0008F    	平台线下还款账户冻结账户
lenderAccountType	BR0009     	平台委外费账户
lenderAccountType	BR0009F    	平台委外费账户冻结账户
lenderAccountType	BR0010     	平台挂账账户
lenderAccountType	BR0010F    	平台挂账账户冻结账户
lenderAccountType	BR0011     	平台不良资产账户
lenderAccountType	BR0011F    	平台不良资产账户冻结账户
		
lenderBalanceResult	2	通道对账
lenderBalanceResult	3	平台多帐
		
lenderAuditStatus	01	未补录
lenderAuditStatus	02	补录中
		
lenderSettleDirectionCode	01	收
lenderSettleDirectionCode	02	付

lenderPaymentChannelCost	umpay_ptp_mer_bind_card	联动优势绑定银行卡
lenderPaymentChannelCost	umpay_mer_recharge_person_nopwd	联动优势个人客户无密充值
lenderPaymentChannelCost	umpay_mer_recharge	联动优势企业客户网关充值
lenderPaymentChannelCost	umpay_cust_withdrawals	联动优势个人客户有密提现
lenderPaymentChannelCost	umpay_cust_withdrawals_nopwd	联动优势个人客户无密提现
lenderPaymentChannelCost	umpay_mer_withdrawals	联动优势企业客户提现
lenderPaymentChannelCost	allinpay_100011	通联单笔代收
lenderPaymentChannelCost	allinpay_211006C	通联实名付确认
lenderPaymentChannelCost	allinpay_100014_0	通联代付提现
lenderPaymentChannelCost	allinpay_100014_1	通联对公代付
lenderPaymentChannelCost	allinpay_211005	通联账户实时签约(四要素)
lenderPaymentChannelCost	cmbcdirect_DCPAYMNT_1	招行银企直接支付
lenderPaymentChannelCost	cmbcdirect_DCPAYMNT_2	招行跨行同城普通直接支付
lenderPaymentChannelCost	wechat_unify_make_order	微信统一下单
