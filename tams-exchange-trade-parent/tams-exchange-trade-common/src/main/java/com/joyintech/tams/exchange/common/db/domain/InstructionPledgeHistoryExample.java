package com.joyintech.tams.exchange.common.db.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InstructionPledgeHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InstructionPledgeHistoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andEntrustIdIsNull() {
            addCriterion("ENTRUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andEntrustIdIsNotNull() {
            addCriterion("ENTRUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustIdEqualTo(String value) {
            addCriterion("ENTRUST_ID =", value, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdNotEqualTo(String value) {
            addCriterion("ENTRUST_ID <>", value, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdGreaterThan(String value) {
            addCriterion("ENTRUST_ID >", value, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdGreaterThanOrEqualTo(String value) {
            addCriterion("ENTRUST_ID >=", value, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdLessThan(String value) {
            addCriterion("ENTRUST_ID <", value, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdLessThanOrEqualTo(String value) {
            addCriterion("ENTRUST_ID <=", value, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdLike(String value) {
            addCriterion("ENTRUST_ID like", value, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdNotLike(String value) {
            addCriterion("ENTRUST_ID not like", value, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdIn(List<String> values) {
            addCriterion("ENTRUST_ID in", values, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdNotIn(List<String> values) {
            addCriterion("ENTRUST_ID not in", values, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdBetween(String value1, String value2) {
            addCriterion("ENTRUST_ID between", value1, value2, "entrustId");
            return (Criteria) this;
        }

        public Criteria andEntrustIdNotBetween(String value1, String value2) {
            addCriterion("ENTRUST_ID not between", value1, value2, "entrustId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdIsNull() {
            addCriterion("FINPROD_ID is null");
            return (Criteria) this;
        }

        public Criteria andFinprodIdIsNotNull() {
            addCriterion("FINPROD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFinprodIdEqualTo(String value) {
            addCriterion("FINPROD_ID =", value, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdNotEqualTo(String value) {
            addCriterion("FINPROD_ID <>", value, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdGreaterThan(String value) {
            addCriterion("FINPROD_ID >", value, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdGreaterThanOrEqualTo(String value) {
            addCriterion("FINPROD_ID >=", value, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdLessThan(String value) {
            addCriterion("FINPROD_ID <", value, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdLessThanOrEqualTo(String value) {
            addCriterion("FINPROD_ID <=", value, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdLike(String value) {
            addCriterion("FINPROD_ID like", value, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdNotLike(String value) {
            addCriterion("FINPROD_ID not like", value, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdIn(List<String> values) {
            addCriterion("FINPROD_ID in", values, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdNotIn(List<String> values) {
            addCriterion("FINPROD_ID not in", values, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdBetween(String value1, String value2) {
            addCriterion("FINPROD_ID between", value1, value2, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodIdNotBetween(String value1, String value2) {
            addCriterion("FINPROD_ID not between", value1, value2, "finprodId");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrIsNull() {
            addCriterion("FINPROD_ABBR is null");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrIsNotNull() {
            addCriterion("FINPROD_ABBR is not null");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrEqualTo(String value) {
            addCriterion("FINPROD_ABBR =", value, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrNotEqualTo(String value) {
            addCriterion("FINPROD_ABBR <>", value, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrGreaterThan(String value) {
            addCriterion("FINPROD_ABBR >", value, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrGreaterThanOrEqualTo(String value) {
            addCriterion("FINPROD_ABBR >=", value, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrLessThan(String value) {
            addCriterion("FINPROD_ABBR <", value, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrLessThanOrEqualTo(String value) {
            addCriterion("FINPROD_ABBR <=", value, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrLike(String value) {
            addCriterion("FINPROD_ABBR like", value, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrNotLike(String value) {
            addCriterion("FINPROD_ABBR not like", value, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrIn(List<String> values) {
            addCriterion("FINPROD_ABBR in", values, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrNotIn(List<String> values) {
            addCriterion("FINPROD_ABBR not in", values, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrBetween(String value1, String value2) {
            addCriterion("FINPROD_ABBR between", value1, value2, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andFinprodAbbrNotBetween(String value1, String value2) {
            addCriterion("FINPROD_ABBR not between", value1, value2, "finprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTradeMarketIsNull() {
            addCriterion("TRADE_MARKET is null");
            return (Criteria) this;
        }

        public Criteria andTradeMarketIsNotNull() {
            addCriterion("TRADE_MARKET is not null");
            return (Criteria) this;
        }

        public Criteria andTradeMarketEqualTo(String value) {
            addCriterion("TRADE_MARKET =", value, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketNotEqualTo(String value) {
            addCriterion("TRADE_MARKET <>", value, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketGreaterThan(String value) {
            addCriterion("TRADE_MARKET >", value, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketGreaterThanOrEqualTo(String value) {
            addCriterion("TRADE_MARKET >=", value, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketLessThan(String value) {
            addCriterion("TRADE_MARKET <", value, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketLessThanOrEqualTo(String value) {
            addCriterion("TRADE_MARKET <=", value, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketLike(String value) {
            addCriterion("TRADE_MARKET like", value, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketNotLike(String value) {
            addCriterion("TRADE_MARKET not like", value, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketIn(List<String> values) {
            addCriterion("TRADE_MARKET in", values, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketNotIn(List<String> values) {
            addCriterion("TRADE_MARKET not in", values, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketBetween(String value1, String value2) {
            addCriterion("TRADE_MARKET between", value1, value2, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andTradeMarketNotBetween(String value1, String value2) {
            addCriterion("TRADE_MARKET not between", value1, value2, "tradeMarket");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdIsNull() {
            addCriterion("FINPROD_MARKET_ID is null");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdIsNotNull() {
            addCriterion("FINPROD_MARKET_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdEqualTo(String value) {
            addCriterion("FINPROD_MARKET_ID =", value, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdNotEqualTo(String value) {
            addCriterion("FINPROD_MARKET_ID <>", value, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdGreaterThan(String value) {
            addCriterion("FINPROD_MARKET_ID >", value, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdGreaterThanOrEqualTo(String value) {
            addCriterion("FINPROD_MARKET_ID >=", value, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdLessThan(String value) {
            addCriterion("FINPROD_MARKET_ID <", value, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdLessThanOrEqualTo(String value) {
            addCriterion("FINPROD_MARKET_ID <=", value, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdLike(String value) {
            addCriterion("FINPROD_MARKET_ID like", value, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdNotLike(String value) {
            addCriterion("FINPROD_MARKET_ID not like", value, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdIn(List<String> values) {
            addCriterion("FINPROD_MARKET_ID in", values, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdNotIn(List<String> values) {
            addCriterion("FINPROD_MARKET_ID not in", values, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdBetween(String value1, String value2) {
            addCriterion("FINPROD_MARKET_ID between", value1, value2, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andFinprodMarketIdNotBetween(String value1, String value2) {
            addCriterion("FINPROD_MARKET_ID not between", value1, value2, "finprodMarketId");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueIsNull() {
            addCriterion("PLEDGE_FACE_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueIsNotNull() {
            addCriterion("PLEDGE_FACE_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_FACE_VALUE =", value, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueNotEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_FACE_VALUE <>", value, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueGreaterThan(BigDecimal value) {
            addCriterion("PLEDGE_FACE_VALUE >", value, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_FACE_VALUE >=", value, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueLessThan(BigDecimal value) {
            addCriterion("PLEDGE_FACE_VALUE <", value, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_FACE_VALUE <=", value, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_FACE_VALUE in", values, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueNotIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_FACE_VALUE not in", values, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_FACE_VALUE between", value1, value2, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeFaceValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_FACE_VALUE not between", value1, value2, "pledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberIsNull() {
            addCriterion("PLEDGE_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberIsNotNull() {
            addCriterion("PLEDGE_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_NUMBER =", value, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberNotEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_NUMBER <>", value, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberGreaterThan(BigDecimal value) {
            addCriterion("PLEDGE_NUMBER >", value, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_NUMBER >=", value, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberLessThan(BigDecimal value) {
            addCriterion("PLEDGE_NUMBER <", value, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_NUMBER <=", value, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_NUMBER in", values, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberNotIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_NUMBER not in", values, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_NUMBER between", value1, value2, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeNumberNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_NUMBER not between", value1, value2, "pledgeNumber");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioIsNull() {
            addCriterion("PLEDGE_RATIO is null");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioIsNotNull() {
            addCriterion("PLEDGE_RATIO is not null");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_RATIO =", value, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioNotEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_RATIO <>", value, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioGreaterThan(BigDecimal value) {
            addCriterion("PLEDGE_RATIO >", value, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_RATIO >=", value, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioLessThan(BigDecimal value) {
            addCriterion("PLEDGE_RATIO <", value, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_RATIO <=", value, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_RATIO in", values, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioNotIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_RATIO not in", values, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_RATIO between", value1, value2, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_RATIO not between", value1, value2, "pledgeRatio");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtIsNull() {
            addCriterion("PLEDGE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtIsNotNull() {
            addCriterion("PLEDGE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_AMT =", value, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtNotEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_AMT <>", value, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtGreaterThan(BigDecimal value) {
            addCriterion("PLEDGE_AMT >", value, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_AMT >=", value, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtLessThan(BigDecimal value) {
            addCriterion("PLEDGE_AMT <", value, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_AMT <=", value, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_AMT in", values, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtNotIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_AMT not in", values, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_AMT between", value1, value2, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andPledgeAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_AMT not between", value1, value2, "pledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdIsNull() {
            addCriterion("OLD_FINPROD_ID is null");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdIsNotNull() {
            addCriterion("OLD_FINPROD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdEqualTo(String value) {
            addCriterion("OLD_FINPROD_ID =", value, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdNotEqualTo(String value) {
            addCriterion("OLD_FINPROD_ID <>", value, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdGreaterThan(String value) {
            addCriterion("OLD_FINPROD_ID >", value, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_FINPROD_ID >=", value, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdLessThan(String value) {
            addCriterion("OLD_FINPROD_ID <", value, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdLessThanOrEqualTo(String value) {
            addCriterion("OLD_FINPROD_ID <=", value, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdLike(String value) {
            addCriterion("OLD_FINPROD_ID like", value, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdNotLike(String value) {
            addCriterion("OLD_FINPROD_ID not like", value, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdIn(List<String> values) {
            addCriterion("OLD_FINPROD_ID in", values, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdNotIn(List<String> values) {
            addCriterion("OLD_FINPROD_ID not in", values, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdBetween(String value1, String value2) {
            addCriterion("OLD_FINPROD_ID between", value1, value2, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodIdNotBetween(String value1, String value2) {
            addCriterion("OLD_FINPROD_ID not between", value1, value2, "oldFinprodId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrIsNull() {
            addCriterion("OLD_FINPROD_ABBR is null");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrIsNotNull() {
            addCriterion("OLD_FINPROD_ABBR is not null");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrEqualTo(String value) {
            addCriterion("OLD_FINPROD_ABBR =", value, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrNotEqualTo(String value) {
            addCriterion("OLD_FINPROD_ABBR <>", value, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrGreaterThan(String value) {
            addCriterion("OLD_FINPROD_ABBR >", value, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_FINPROD_ABBR >=", value, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrLessThan(String value) {
            addCriterion("OLD_FINPROD_ABBR <", value, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrLessThanOrEqualTo(String value) {
            addCriterion("OLD_FINPROD_ABBR <=", value, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrLike(String value) {
            addCriterion("OLD_FINPROD_ABBR like", value, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrNotLike(String value) {
            addCriterion("OLD_FINPROD_ABBR not like", value, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrIn(List<String> values) {
            addCriterion("OLD_FINPROD_ABBR in", values, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrNotIn(List<String> values) {
            addCriterion("OLD_FINPROD_ABBR not in", values, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrBetween(String value1, String value2) {
            addCriterion("OLD_FINPROD_ABBR between", value1, value2, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldFinprodAbbrNotBetween(String value1, String value2) {
            addCriterion("OLD_FINPROD_ABBR not between", value1, value2, "oldFinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketIsNull() {
            addCriterion("OLD_TRADE_MARKET is null");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketIsNotNull() {
            addCriterion("OLD_TRADE_MARKET is not null");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketEqualTo(String value) {
            addCriterion("OLD_TRADE_MARKET =", value, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketNotEqualTo(String value) {
            addCriterion("OLD_TRADE_MARKET <>", value, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketGreaterThan(String value) {
            addCriterion("OLD_TRADE_MARKET >", value, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_TRADE_MARKET >=", value, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketLessThan(String value) {
            addCriterion("OLD_TRADE_MARKET <", value, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketLessThanOrEqualTo(String value) {
            addCriterion("OLD_TRADE_MARKET <=", value, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketLike(String value) {
            addCriterion("OLD_TRADE_MARKET like", value, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketNotLike(String value) {
            addCriterion("OLD_TRADE_MARKET not like", value, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketIn(List<String> values) {
            addCriterion("OLD_TRADE_MARKET in", values, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketNotIn(List<String> values) {
            addCriterion("OLD_TRADE_MARKET not in", values, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketBetween(String value1, String value2) {
            addCriterion("OLD_TRADE_MARKET between", value1, value2, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldTradeMarketNotBetween(String value1, String value2) {
            addCriterion("OLD_TRADE_MARKET not between", value1, value2, "oldTradeMarket");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdIsNull() {
            addCriterion("OLD_FINPROD_MARKET_ID is null");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdIsNotNull() {
            addCriterion("OLD_FINPROD_MARKET_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdEqualTo(String value) {
            addCriterion("OLD_FINPROD_MARKET_ID =", value, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdNotEqualTo(String value) {
            addCriterion("OLD_FINPROD_MARKET_ID <>", value, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdGreaterThan(String value) {
            addCriterion("OLD_FINPROD_MARKET_ID >", value, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_FINPROD_MARKET_ID >=", value, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdLessThan(String value) {
            addCriterion("OLD_FINPROD_MARKET_ID <", value, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdLessThanOrEqualTo(String value) {
            addCriterion("OLD_FINPROD_MARKET_ID <=", value, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdLike(String value) {
            addCriterion("OLD_FINPROD_MARKET_ID like", value, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdNotLike(String value) {
            addCriterion("OLD_FINPROD_MARKET_ID not like", value, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdIn(List<String> values) {
            addCriterion("OLD_FINPROD_MARKET_ID in", values, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdNotIn(List<String> values) {
            addCriterion("OLD_FINPROD_MARKET_ID not in", values, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdBetween(String value1, String value2) {
            addCriterion("OLD_FINPROD_MARKET_ID between", value1, value2, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldFinprodMarketIdNotBetween(String value1, String value2) {
            addCriterion("OLD_FINPROD_MARKET_ID not between", value1, value2, "oldFinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueIsNull() {
            addCriterion("OLD_PLEDGE_FACE_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueIsNotNull() {
            addCriterion("OLD_PLEDGE_FACE_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_FACE_VALUE =", value, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueNotEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_FACE_VALUE <>", value, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueGreaterThan(BigDecimal value) {
            addCriterion("OLD_PLEDGE_FACE_VALUE >", value, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_FACE_VALUE >=", value, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueLessThan(BigDecimal value) {
            addCriterion("OLD_PLEDGE_FACE_VALUE <", value, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_FACE_VALUE <=", value, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueIn(List<BigDecimal> values) {
            addCriterion("OLD_PLEDGE_FACE_VALUE in", values, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueNotIn(List<BigDecimal> values) {
            addCriterion("OLD_PLEDGE_FACE_VALUE not in", values, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_PLEDGE_FACE_VALUE between", value1, value2, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeFaceValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_PLEDGE_FACE_VALUE not between", value1, value2, "oldPledgeFaceValue");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberIsNull() {
            addCriterion("OLD_PLEDGE_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberIsNotNull() {
            addCriterion("OLD_PLEDGE_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_NUMBER =", value, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberNotEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_NUMBER <>", value, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberGreaterThan(BigDecimal value) {
            addCriterion("OLD_PLEDGE_NUMBER >", value, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_NUMBER >=", value, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberLessThan(BigDecimal value) {
            addCriterion("OLD_PLEDGE_NUMBER <", value, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_NUMBER <=", value, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberIn(List<BigDecimal> values) {
            addCriterion("OLD_PLEDGE_NUMBER in", values, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberNotIn(List<BigDecimal> values) {
            addCriterion("OLD_PLEDGE_NUMBER not in", values, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_PLEDGE_NUMBER between", value1, value2, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeNumberNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_PLEDGE_NUMBER not between", value1, value2, "oldPledgeNumber");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioIsNull() {
            addCriterion("OLD_PLEDGE_RATIO is null");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioIsNotNull() {
            addCriterion("OLD_PLEDGE_RATIO is not null");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_RATIO =", value, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioNotEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_RATIO <>", value, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioGreaterThan(BigDecimal value) {
            addCriterion("OLD_PLEDGE_RATIO >", value, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_RATIO >=", value, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioLessThan(BigDecimal value) {
            addCriterion("OLD_PLEDGE_RATIO <", value, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_RATIO <=", value, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioIn(List<BigDecimal> values) {
            addCriterion("OLD_PLEDGE_RATIO in", values, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioNotIn(List<BigDecimal> values) {
            addCriterion("OLD_PLEDGE_RATIO not in", values, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_PLEDGE_RATIO between", value1, value2, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_PLEDGE_RATIO not between", value1, value2, "oldPledgeRatio");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtIsNull() {
            addCriterion("OLD_PLEDGE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtIsNotNull() {
            addCriterion("OLD_PLEDGE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_AMT =", value, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtNotEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_AMT <>", value, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtGreaterThan(BigDecimal value) {
            addCriterion("OLD_PLEDGE_AMT >", value, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_AMT >=", value, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtLessThan(BigDecimal value) {
            addCriterion("OLD_PLEDGE_AMT <", value, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OLD_PLEDGE_AMT <=", value, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtIn(List<BigDecimal> values) {
            addCriterion("OLD_PLEDGE_AMT in", values, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtNotIn(List<BigDecimal> values) {
            addCriterion("OLD_PLEDGE_AMT not in", values, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_PLEDGE_AMT between", value1, value2, "oldPledgeAmt");
            return (Criteria) this;
        }

        public Criteria andOldPledgeAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OLD_PLEDGE_AMT not between", value1, value2, "oldPledgeAmt");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}