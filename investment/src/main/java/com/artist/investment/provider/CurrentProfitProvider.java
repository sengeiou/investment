package com.artist.investment.provider;

import com.artist.investment.constant.RepaymentMethod;
import com.artist.investment.domain.Investment;
import com.dili.ss.metadata.*;
import com.dili.ss.util.DateUtils;
import com.dili.ss.util.MoneyUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 当前收益提供者
 */
@Component
public class CurrentProfitProvider implements ValueProvider {

    @Override
    public List<ValuePair<?>> getLookupList(Object obj, Map metaMap, FieldMeta fieldMeta) {
        return null;
    }

    @Override
    public String getDisplayText(Object obj, Map metaMap, FieldMeta fieldMeta) {
        Investment investment = (Investment)metaMap.get(ROW_DATA_KEY);
        //非到期还款,直接显示当前已到帐资金
        if(!investment.getRepaymentMethod().equals(RepaymentMethod.DUE)){
            return MoneyUtils.centToYuan(investment.getArrived());
        }
        if(investment.getStartDate() == null){
            return "0.00";
        }
        //如果还没开始计算收益，则收益为0
        if(investment.getStartDate().after(new Date())){
            return "0.00";
        }
        //求开始投资时间到现在的相关天数
        int defDay = DateUtils.differentDays(investment.getStartDate(), new Date());
        //总天数
//        int totalDay = DateUtils.differentDays(investment.getStartDate(), investment.getEndDate());
        //计算开始投资时间到现在的收益(单位元)
        //获取投资额，因为这里已经被批量提供者转换过了，所以要取原始值，如果没有原始值才取字段的值
        Object investmentObj = investment.aget(ValueProviderUtils.ORIGINAL_KEY_PREFIX + "investment");
        if(investmentObj == null){
            investmentObj = investment.aget("investment");
        }
        Long investment1 = Long.parseLong(investmentObj.toString());
        Object deductedObj = investment.aget(ValueProviderUtils.ORIGINAL_KEY_PREFIX + "deducted");
        if(deductedObj == null){
            deductedObj = investment.aget("deducted");
        }
        Long deducted = Long.parseLong(deductedObj.toString());
        //(投资额 + 抵扣额) * (年化收益率 + 利率加息券) * 已投资天数 / 总天数 / 100%
        BigDecimal bigDecimal = new BigDecimal((investment1+deducted) * (investment.getProfitRatio()+ investment.getInterestCoupon()) * defDay);
        BigDecimal bigDecimalTotalDay = new BigDecimal(365);
        BigDecimal bigDecimal100 = new BigDecimal(100);

        if(investment.getRepaymentMethod().equals(RepaymentMethod.DUE.getCode())) {
            //精确计算两位小数，并且四舍五入
            Long profit = bigDecimal.divide(bigDecimalTotalDay, 0, BigDecimal.ROUND_HALF_DOWN).divide(bigDecimal100, 0, BigDecimal.ROUND_HALF_DOWN).longValue();
            return MoneyUtils.centToYuan(profit);
        }else{//非到期还款，直接取当前到帐额
            return MoneyUtils.centToYuan(investment.getArrived());
        }
    }
}