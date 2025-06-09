
lenderPaymentState	支付状态	支付状态，对账管理-通道对账明细	
LenderExceptionState	异常状态	异常状态，对账管理-通道对账明细异常处理	
LenderExceptionType	异常类型	异常类型，对账管理-通道对账明细异常处理	
lenderLoanStatus	贷款状态	贷款状态，贷款管理-贷款查询，交易流水查询-放款查询	
lenderAuditStatus	查账状态	查账状态，对账管理-通道对账明细	
lenderRepaymentMode	还款方式	还款方式，对账管理-资金方还款计划	
lenderRefundStatus	还款状态	还款状态，对账管理-资金方还款计划	
lenderInterestCalculation	计息方式	计息方式，对账管理-资金方还款计划	
lenderTransType	交易方式	交易方式，借款人信息-线下还款录入	
			
lenderPaymentState	value	编码	编码
lenderPaymentState	name	名称	名称
LenderExceptionState	value	编码	编码
LenderExceptionState	name	名称	名称
LenderExceptionType	value	编码	编码
LenderExceptionType	name	名称	名称
lenderLoanStatus	value	编码	编码
lenderLoanStatus	name	名称	名称
lenderAuditStatus	value	编码	编码
lenderAuditStatus	name	名称	名称
lenderRepaymentMode	value	编码	编码
lenderRepaymentMode	name	名称	名称
lenderRefundStatus	value	编码	编码
lenderRefundStatus	name	名称	名称
lenderInterestCalculation	value	编码	编码
lenderInterestCalculation	name	名称	名称
lenderTransType	value	编码	编码
lenderTransType	name	名称	名称

contentFlag

lenderPaymentState	00	支付失败
lenderPaymentState	02	支付成功
		
LenderExceptionState	1	待处理
LenderExceptionState	2	处理中
LenderExceptionState	3	处理成功
LenderExceptionState	4	处理失败
		
LenderExceptionType	1	待处理
LenderExceptionType	2	处理中
LenderExceptionType	3	处理成功
LenderExceptionType	4	处理失败 
		
lenderLoanStatus	confirmed	贷款确认
lenderLoanStatus	disbursed	放款成功
lenderLoanStatus	disbursed_failed	放款失败
lenderLoanStatus	cancelled	贷款取消
lenderLoanStatus	closed	贷款完成
		
lenderAuditStatus	1	待处理
lenderAuditStatus	2	处理中
lenderAuditStatus	3	处理成功
lenderAuditStatus	4	处理失败 
		
lenderRepaymentMode	YB	提前全额结清
lenderRepaymentMode	Y8	还到当前期
lenderRepaymentMode	YD	按期还款
lenderRepaymentMode	YE	还款到不逾期
lenderRepaymentMode	YF	还款到指定期
lenderRepaymentMode	YG	展期还款
		
		
lenderRefundStatus	init	初始化
lenderRefundStatus	succeeded	成功
		
lenderInterestCalculation	by_day	按日计息
lenderInterestCalculation	by_installment	按期计算
		
lenderTransType	01	充值
lenderTransType	02	提现
