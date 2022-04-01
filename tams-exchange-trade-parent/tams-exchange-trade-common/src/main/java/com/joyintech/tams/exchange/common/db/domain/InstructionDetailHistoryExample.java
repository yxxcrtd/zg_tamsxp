package com.joyintech.tams.exchange.common.db.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InstructionDetailHistoryExample {
    protected String orderByClause;
    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InstructionDetailHistoryExample() {
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

        public Criteria andPortfolioIdIsNull() {
            addCriterion("PORTFOLIO_ID is null");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdIsNotNull() {
            addCriterion("PORTFOLIO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdEqualTo(String value) {
            addCriterion("PORTFOLIO_ID =", value, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdNotEqualTo(String value) {
            addCriterion("PORTFOLIO_ID <>", value, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdGreaterThan(String value) {
            addCriterion("PORTFOLIO_ID >", value, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdGreaterThanOrEqualTo(String value) {
            addCriterion("PORTFOLIO_ID >=", value, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdLessThan(String value) {
            addCriterion("PORTFOLIO_ID <", value, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdLessThanOrEqualTo(String value) {
            addCriterion("PORTFOLIO_ID <=", value, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdLike(String value) {
            addCriterion("PORTFOLIO_ID like", value, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdNotLike(String value) {
            addCriterion("PORTFOLIO_ID not like", value, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdIn(List<String> values) {
            addCriterion("PORTFOLIO_ID in", values, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdNotIn(List<String> values) {
            addCriterion("PORTFOLIO_ID not in", values, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdBetween(String value1, String value2) {
            addCriterion("PORTFOLIO_ID between", value1, value2, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioIdNotBetween(String value1, String value2) {
            addCriterion("PORTFOLIO_ID not between", value1, value2, "portfolioId");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameIsNull() {
            addCriterion("PORTFOLIO_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameIsNotNull() {
            addCriterion("PORTFOLIO_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameEqualTo(String value) {
            addCriterion("PORTFOLIO_NAME =", value, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameNotEqualTo(String value) {
            addCriterion("PORTFOLIO_NAME <>", value, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameGreaterThan(String value) {
            addCriterion("PORTFOLIO_NAME >", value, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameGreaterThanOrEqualTo(String value) {
            addCriterion("PORTFOLIO_NAME >=", value, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameLessThan(String value) {
            addCriterion("PORTFOLIO_NAME <", value, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameLessThanOrEqualTo(String value) {
            addCriterion("PORTFOLIO_NAME <=", value, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameLike(String value) {
            addCriterion("PORTFOLIO_NAME like", value, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameNotLike(String value) {
            addCriterion("PORTFOLIO_NAME not like", value, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameIn(List<String> values) {
            addCriterion("PORTFOLIO_NAME in", values, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameNotIn(List<String> values) {
            addCriterion("PORTFOLIO_NAME not in", values, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameBetween(String value1, String value2) {
            addCriterion("PORTFOLIO_NAME between", value1, value2, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andPortfolioNameNotBetween(String value1, String value2) {
            addCriterion("PORTFOLIO_NAME not between", value1, value2, "portfolioName");
            return (Criteria) this;
        }

        public Criteria andDisRateIsNull() {
            addCriterion("DIS_RATE is null");
            return (Criteria) this;
        }

        public Criteria andDisRateIsNotNull() {
            addCriterion("DIS_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andDisRateEqualTo(BigDecimal value) {
            addCriterion("DIS_RATE =", value, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateNotEqualTo(BigDecimal value) {
            addCriterion("DIS_RATE <>", value, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateGreaterThan(BigDecimal value) {
            addCriterion("DIS_RATE >", value, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DIS_RATE >=", value, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateLessThan(BigDecimal value) {
            addCriterion("DIS_RATE <", value, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DIS_RATE <=", value, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateIn(List<BigDecimal> values) {
            addCriterion("DIS_RATE in", values, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateNotIn(List<BigDecimal> values) {
            addCriterion("DIS_RATE not in", values, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DIS_RATE between", value1, value2, "disRate");
            return (Criteria) this;
        }

        public Criteria andDisRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DIS_RATE not between", value1, value2, "disRate");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtIsNull() {
            addCriterion("ENTRUST_AMT is null");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtIsNotNull() {
            addCriterion("ENTRUST_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_AMT =", value, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtNotEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_AMT <>", value, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtGreaterThan(BigDecimal value) {
            addCriterion("ENTRUST_AMT >", value, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_AMT >=", value, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtLessThan(BigDecimal value) {
            addCriterion("ENTRUST_AMT <", value, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_AMT <=", value, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtIn(List<BigDecimal> values) {
            addCriterion("ENTRUST_AMT in", values, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtNotIn(List<BigDecimal> values) {
            addCriterion("ENTRUST_AMT not in", values, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ENTRUST_AMT between", value1, value2, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ENTRUST_AMT not between", value1, value2, "entrustAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustNumIsNull() {
            addCriterion("ENTRUST_NUM is null");
            return (Criteria) this;
        }

        public Criteria andEntrustNumIsNotNull() {
            addCriterion("ENTRUST_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustNumEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_NUM =", value, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumNotEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_NUM <>", value, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumGreaterThan(BigDecimal value) {
            addCriterion("ENTRUST_NUM >", value, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_NUM >=", value, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumLessThan(BigDecimal value) {
            addCriterion("ENTRUST_NUM <", value, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_NUM <=", value, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumIn(List<BigDecimal> values) {
            addCriterion("ENTRUST_NUM in", values, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumNotIn(List<BigDecimal> values) {
            addCriterion("ENTRUST_NUM not in", values, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ENTRUST_NUM between", value1, value2, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andEntrustNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ENTRUST_NUM not between", value1, value2, "entrustNum");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIsNull() {
            addCriterion("TARGET_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIsNotNull() {
            addCriterion("TARGET_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeEqualTo(String value) {
            addCriterion("TARGET_TYPE =", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotEqualTo(String value) {
            addCriterion("TARGET_TYPE <>", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeGreaterThan(String value) {
            addCriterion("TARGET_TYPE >", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TARGET_TYPE >=", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeLessThan(String value) {
            addCriterion("TARGET_TYPE <", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeLessThanOrEqualTo(String value) {
            addCriterion("TARGET_TYPE <=", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeLike(String value) {
            addCriterion("TARGET_TYPE like", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotLike(String value) {
            addCriterion("TARGET_TYPE not like", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIn(List<String> values) {
            addCriterion("TARGET_TYPE in", values, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotIn(List<String> values) {
            addCriterion("TARGET_TYPE not in", values, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBetween(String value1, String value2) {
            addCriterion("TARGET_TYPE between", value1, value2, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotBetween(String value1, String value2) {
            addCriterion("TARGET_TYPE not between", value1, value2, "targetType");
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