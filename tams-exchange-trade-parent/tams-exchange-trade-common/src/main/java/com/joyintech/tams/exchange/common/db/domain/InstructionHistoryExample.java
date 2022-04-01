package com.joyintech.tams.exchange.common.db.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstructionHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InstructionHistoryExample() {
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

        public Criteria andFinprodNameIsNull() {
            addCriterion("FINPROD_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFinprodNameIsNotNull() {
            addCriterion("FINPROD_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFinprodNameEqualTo(String value) {
            addCriterion("FINPROD_NAME =", value, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameNotEqualTo(String value) {
            addCriterion("FINPROD_NAME <>", value, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameGreaterThan(String value) {
            addCriterion("FINPROD_NAME >", value, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameGreaterThanOrEqualTo(String value) {
            addCriterion("FINPROD_NAME >=", value, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameLessThan(String value) {
            addCriterion("FINPROD_NAME <", value, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameLessThanOrEqualTo(String value) {
            addCriterion("FINPROD_NAME <=", value, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameLike(String value) {
            addCriterion("FINPROD_NAME like", value, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameNotLike(String value) {
            addCriterion("FINPROD_NAME not like", value, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameIn(List<String> values) {
            addCriterion("FINPROD_NAME in", values, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameNotIn(List<String> values) {
            addCriterion("FINPROD_NAME not in", values, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameBetween(String value1, String value2) {
            addCriterion("FINPROD_NAME between", value1, value2, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodNameNotBetween(String value1, String value2) {
            addCriterion("FINPROD_NAME not between", value1, value2, "finprodName");
            return (Criteria) this;
        }

        public Criteria andFinprodType2IsNull() {
            addCriterion("FINPROD_TYPE2 is null");
            return (Criteria) this;
        }

        public Criteria andFinprodType2IsNotNull() {
            addCriterion("FINPROD_TYPE2 is not null");
            return (Criteria) this;
        }

        public Criteria andFinprodType2EqualTo(String value) {
            addCriterion("FINPROD_TYPE2 =", value, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2NotEqualTo(String value) {
            addCriterion("FINPROD_TYPE2 <>", value, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2GreaterThan(String value) {
            addCriterion("FINPROD_TYPE2 >", value, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2GreaterThanOrEqualTo(String value) {
            addCriterion("FINPROD_TYPE2 >=", value, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2LessThan(String value) {
            addCriterion("FINPROD_TYPE2 <", value, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2LessThanOrEqualTo(String value) {
            addCriterion("FINPROD_TYPE2 <=", value, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2Like(String value) {
            addCriterion("FINPROD_TYPE2 like", value, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2NotLike(String value) {
            addCriterion("FINPROD_TYPE2 not like", value, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2In(List<String> values) {
            addCriterion("FINPROD_TYPE2 in", values, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2NotIn(List<String> values) {
            addCriterion("FINPROD_TYPE2 not in", values, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2Between(String value1, String value2) {
            addCriterion("FINPROD_TYPE2 between", value1, value2, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andFinprodType2NotBetween(String value1, String value2) {
            addCriterion("FINPROD_TYPE2 not between", value1, value2, "finprodType2");
            return (Criteria) this;
        }

        public Criteria andAcctNoIsNull() {
            addCriterion("ACCT_NO is null");
            return (Criteria) this;
        }

        public Criteria andAcctNoIsNotNull() {
            addCriterion("ACCT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andAcctNoEqualTo(String value) {
            addCriterion("ACCT_NO =", value, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoNotEqualTo(String value) {
            addCriterion("ACCT_NO <>", value, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoGreaterThan(String value) {
            addCriterion("ACCT_NO >", value, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoGreaterThanOrEqualTo(String value) {
            addCriterion("ACCT_NO >=", value, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoLessThan(String value) {
            addCriterion("ACCT_NO <", value, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoLessThanOrEqualTo(String value) {
            addCriterion("ACCT_NO <=", value, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoLike(String value) {
            addCriterion("ACCT_NO like", value, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoNotLike(String value) {
            addCriterion("ACCT_NO not like", value, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoIn(List<String> values) {
            addCriterion("ACCT_NO in", values, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoNotIn(List<String> values) {
            addCriterion("ACCT_NO not in", values, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoBetween(String value1, String value2) {
            addCriterion("ACCT_NO between", value1, value2, "acctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNoNotBetween(String value1, String value2) {
            addCriterion("ACCT_NO not between", value1, value2, "acctNo");
            return (Criteria) this;
        }

        public Criteria andCcyIsNull() {
            addCriterion("CCY is null");
            return (Criteria) this;
        }

        public Criteria andCcyIsNotNull() {
            addCriterion("CCY is not null");
            return (Criteria) this;
        }

        public Criteria andCcyEqualTo(String value) {
            addCriterion("CCY =", value, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyNotEqualTo(String value) {
            addCriterion("CCY <>", value, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyGreaterThan(String value) {
            addCriterion("CCY >", value, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyGreaterThanOrEqualTo(String value) {
            addCriterion("CCY >=", value, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyLessThan(String value) {
            addCriterion("CCY <", value, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyLessThanOrEqualTo(String value) {
            addCriterion("CCY <=", value, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyLike(String value) {
            addCriterion("CCY like", value, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyNotLike(String value) {
            addCriterion("CCY not like", value, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyIn(List<String> values) {
            addCriterion("CCY in", values, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyNotIn(List<String> values) {
            addCriterion("CCY not in", values, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyBetween(String value1, String value2) {
            addCriterion("CCY between", value1, value2, "ccy");
            return (Criteria) this;
        }

        public Criteria andCcyNotBetween(String value1, String value2) {
            addCriterion("CCY not between", value1, value2, "ccy");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoIsNull() {
            addCriterion("BUSI_ACCT_NO is null");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoIsNotNull() {
            addCriterion("BUSI_ACCT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoEqualTo(String value) {
            addCriterion("BUSI_ACCT_NO =", value, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoNotEqualTo(String value) {
            addCriterion("BUSI_ACCT_NO <>", value, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoGreaterThan(String value) {
            addCriterion("BUSI_ACCT_NO >", value, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_ACCT_NO >=", value, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoLessThan(String value) {
            addCriterion("BUSI_ACCT_NO <", value, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoLessThanOrEqualTo(String value) {
            addCriterion("BUSI_ACCT_NO <=", value, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoLike(String value) {
            addCriterion("BUSI_ACCT_NO like", value, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoNotLike(String value) {
            addCriterion("BUSI_ACCT_NO not like", value, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoIn(List<String> values) {
            addCriterion("BUSI_ACCT_NO in", values, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoNotIn(List<String> values) {
            addCriterion("BUSI_ACCT_NO not in", values, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoBetween(String value1, String value2) {
            addCriterion("BUSI_ACCT_NO between", value1, value2, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andBusiAcctNoNotBetween(String value1, String value2) {
            addCriterion("BUSI_ACCT_NO not between", value1, value2, "busiAcctNo");
            return (Criteria) this;
        }

        public Criteria andAcctNameIsNull() {
            addCriterion("ACCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAcctNameIsNotNull() {
            addCriterion("ACCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAcctNameEqualTo(String value) {
            addCriterion("ACCT_NAME =", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameNotEqualTo(String value) {
            addCriterion("ACCT_NAME <>", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameGreaterThan(String value) {
            addCriterion("ACCT_NAME >", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameGreaterThanOrEqualTo(String value) {
            addCriterion("ACCT_NAME >=", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameLessThan(String value) {
            addCriterion("ACCT_NAME <", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameLessThanOrEqualTo(String value) {
            addCriterion("ACCT_NAME <=", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameLike(String value) {
            addCriterion("ACCT_NAME like", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameNotLike(String value) {
            addCriterion("ACCT_NAME not like", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameIn(List<String> values) {
            addCriterion("ACCT_NAME in", values, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameNotIn(List<String> values) {
            addCriterion("ACCT_NAME not in", values, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameBetween(String value1, String value2) {
            addCriterion("ACCT_NAME between", value1, value2, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameNotBetween(String value1, String value2) {
            addCriterion("ACCT_NAME not between", value1, value2, "acctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdIsNull() {
            addCriterion("SEC_ACCT_ID is null");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdIsNotNull() {
            addCriterion("SEC_ACCT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdEqualTo(String value) {
            addCriterion("SEC_ACCT_ID =", value, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdNotEqualTo(String value) {
            addCriterion("SEC_ACCT_ID <>", value, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdGreaterThan(String value) {
            addCriterion("SEC_ACCT_ID >", value, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdGreaterThanOrEqualTo(String value) {
            addCriterion("SEC_ACCT_ID >=", value, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdLessThan(String value) {
            addCriterion("SEC_ACCT_ID <", value, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdLessThanOrEqualTo(String value) {
            addCriterion("SEC_ACCT_ID <=", value, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdLike(String value) {
            addCriterion("SEC_ACCT_ID like", value, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdNotLike(String value) {
            addCriterion("SEC_ACCT_ID not like", value, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdIn(List<String> values) {
            addCriterion("SEC_ACCT_ID in", values, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdNotIn(List<String> values) {
            addCriterion("SEC_ACCT_ID not in", values, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdBetween(String value1, String value2) {
            addCriterion("SEC_ACCT_ID between", value1, value2, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctIdNotBetween(String value1, String value2) {
            addCriterion("SEC_ACCT_ID not between", value1, value2, "secAcctId");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameIsNull() {
            addCriterion("SEC_ACCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameIsNotNull() {
            addCriterion("SEC_ACCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameEqualTo(String value) {
            addCriterion("SEC_ACCT_NAME =", value, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameNotEqualTo(String value) {
            addCriterion("SEC_ACCT_NAME <>", value, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameGreaterThan(String value) {
            addCriterion("SEC_ACCT_NAME >", value, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameGreaterThanOrEqualTo(String value) {
            addCriterion("SEC_ACCT_NAME >=", value, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameLessThan(String value) {
            addCriterion("SEC_ACCT_NAME <", value, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameLessThanOrEqualTo(String value) {
            addCriterion("SEC_ACCT_NAME <=", value, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameLike(String value) {
            addCriterion("SEC_ACCT_NAME like", value, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameNotLike(String value) {
            addCriterion("SEC_ACCT_NAME not like", value, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameIn(List<String> values) {
            addCriterion("SEC_ACCT_NAME in", values, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameNotIn(List<String> values) {
            addCriterion("SEC_ACCT_NAME not in", values, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameBetween(String value1, String value2) {
            addCriterion("SEC_ACCT_NAME between", value1, value2, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andSecAcctNameNotBetween(String value1, String value2) {
            addCriterion("SEC_ACCT_NAME not between", value1, value2, "secAcctName");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoIsNull() {
            addCriterion("STOCK_HOLDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoIsNotNull() {
            addCriterion("STOCK_HOLDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoEqualTo(String value) {
            addCriterion("STOCK_HOLDER_NO =", value, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoNotEqualTo(String value) {
            addCriterion("STOCK_HOLDER_NO <>", value, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoGreaterThan(String value) {
            addCriterion("STOCK_HOLDER_NO >", value, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoGreaterThanOrEqualTo(String value) {
            addCriterion("STOCK_HOLDER_NO >=", value, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoLessThan(String value) {
            addCriterion("STOCK_HOLDER_NO <", value, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoLessThanOrEqualTo(String value) {
            addCriterion("STOCK_HOLDER_NO <=", value, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoLike(String value) {
            addCriterion("STOCK_HOLDER_NO like", value, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoNotLike(String value) {
            addCriterion("STOCK_HOLDER_NO not like", value, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoIn(List<String> values) {
            addCriterion("STOCK_HOLDER_NO in", values, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoNotIn(List<String> values) {
            addCriterion("STOCK_HOLDER_NO not in", values, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoBetween(String value1, String value2) {
            addCriterion("STOCK_HOLDER_NO between", value1, value2, "stockHolderNo");
            return (Criteria) this;
        }

        public Criteria andStockHolderNoNotBetween(String value1, String value2) {
            addCriterion("STOCK_HOLDER_NO not between", value1, value2, "stockHolderNo");
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

        public Criteria andTradePlatIsNull() {
            addCriterion("TRADE_PLAT is null");
            return (Criteria) this;
        }

        public Criteria andTradePlatIsNotNull() {
            addCriterion("TRADE_PLAT is not null");
            return (Criteria) this;
        }

        public Criteria andTradePlatEqualTo(String value) {
            addCriterion("TRADE_PLAT =", value, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatNotEqualTo(String value) {
            addCriterion("TRADE_PLAT <>", value, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatGreaterThan(String value) {
            addCriterion("TRADE_PLAT >", value, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatGreaterThanOrEqualTo(String value) {
            addCriterion("TRADE_PLAT >=", value, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatLessThan(String value) {
            addCriterion("TRADE_PLAT <", value, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatLessThanOrEqualTo(String value) {
            addCriterion("TRADE_PLAT <=", value, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatLike(String value) {
            addCriterion("TRADE_PLAT like", value, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatNotLike(String value) {
            addCriterion("TRADE_PLAT not like", value, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatIn(List<String> values) {
            addCriterion("TRADE_PLAT in", values, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatNotIn(List<String> values) {
            addCriterion("TRADE_PLAT not in", values, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatBetween(String value1, String value2) {
            addCriterion("TRADE_PLAT between", value1, value2, "tradePlat");
            return (Criteria) this;
        }

        public Criteria andTradePlatNotBetween(String value1, String value2) {
            addCriterion("TRADE_PLAT not between", value1, value2, "tradePlat");
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

        public Criteria andPsIsNull() {
            addCriterion("PS is null");
            return (Criteria) this;
        }

        public Criteria andPsIsNotNull() {
            addCriterion("PS is not null");
            return (Criteria) this;
        }

        public Criteria andPsEqualTo(String value) {
            addCriterion("PS =", value, "ps");
            return (Criteria) this;
        }

        public Criteria andPsNotEqualTo(String value) {
            addCriterion("PS <>", value, "ps");
            return (Criteria) this;
        }

        public Criteria andPsGreaterThan(String value) {
            addCriterion("PS >", value, "ps");
            return (Criteria) this;
        }

        public Criteria andPsGreaterThanOrEqualTo(String value) {
            addCriterion("PS >=", value, "ps");
            return (Criteria) this;
        }

        public Criteria andPsLessThan(String value) {
            addCriterion("PS <", value, "ps");
            return (Criteria) this;
        }

        public Criteria andPsLessThanOrEqualTo(String value) {
            addCriterion("PS <=", value, "ps");
            return (Criteria) this;
        }

        public Criteria andPsLike(String value) {
            addCriterion("PS like", value, "ps");
            return (Criteria) this;
        }

        public Criteria andPsNotLike(String value) {
            addCriterion("PS not like", value, "ps");
            return (Criteria) this;
        }

        public Criteria andPsIn(List<String> values) {
            addCriterion("PS in", values, "ps");
            return (Criteria) this;
        }

        public Criteria andPsNotIn(List<String> values) {
            addCriterion("PS not in", values, "ps");
            return (Criteria) this;
        }

        public Criteria andPsBetween(String value1, String value2) {
            addCriterion("PS between", value1, value2, "ps");
            return (Criteria) this;
        }

        public Criteria andPsNotBetween(String value1, String value2) {
            addCriterion("PS not between", value1, value2, "ps");
            return (Criteria) this;
        }

        public Criteria andDealModeIsNull() {
            addCriterion("DEAL_MODE is null");
            return (Criteria) this;
        }

        public Criteria andDealModeIsNotNull() {
            addCriterion("DEAL_MODE is not null");
            return (Criteria) this;
        }

        public Criteria andDealModeEqualTo(String value) {
            addCriterion("DEAL_MODE =", value, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeNotEqualTo(String value) {
            addCriterion("DEAL_MODE <>", value, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeGreaterThan(String value) {
            addCriterion("DEAL_MODE >", value, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeGreaterThanOrEqualTo(String value) {
            addCriterion("DEAL_MODE >=", value, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeLessThan(String value) {
            addCriterion("DEAL_MODE <", value, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeLessThanOrEqualTo(String value) {
            addCriterion("DEAL_MODE <=", value, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeLike(String value) {
            addCriterion("DEAL_MODE like", value, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeNotLike(String value) {
            addCriterion("DEAL_MODE not like", value, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeIn(List<String> values) {
            addCriterion("DEAL_MODE in", values, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeNotIn(List<String> values) {
            addCriterion("DEAL_MODE not in", values, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeBetween(String value1, String value2) {
            addCriterion("DEAL_MODE between", value1, value2, "dealMode");
            return (Criteria) this;
        }

        public Criteria andDealModeNotBetween(String value1, String value2) {
            addCriterion("DEAL_MODE not between", value1, value2, "dealMode");
            return (Criteria) this;
        }

        public Criteria andCounterIdIsNull() {
            addCriterion("COUNTER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCounterIdIsNotNull() {
            addCriterion("COUNTER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCounterIdEqualTo(String value) {
            addCriterion("COUNTER_ID =", value, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdNotEqualTo(String value) {
            addCriterion("COUNTER_ID <>", value, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdGreaterThan(String value) {
            addCriterion("COUNTER_ID >", value, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTER_ID >=", value, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdLessThan(String value) {
            addCriterion("COUNTER_ID <", value, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdLessThanOrEqualTo(String value) {
            addCriterion("COUNTER_ID <=", value, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdLike(String value) {
            addCriterion("COUNTER_ID like", value, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdNotLike(String value) {
            addCriterion("COUNTER_ID not like", value, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdIn(List<String> values) {
            addCriterion("COUNTER_ID in", values, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdNotIn(List<String> values) {
            addCriterion("COUNTER_ID not in", values, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdBetween(String value1, String value2) {
            addCriterion("COUNTER_ID between", value1, value2, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterIdNotBetween(String value1, String value2) {
            addCriterion("COUNTER_ID not between", value1, value2, "counterId");
            return (Criteria) this;
        }

        public Criteria andCounterSeatIsNull() {
            addCriterion("COUNTER_SEAT is null");
            return (Criteria) this;
        }

        public Criteria andCounterSeatIsNotNull() {
            addCriterion("COUNTER_SEAT is not null");
            return (Criteria) this;
        }

        public Criteria andCounterSeatEqualTo(String value) {
            addCriterion("COUNTER_SEAT =", value, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatNotEqualTo(String value) {
            addCriterion("COUNTER_SEAT <>", value, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatGreaterThan(String value) {
            addCriterion("COUNTER_SEAT >", value, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTER_SEAT >=", value, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatLessThan(String value) {
            addCriterion("COUNTER_SEAT <", value, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatLessThanOrEqualTo(String value) {
            addCriterion("COUNTER_SEAT <=", value, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatLike(String value) {
            addCriterion("COUNTER_SEAT like", value, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatNotLike(String value) {
            addCriterion("COUNTER_SEAT not like", value, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatIn(List<String> values) {
            addCriterion("COUNTER_SEAT in", values, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatNotIn(List<String> values) {
            addCriterion("COUNTER_SEAT not in", values, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatBetween(String value1, String value2) {
            addCriterion("COUNTER_SEAT between", value1, value2, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterSeatNotBetween(String value1, String value2) {
            addCriterion("COUNTER_SEAT not between", value1, value2, "counterSeat");
            return (Criteria) this;
        }

        public Criteria andCounterTraderIsNull() {
            addCriterion("COUNTER_TRADER is null");
            return (Criteria) this;
        }

        public Criteria andCounterTraderIsNotNull() {
            addCriterion("COUNTER_TRADER is not null");
            return (Criteria) this;
        }

        public Criteria andCounterTraderEqualTo(String value) {
            addCriterion("COUNTER_TRADER =", value, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderNotEqualTo(String value) {
            addCriterion("COUNTER_TRADER <>", value, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderGreaterThan(String value) {
            addCriterion("COUNTER_TRADER >", value, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTER_TRADER >=", value, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderLessThan(String value) {
            addCriterion("COUNTER_TRADER <", value, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderLessThanOrEqualTo(String value) {
            addCriterion("COUNTER_TRADER <=", value, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderLike(String value) {
            addCriterion("COUNTER_TRADER like", value, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderNotLike(String value) {
            addCriterion("COUNTER_TRADER not like", value, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderIn(List<String> values) {
            addCriterion("COUNTER_TRADER in", values, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderNotIn(List<String> values) {
            addCriterion("COUNTER_TRADER not in", values, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderBetween(String value1, String value2) {
            addCriterion("COUNTER_TRADER between", value1, value2, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterTraderNotBetween(String value1, String value2) {
            addCriterion("COUNTER_TRADER not between", value1, value2, "counterTrader");
            return (Criteria) this;
        }

        public Criteria andCounterInfoIsNull() {
            addCriterion("COUNTER_INFO is null");
            return (Criteria) this;
        }

        public Criteria andCounterInfoIsNotNull() {
            addCriterion("COUNTER_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andCounterInfoEqualTo(String value) {
            addCriterion("COUNTER_INFO =", value, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoNotEqualTo(String value) {
            addCriterion("COUNTER_INFO <>", value, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoGreaterThan(String value) {
            addCriterion("COUNTER_INFO >", value, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTER_INFO >=", value, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoLessThan(String value) {
            addCriterion("COUNTER_INFO <", value, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoLessThanOrEqualTo(String value) {
            addCriterion("COUNTER_INFO <=", value, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoLike(String value) {
            addCriterion("COUNTER_INFO like", value, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoNotLike(String value) {
            addCriterion("COUNTER_INFO not like", value, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoIn(List<String> values) {
            addCriterion("COUNTER_INFO in", values, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoNotIn(List<String> values) {
            addCriterion("COUNTER_INFO not in", values, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoBetween(String value1, String value2) {
            addCriterion("COUNTER_INFO between", value1, value2, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterInfoNotBetween(String value1, String value2) {
            addCriterion("COUNTER_INFO not between", value1, value2, "counterInfo");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdIsNull() {
            addCriterion("COUNTER_DEALER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdIsNotNull() {
            addCriterion("COUNTER_DEALER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdEqualTo(String value) {
            addCriterion("COUNTER_DEALER_ID =", value, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdNotEqualTo(String value) {
            addCriterion("COUNTER_DEALER_ID <>", value, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdGreaterThan(String value) {
            addCriterion("COUNTER_DEALER_ID >", value, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTER_DEALER_ID >=", value, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdLessThan(String value) {
            addCriterion("COUNTER_DEALER_ID <", value, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdLessThanOrEqualTo(String value) {
            addCriterion("COUNTER_DEALER_ID <=", value, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdLike(String value) {
            addCriterion("COUNTER_DEALER_ID like", value, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdNotLike(String value) {
            addCriterion("COUNTER_DEALER_ID not like", value, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdIn(List<String> values) {
            addCriterion("COUNTER_DEALER_ID in", values, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdNotIn(List<String> values) {
            addCriterion("COUNTER_DEALER_ID not in", values, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdBetween(String value1, String value2) {
            addCriterion("COUNTER_DEALER_ID between", value1, value2, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andCounterDealerIdNotBetween(String value1, String value2) {
            addCriterion("COUNTER_DEALER_ID not between", value1, value2, "counterDealerId");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberIsNull() {
            addCriterion("APPOINTMENT_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberIsNotNull() {
            addCriterion("APPOINTMENT_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberEqualTo(String value) {
            addCriterion("APPOINTMENT_NUMBER =", value, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberNotEqualTo(String value) {
            addCriterion("APPOINTMENT_NUMBER <>", value, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberGreaterThan(String value) {
            addCriterion("APPOINTMENT_NUMBER >", value, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberGreaterThanOrEqualTo(String value) {
            addCriterion("APPOINTMENT_NUMBER >=", value, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberLessThan(String value) {
            addCriterion("APPOINTMENT_NUMBER <", value, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberLessThanOrEqualTo(String value) {
            addCriterion("APPOINTMENT_NUMBER <=", value, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberLike(String value) {
            addCriterion("APPOINTMENT_NUMBER like", value, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberNotLike(String value) {
            addCriterion("APPOINTMENT_NUMBER not like", value, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberIn(List<String> values) {
            addCriterion("APPOINTMENT_NUMBER in", values, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberNotIn(List<String> values) {
            addCriterion("APPOINTMENT_NUMBER not in", values, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberBetween(String value1, String value2) {
            addCriterion("APPOINTMENT_NUMBER between", value1, value2, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andAppointmentNumberNotBetween(String value1, String value2) {
            addCriterion("APPOINTMENT_NUMBER not between", value1, value2, "appointmentNumber");
            return (Criteria) this;
        }

        public Criteria andDealNoIsNull() {
            addCriterion("DEAL_NO is null");
            return (Criteria) this;
        }

        public Criteria andDealNoIsNotNull() {
            addCriterion("DEAL_NO is not null");
            return (Criteria) this;
        }

        public Criteria andDealNoEqualTo(String value) {
            addCriterion("DEAL_NO =", value, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoNotEqualTo(String value) {
            addCriterion("DEAL_NO <>", value, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoGreaterThan(String value) {
            addCriterion("DEAL_NO >", value, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoGreaterThanOrEqualTo(String value) {
            addCriterion("DEAL_NO >=", value, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoLessThan(String value) {
            addCriterion("DEAL_NO <", value, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoLessThanOrEqualTo(String value) {
            addCriterion("DEAL_NO <=", value, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoLike(String value) {
            addCriterion("DEAL_NO like", value, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoNotLike(String value) {
            addCriterion("DEAL_NO not like", value, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoIn(List<String> values) {
            addCriterion("DEAL_NO in", values, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoNotIn(List<String> values) {
            addCriterion("DEAL_NO not in", values, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoBetween(String value1, String value2) {
            addCriterion("DEAL_NO between", value1, value2, "dealNo");
            return (Criteria) this;
        }

        public Criteria andDealNoNotBetween(String value1, String value2) {
            addCriterion("DEAL_NO not between", value1, value2, "dealNo");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdIsNull() {
            addCriterion("TRD_MATCH_ID is null");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdIsNotNull() {
            addCriterion("TRD_MATCH_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdEqualTo(String value) {
            addCriterion("TRD_MATCH_ID =", value, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdNotEqualTo(String value) {
            addCriterion("TRD_MATCH_ID <>", value, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdGreaterThan(String value) {
            addCriterion("TRD_MATCH_ID >", value, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdGreaterThanOrEqualTo(String value) {
            addCriterion("TRD_MATCH_ID >=", value, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdLessThan(String value) {
            addCriterion("TRD_MATCH_ID <", value, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdLessThanOrEqualTo(String value) {
            addCriterion("TRD_MATCH_ID <=", value, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdLike(String value) {
            addCriterion("TRD_MATCH_ID like", value, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdNotLike(String value) {
            addCriterion("TRD_MATCH_ID not like", value, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdIn(List<String> values) {
            addCriterion("TRD_MATCH_ID in", values, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdNotIn(List<String> values) {
            addCriterion("TRD_MATCH_ID not in", values, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdBetween(String value1, String value2) {
            addCriterion("TRD_MATCH_ID between", value1, value2, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andTrdMatchIdNotBetween(String value1, String value2) {
            addCriterion("TRD_MATCH_ID not between", value1, value2, "trdMatchId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdIsNull() {
            addCriterion("QUOTEREF_ID is null");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdIsNotNull() {
            addCriterion("QUOTEREF_ID is not null");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdEqualTo(String value) {
            addCriterion("QUOTEREF_ID =", value, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdNotEqualTo(String value) {
            addCriterion("QUOTEREF_ID <>", value, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdGreaterThan(String value) {
            addCriterion("QUOTEREF_ID >", value, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdGreaterThanOrEqualTo(String value) {
            addCriterion("QUOTEREF_ID >=", value, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdLessThan(String value) {
            addCriterion("QUOTEREF_ID <", value, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdLessThanOrEqualTo(String value) {
            addCriterion("QUOTEREF_ID <=", value, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdLike(String value) {
            addCriterion("QUOTEREF_ID like", value, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdNotLike(String value) {
            addCriterion("QUOTEREF_ID not like", value, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdIn(List<String> values) {
            addCriterion("QUOTEREF_ID in", values, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdNotIn(List<String> values) {
            addCriterion("QUOTEREF_ID not in", values, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdBetween(String value1, String value2) {
            addCriterion("QUOTEREF_ID between", value1, value2, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andQuoterefIdNotBetween(String value1, String value2) {
            addCriterion("QUOTEREF_ID not between", value1, value2, "quoterefId");
            return (Criteria) this;
        }

        public Criteria andConShareIdIsNull() {
            addCriterion("CON_SHARE_ID is null");
            return (Criteria) this;
        }

        public Criteria andConShareIdIsNotNull() {
            addCriterion("CON_SHARE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andConShareIdEqualTo(String value) {
            addCriterion("CON_SHARE_ID =", value, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdNotEqualTo(String value) {
            addCriterion("CON_SHARE_ID <>", value, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdGreaterThan(String value) {
            addCriterion("CON_SHARE_ID >", value, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdGreaterThanOrEqualTo(String value) {
            addCriterion("CON_SHARE_ID >=", value, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdLessThan(String value) {
            addCriterion("CON_SHARE_ID <", value, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdLessThanOrEqualTo(String value) {
            addCriterion("CON_SHARE_ID <=", value, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdLike(String value) {
            addCriterion("CON_SHARE_ID like", value, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdNotLike(String value) {
            addCriterion("CON_SHARE_ID not like", value, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdIn(List<String> values) {
            addCriterion("CON_SHARE_ID in", values, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdNotIn(List<String> values) {
            addCriterion("CON_SHARE_ID not in", values, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdBetween(String value1, String value2) {
            addCriterion("CON_SHARE_ID between", value1, value2, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConShareIdNotBetween(String value1, String value2) {
            addCriterion("CON_SHARE_ID not between", value1, value2, "conShareId");
            return (Criteria) this;
        }

        public Criteria andConPriceIsNull() {
            addCriterion("CON_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andConPriceIsNotNull() {
            addCriterion("CON_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andConPriceEqualTo(BigDecimal value) {
            addCriterion("CON_PRICE =", value, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceNotEqualTo(BigDecimal value) {
            addCriterion("CON_PRICE <>", value, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceGreaterThan(BigDecimal value) {
            addCriterion("CON_PRICE >", value, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CON_PRICE >=", value, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceLessThan(BigDecimal value) {
            addCriterion("CON_PRICE <", value, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CON_PRICE <=", value, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceIn(List<BigDecimal> values) {
            addCriterion("CON_PRICE in", values, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceNotIn(List<BigDecimal> values) {
            addCriterion("CON_PRICE not in", values, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CON_PRICE between", value1, value2, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CON_PRICE not between", value1, value2, "conPrice");
            return (Criteria) this;
        }

        public Criteria andConNumIsNull() {
            addCriterion("CON_NUM is null");
            return (Criteria) this;
        }

        public Criteria andConNumIsNotNull() {
            addCriterion("CON_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andConNumEqualTo(BigDecimal value) {
            addCriterion("CON_NUM =", value, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumNotEqualTo(BigDecimal value) {
            addCriterion("CON_NUM <>", value, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumGreaterThan(BigDecimal value) {
            addCriterion("CON_NUM >", value, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CON_NUM >=", value, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumLessThan(BigDecimal value) {
            addCriterion("CON_NUM <", value, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CON_NUM <=", value, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumIn(List<BigDecimal> values) {
            addCriterion("CON_NUM in", values, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumNotIn(List<BigDecimal> values) {
            addCriterion("CON_NUM not in", values, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CON_NUM between", value1, value2, "conNum");
            return (Criteria) this;
        }

        public Criteria andConNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CON_NUM not between", value1, value2, "conNum");
            return (Criteria) this;
        }

        public Criteria andTradeDateIsNull() {
            addCriterion("TRADE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTradeDateIsNotNull() {
            addCriterion("TRADE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTradeDateEqualTo(Date value) {
            addCriterion("TRADE_DATE =", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateNotEqualTo(Date value) {
            addCriterion("TRADE_DATE <>", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateGreaterThan(Date value) {
            addCriterion("TRADE_DATE >", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("TRADE_DATE >=", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateLessThan(Date value) {
            addCriterion("TRADE_DATE <", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateLessThanOrEqualTo(Date value) {
            addCriterion("TRADE_DATE <=", value, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateIn(List<Date> values) {
            addCriterion("TRADE_DATE in", values, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateNotIn(List<Date> values) {
            addCriterion("TRADE_DATE not in", values, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateBetween(Date value1, Date value2) {
            addCriterion("TRADE_DATE between", value1, value2, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andTradeDateNotBetween(Date value1, Date value2) {
            addCriterion("TRADE_DATE not between", value1, value2, "tradeDate");
            return (Criteria) this;
        }

        public Criteria andVdateIsNull() {
            addCriterion("VDATE is null");
            return (Criteria) this;
        }

        public Criteria andVdateIsNotNull() {
            addCriterion("VDATE is not null");
            return (Criteria) this;
        }

        public Criteria andVdateEqualTo(Date value) {
            addCriterion("VDATE =", value, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateNotEqualTo(Date value) {
            addCriterion("VDATE <>", value, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateGreaterThan(Date value) {
            addCriterion("VDATE >", value, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateGreaterThanOrEqualTo(Date value) {
            addCriterion("VDATE >=", value, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateLessThan(Date value) {
            addCriterion("VDATE <", value, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateLessThanOrEqualTo(Date value) {
            addCriterion("VDATE <=", value, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateIn(List<Date> values) {
            addCriterion("VDATE in", values, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateNotIn(List<Date> values) {
            addCriterion("VDATE not in", values, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateBetween(Date value1, Date value2) {
            addCriterion("VDATE between", value1, value2, "vdate");
            return (Criteria) this;
        }

        public Criteria andVdateNotBetween(Date value1, Date value2) {
            addCriterion("VDATE not between", value1, value2, "vdate");
            return (Criteria) this;
        }

        public Criteria andMdateIsNull() {
            addCriterion("MDATE is null");
            return (Criteria) this;
        }

        public Criteria andMdateIsNotNull() {
            addCriterion("MDATE is not null");
            return (Criteria) this;
        }

        public Criteria andMdateEqualTo(Date value) {
            addCriterion("MDATE =", value, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateNotEqualTo(Date value) {
            addCriterion("MDATE <>", value, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateGreaterThan(Date value) {
            addCriterion("MDATE >", value, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateGreaterThanOrEqualTo(Date value) {
            addCriterion("MDATE >=", value, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateLessThan(Date value) {
            addCriterion("MDATE <", value, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateLessThanOrEqualTo(Date value) {
            addCriterion("MDATE <=", value, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateIn(List<Date> values) {
            addCriterion("MDATE in", values, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateNotIn(List<Date> values) {
            addCriterion("MDATE not in", values, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateBetween(Date value1, Date value2) {
            addCriterion("MDATE between", value1, value2, "mdate");
            return (Criteria) this;
        }

        public Criteria andMdateNotBetween(Date value1, Date value2) {
            addCriterion("MDATE not between", value1, value2, "mdate");
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

        public Criteria andEntrustStatusIsNull() {
            addCriterion("ENTRUST_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusIsNotNull() {
            addCriterion("ENTRUST_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusEqualTo(String value) {
            addCriterion("ENTRUST_STATUS =", value, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusNotEqualTo(String value) {
            addCriterion("ENTRUST_STATUS <>", value, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusGreaterThan(String value) {
            addCriterion("ENTRUST_STATUS >", value, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusGreaterThanOrEqualTo(String value) {
            addCriterion("ENTRUST_STATUS >=", value, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusLessThan(String value) {
            addCriterion("ENTRUST_STATUS <", value, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusLessThanOrEqualTo(String value) {
            addCriterion("ENTRUST_STATUS <=", value, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusLike(String value) {
            addCriterion("ENTRUST_STATUS like", value, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusNotLike(String value) {
            addCriterion("ENTRUST_STATUS not like", value, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusIn(List<String> values) {
            addCriterion("ENTRUST_STATUS in", values, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusNotIn(List<String> values) {
            addCriterion("ENTRUST_STATUS not in", values, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusBetween(String value1, String value2) {
            addCriterion("ENTRUST_STATUS between", value1, value2, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustStatusNotBetween(String value1, String value2) {
            addCriterion("ENTRUST_STATUS not between", value1, value2, "entrustStatus");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceIsNull() {
            addCriterion("ENTRUST_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceIsNotNull() {
            addCriterion("ENTRUST_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_PRICE =", value, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceNotEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_PRICE <>", value, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceGreaterThan(BigDecimal value) {
            addCriterion("ENTRUST_PRICE >", value, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_PRICE >=", value, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceLessThan(BigDecimal value) {
            addCriterion("ENTRUST_PRICE <", value, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_PRICE <=", value, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceIn(List<BigDecimal> values) {
            addCriterion("ENTRUST_PRICE in", values, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceNotIn(List<BigDecimal> values) {
            addCriterion("ENTRUST_PRICE not in", values, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ENTRUST_PRICE between", value1, value2, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andEntrustPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ENTRUST_PRICE not between", value1, value2, "entrustPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceIsNull() {
            addCriterion("FULL_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andFullPriceIsNotNull() {
            addCriterion("FULL_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andFullPriceEqualTo(BigDecimal value) {
            addCriterion("FULL_PRICE =", value, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceNotEqualTo(BigDecimal value) {
            addCriterion("FULL_PRICE <>", value, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceGreaterThan(BigDecimal value) {
            addCriterion("FULL_PRICE >", value, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FULL_PRICE >=", value, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceLessThan(BigDecimal value) {
            addCriterion("FULL_PRICE <", value, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FULL_PRICE <=", value, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceIn(List<BigDecimal> values) {
            addCriterion("FULL_PRICE in", values, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceNotIn(List<BigDecimal> values) {
            addCriterion("FULL_PRICE not in", values, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FULL_PRICE between", value1, value2, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andFullPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FULL_PRICE not between", value1, value2, "fullPrice");
            return (Criteria) this;
        }

        public Criteria andPriceModeIsNull() {
            addCriterion("PRICE_MODE is null");
            return (Criteria) this;
        }

        public Criteria andPriceModeIsNotNull() {
            addCriterion("PRICE_MODE is not null");
            return (Criteria) this;
        }

        public Criteria andPriceModeEqualTo(String value) {
            addCriterion("PRICE_MODE =", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeNotEqualTo(String value) {
            addCriterion("PRICE_MODE <>", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeGreaterThan(String value) {
            addCriterion("PRICE_MODE >", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeGreaterThanOrEqualTo(String value) {
            addCriterion("PRICE_MODE >=", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeLessThan(String value) {
            addCriterion("PRICE_MODE <", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeLessThanOrEqualTo(String value) {
            addCriterion("PRICE_MODE <=", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeLike(String value) {
            addCriterion("PRICE_MODE like", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeNotLike(String value) {
            addCriterion("PRICE_MODE not like", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeIn(List<String> values) {
            addCriterion("PRICE_MODE in", values, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeNotIn(List<String> values) {
            addCriterion("PRICE_MODE not in", values, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeBetween(String value1, String value2) {
            addCriterion("PRICE_MODE between", value1, value2, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeNotBetween(String value1, String value2) {
            addCriterion("PRICE_MODE not between", value1, value2, "priceMode");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtIsNull() {
            addCriterion("ENTRUST_LEFT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtIsNotNull() {
            addCriterion("ENTRUST_LEFT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_LEFT_AMT =", value, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtNotEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_LEFT_AMT <>", value, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtGreaterThan(BigDecimal value) {
            addCriterion("ENTRUST_LEFT_AMT >", value, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_LEFT_AMT >=", value, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtLessThan(BigDecimal value) {
            addCriterion("ENTRUST_LEFT_AMT <", value, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ENTRUST_LEFT_AMT <=", value, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtIn(List<BigDecimal> values) {
            addCriterion("ENTRUST_LEFT_AMT in", values, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtNotIn(List<BigDecimal> values) {
            addCriterion("ENTRUST_LEFT_AMT not in", values, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ENTRUST_LEFT_AMT between", value1, value2, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andEntrustLeftAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ENTRUST_LEFT_AMT not between", value1, value2, "entrustLeftAmt");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityIsNull() {
            addCriterion("FROZEN_QUANTITY is null");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityIsNotNull() {
            addCriterion("FROZEN_QUANTITY is not null");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityEqualTo(BigDecimal value) {
            addCriterion("FROZEN_QUANTITY =", value, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityNotEqualTo(BigDecimal value) {
            addCriterion("FROZEN_QUANTITY <>", value, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityGreaterThan(BigDecimal value) {
            addCriterion("FROZEN_QUANTITY >", value, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FROZEN_QUANTITY >=", value, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityLessThan(BigDecimal value) {
            addCriterion("FROZEN_QUANTITY <", value, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FROZEN_QUANTITY <=", value, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityIn(List<BigDecimal> values) {
            addCriterion("FROZEN_QUANTITY in", values, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityNotIn(List<BigDecimal> values) {
            addCriterion("FROZEN_QUANTITY not in", values, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FROZEN_QUANTITY between", value1, value2, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andFrozenQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FROZEN_QUANTITY not between", value1, value2, "frozenQuantity");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedIsNull() {
            addCriterion("SETTLE_SPEED is null");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedIsNotNull() {
            addCriterion("SETTLE_SPEED is not null");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedEqualTo(Long value) {
            addCriterion("SETTLE_SPEED =", value, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedNotEqualTo(Long value) {
            addCriterion("SETTLE_SPEED <>", value, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedGreaterThan(Long value) {
            addCriterion("SETTLE_SPEED >", value, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedGreaterThanOrEqualTo(Long value) {
            addCriterion("SETTLE_SPEED >=", value, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedLessThan(Long value) {
            addCriterion("SETTLE_SPEED <", value, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedLessThanOrEqualTo(Long value) {
            addCriterion("SETTLE_SPEED <=", value, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedIn(List<Long> values) {
            addCriterion("SETTLE_SPEED in", values, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedNotIn(List<Long> values) {
            addCriterion("SETTLE_SPEED not in", values, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedBetween(Long value1, Long value2) {
            addCriterion("SETTLE_SPEED between", value1, value2, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andSettleSpeedNotBetween(Long value1, Long value2) {
            addCriterion("SETTLE_SPEED not between", value1, value2, "settleSpeed");
            return (Criteria) this;
        }

        public Criteria andValueDateIsNull() {
            addCriterion("VALUE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andValueDateIsNotNull() {
            addCriterion("VALUE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andValueDateEqualTo(Date value) {
            addCriterion("VALUE_DATE =", value, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateNotEqualTo(Date value) {
            addCriterion("VALUE_DATE <>", value, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateGreaterThan(Date value) {
            addCriterion("VALUE_DATE >", value, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateGreaterThanOrEqualTo(Date value) {
            addCriterion("VALUE_DATE >=", value, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateLessThan(Date value) {
            addCriterion("VALUE_DATE <", value, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateLessThanOrEqualTo(Date value) {
            addCriterion("VALUE_DATE <=", value, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateIn(List<Date> values) {
            addCriterion("VALUE_DATE in", values, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateNotIn(List<Date> values) {
            addCriterion("VALUE_DATE not in", values, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateBetween(Date value1, Date value2) {
            addCriterion("VALUE_DATE between", value1, value2, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDateNotBetween(Date value1, Date value2) {
            addCriterion("VALUE_DATE not between", value1, value2, "valueDate");
            return (Criteria) this;
        }

        public Criteria andValueDate2IsNull() {
            addCriterion("VALUE_DATE2 is null");
            return (Criteria) this;
        }

        public Criteria andValueDate2IsNotNull() {
            addCriterion("VALUE_DATE2 is not null");
            return (Criteria) this;
        }

        public Criteria andValueDate2EqualTo(Date value) {
            addCriterion("VALUE_DATE2 =", value, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2NotEqualTo(Date value) {
            addCriterion("VALUE_DATE2 <>", value, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2GreaterThan(Date value) {
            addCriterion("VALUE_DATE2 >", value, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2GreaterThanOrEqualTo(Date value) {
            addCriterion("VALUE_DATE2 >=", value, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2LessThan(Date value) {
            addCriterion("VALUE_DATE2 <", value, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2LessThanOrEqualTo(Date value) {
            addCriterion("VALUE_DATE2 <=", value, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2In(List<Date> values) {
            addCriterion("VALUE_DATE2 in", values, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2NotIn(List<Date> values) {
            addCriterion("VALUE_DATE2 not in", values, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2Between(Date value1, Date value2) {
            addCriterion("VALUE_DATE2 between", value1, value2, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andValueDate2NotBetween(Date value1, Date value2) {
            addCriterion("VALUE_DATE2 not between", value1, value2, "valueDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDateIsNull() {
            addCriterion("MATURITY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andMaturityDateIsNotNull() {
            addCriterion("MATURITY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andMaturityDateEqualTo(Date value) {
            addCriterion("MATURITY_DATE =", value, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateNotEqualTo(Date value) {
            addCriterion("MATURITY_DATE <>", value, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateGreaterThan(Date value) {
            addCriterion("MATURITY_DATE >", value, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateGreaterThanOrEqualTo(Date value) {
            addCriterion("MATURITY_DATE >=", value, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateLessThan(Date value) {
            addCriterion("MATURITY_DATE <", value, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateLessThanOrEqualTo(Date value) {
            addCriterion("MATURITY_DATE <=", value, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateIn(List<Date> values) {
            addCriterion("MATURITY_DATE in", values, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateNotIn(List<Date> values) {
            addCriterion("MATURITY_DATE not in", values, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateBetween(Date value1, Date value2) {
            addCriterion("MATURITY_DATE between", value1, value2, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDateNotBetween(Date value1, Date value2) {
            addCriterion("MATURITY_DATE not between", value1, value2, "maturityDate");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2IsNull() {
            addCriterion("MATURITY_DATE2 is null");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2IsNotNull() {
            addCriterion("MATURITY_DATE2 is not null");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2EqualTo(Date value) {
            addCriterion("MATURITY_DATE2 =", value, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2NotEqualTo(Date value) {
            addCriterion("MATURITY_DATE2 <>", value, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2GreaterThan(Date value) {
            addCriterion("MATURITY_DATE2 >", value, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2GreaterThanOrEqualTo(Date value) {
            addCriterion("MATURITY_DATE2 >=", value, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2LessThan(Date value) {
            addCriterion("MATURITY_DATE2 <", value, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2LessThanOrEqualTo(Date value) {
            addCriterion("MATURITY_DATE2 <=", value, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2In(List<Date> values) {
            addCriterion("MATURITY_DATE2 in", values, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2NotIn(List<Date> values) {
            addCriterion("MATURITY_DATE2 not in", values, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2Between(Date value1, Date value2) {
            addCriterion("MATURITY_DATE2 between", value1, value2, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andMaturityDate2NotBetween(Date value1, Date value2) {
            addCriterion("MATURITY_DATE2 not between", value1, value2, "maturityDate2");
            return (Criteria) this;
        }

        public Criteria andTermDaysIsNull() {
            addCriterion("TERM_DAYS is null");
            return (Criteria) this;
        }

        public Criteria andTermDaysIsNotNull() {
            addCriterion("TERM_DAYS is not null");
            return (Criteria) this;
        }

        public Criteria andTermDaysEqualTo(Long value) {
            addCriterion("TERM_DAYS =", value, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysNotEqualTo(Long value) {
            addCriterion("TERM_DAYS <>", value, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysGreaterThan(Long value) {
            addCriterion("TERM_DAYS >", value, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysGreaterThanOrEqualTo(Long value) {
            addCriterion("TERM_DAYS >=", value, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysLessThan(Long value) {
            addCriterion("TERM_DAYS <", value, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysLessThanOrEqualTo(Long value) {
            addCriterion("TERM_DAYS <=", value, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysIn(List<Long> values) {
            addCriterion("TERM_DAYS in", values, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysNotIn(List<Long> values) {
            addCriterion("TERM_DAYS not in", values, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysBetween(Long value1, Long value2) {
            addCriterion("TERM_DAYS between", value1, value2, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDaysNotBetween(Long value1, Long value2) {
            addCriterion("TERM_DAYS not between", value1, value2, "termDays");
            return (Criteria) this;
        }

        public Criteria andTermDays2IsNull() {
            addCriterion("TERM_DAYS2 is null");
            return (Criteria) this;
        }

        public Criteria andTermDays2IsNotNull() {
            addCriterion("TERM_DAYS2 is not null");
            return (Criteria) this;
        }

        public Criteria andTermDays2EqualTo(Long value) {
            addCriterion("TERM_DAYS2 =", value, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2NotEqualTo(Long value) {
            addCriterion("TERM_DAYS2 <>", value, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2GreaterThan(Long value) {
            addCriterion("TERM_DAYS2 >", value, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2GreaterThanOrEqualTo(Long value) {
            addCriterion("TERM_DAYS2 >=", value, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2LessThan(Long value) {
            addCriterion("TERM_DAYS2 <", value, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2LessThanOrEqualTo(Long value) {
            addCriterion("TERM_DAYS2 <=", value, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2In(List<Long> values) {
            addCriterion("TERM_DAYS2 in", values, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2NotIn(List<Long> values) {
            addCriterion("TERM_DAYS2 not in", values, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2Between(Long value1, Long value2) {
            addCriterion("TERM_DAYS2 between", value1, value2, "termDays2");
            return (Criteria) this;
        }

        public Criteria andTermDays2NotBetween(Long value1, Long value2) {
            addCriterion("TERM_DAYS2 not between", value1, value2, "termDays2");
            return (Criteria) this;
        }

        public Criteria andPurchTypeIsNull() {
            addCriterion("PURCH_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPurchTypeIsNotNull() {
            addCriterion("PURCH_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPurchTypeEqualTo(String value) {
            addCriterion("PURCH_TYPE =", value, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeNotEqualTo(String value) {
            addCriterion("PURCH_TYPE <>", value, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeGreaterThan(String value) {
            addCriterion("PURCH_TYPE >", value, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PURCH_TYPE >=", value, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeLessThan(String value) {
            addCriterion("PURCH_TYPE <", value, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeLessThanOrEqualTo(String value) {
            addCriterion("PURCH_TYPE <=", value, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeLike(String value) {
            addCriterion("PURCH_TYPE like", value, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeNotLike(String value) {
            addCriterion("PURCH_TYPE not like", value, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeIn(List<String> values) {
            addCriterion("PURCH_TYPE in", values, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeNotIn(List<String> values) {
            addCriterion("PURCH_TYPE not in", values, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeBetween(String value1, String value2) {
            addCriterion("PURCH_TYPE between", value1, value2, "purchType");
            return (Criteria) this;
        }

        public Criteria andPurchTypeNotBetween(String value1, String value2) {
            addCriterion("PURCH_TYPE not between", value1, value2, "purchType");
            return (Criteria) this;
        }

        public Criteria andSaleCodeIsNull() {
            addCriterion("SALE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andSaleCodeIsNotNull() {
            addCriterion("SALE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andSaleCodeEqualTo(String value) {
            addCriterion("SALE_CODE =", value, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeNotEqualTo(String value) {
            addCriterion("SALE_CODE <>", value, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeGreaterThan(String value) {
            addCriterion("SALE_CODE >", value, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SALE_CODE >=", value, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeLessThan(String value) {
            addCriterion("SALE_CODE <", value, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeLessThanOrEqualTo(String value) {
            addCriterion("SALE_CODE <=", value, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeLike(String value) {
            addCriterion("SALE_CODE like", value, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeNotLike(String value) {
            addCriterion("SALE_CODE not like", value, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeIn(List<String> values) {
            addCriterion("SALE_CODE in", values, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeNotIn(List<String> values) {
            addCriterion("SALE_CODE not in", values, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeBetween(String value1, String value2) {
            addCriterion("SALE_CODE between", value1, value2, "saleCode");
            return (Criteria) this;
        }

        public Criteria andSaleCodeNotBetween(String value1, String value2) {
            addCriterion("SALE_CODE not between", value1, value2, "saleCode");
            return (Criteria) this;
        }

        public Criteria andMinAmtIsNull() {
            addCriterion("MIN_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMinAmtIsNotNull() {
            addCriterion("MIN_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMinAmtEqualTo(BigDecimal value) {
            addCriterion("MIN_AMT =", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtNotEqualTo(BigDecimal value) {
            addCriterion("MIN_AMT <>", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtGreaterThan(BigDecimal value) {
            addCriterion("MIN_AMT >", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MIN_AMT >=", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtLessThan(BigDecimal value) {
            addCriterion("MIN_AMT <", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MIN_AMT <=", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtIn(List<BigDecimal> values) {
            addCriterion("MIN_AMT in", values, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtNotIn(List<BigDecimal> values) {
            addCriterion("MIN_AMT not in", values, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MIN_AMT between", value1, value2, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MIN_AMT not between", value1, value2, "minAmt");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdIsNull() {
            addCriterion("TAR_FINPROD_ID is null");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdIsNotNull() {
            addCriterion("TAR_FINPROD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdEqualTo(String value) {
            addCriterion("TAR_FINPROD_ID =", value, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdNotEqualTo(String value) {
            addCriterion("TAR_FINPROD_ID <>", value, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdGreaterThan(String value) {
            addCriterion("TAR_FINPROD_ID >", value, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdGreaterThanOrEqualTo(String value) {
            addCriterion("TAR_FINPROD_ID >=", value, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdLessThan(String value) {
            addCriterion("TAR_FINPROD_ID <", value, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdLessThanOrEqualTo(String value) {
            addCriterion("TAR_FINPROD_ID <=", value, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdLike(String value) {
            addCriterion("TAR_FINPROD_ID like", value, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdNotLike(String value) {
            addCriterion("TAR_FINPROD_ID not like", value, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdIn(List<String> values) {
            addCriterion("TAR_FINPROD_ID in", values, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdNotIn(List<String> values) {
            addCriterion("TAR_FINPROD_ID not in", values, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdBetween(String value1, String value2) {
            addCriterion("TAR_FINPROD_ID between", value1, value2, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarFinprodIdNotBetween(String value1, String value2) {
            addCriterion("TAR_FINPROD_ID not between", value1, value2, "tarFinprodId");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrIsNull() {
            addCriterion("TARINPROD_ABBR is null");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrIsNotNull() {
            addCriterion("TARINPROD_ABBR is not null");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrEqualTo(String value) {
            addCriterion("TARINPROD_ABBR =", value, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrNotEqualTo(String value) {
            addCriterion("TARINPROD_ABBR <>", value, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrGreaterThan(String value) {
            addCriterion("TARINPROD_ABBR >", value, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrGreaterThanOrEqualTo(String value) {
            addCriterion("TARINPROD_ABBR >=", value, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrLessThan(String value) {
            addCriterion("TARINPROD_ABBR <", value, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrLessThanOrEqualTo(String value) {
            addCriterion("TARINPROD_ABBR <=", value, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrLike(String value) {
            addCriterion("TARINPROD_ABBR like", value, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrNotLike(String value) {
            addCriterion("TARINPROD_ABBR not like", value, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrIn(List<String> values) {
            addCriterion("TARINPROD_ABBR in", values, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrNotIn(List<String> values) {
            addCriterion("TARINPROD_ABBR not in", values, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrBetween(String value1, String value2) {
            addCriterion("TARINPROD_ABBR between", value1, value2, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodAbbrNotBetween(String value1, String value2) {
            addCriterion("TARINPROD_ABBR not between", value1, value2, "tarinprodAbbr");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdIsNull() {
            addCriterion("TARINPROD_MARKET_ID is null");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdIsNotNull() {
            addCriterion("TARINPROD_MARKET_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdEqualTo(String value) {
            addCriterion("TARINPROD_MARKET_ID =", value, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdNotEqualTo(String value) {
            addCriterion("TARINPROD_MARKET_ID <>", value, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdGreaterThan(String value) {
            addCriterion("TARINPROD_MARKET_ID >", value, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdGreaterThanOrEqualTo(String value) {
            addCriterion("TARINPROD_MARKET_ID >=", value, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdLessThan(String value) {
            addCriterion("TARINPROD_MARKET_ID <", value, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdLessThanOrEqualTo(String value) {
            addCriterion("TARINPROD_MARKET_ID <=", value, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdLike(String value) {
            addCriterion("TARINPROD_MARKET_ID like", value, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdNotLike(String value) {
            addCriterion("TARINPROD_MARKET_ID not like", value, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdIn(List<String> values) {
            addCriterion("TARINPROD_MARKET_ID in", values, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdNotIn(List<String> values) {
            addCriterion("TARINPROD_MARKET_ID not in", values, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdBetween(String value1, String value2) {
            addCriterion("TARINPROD_MARKET_ID between", value1, value2, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTarinprodMarketIdNotBetween(String value1, String value2) {
            addCriterion("TARINPROD_MARKET_ID not between", value1, value2, "tarinprodMarketId");
            return (Criteria) this;
        }

        public Criteria andTradeIdIsNull() {
            addCriterion("TRADE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTradeIdIsNotNull() {
            addCriterion("TRADE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTradeIdEqualTo(String value) {
            addCriterion("TRADE_ID =", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotEqualTo(String value) {
            addCriterion("TRADE_ID <>", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdGreaterThan(String value) {
            addCriterion("TRADE_ID >", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdGreaterThanOrEqualTo(String value) {
            addCriterion("TRADE_ID >=", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdLessThan(String value) {
            addCriterion("TRADE_ID <", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdLessThanOrEqualTo(String value) {
            addCriterion("TRADE_ID <=", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdLike(String value) {
            addCriterion("TRADE_ID like", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotLike(String value) {
            addCriterion("TRADE_ID not like", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdIn(List<String> values) {
            addCriterion("TRADE_ID in", values, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotIn(List<String> values) {
            addCriterion("TRADE_ID not in", values, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdBetween(String value1, String value2) {
            addCriterion("TRADE_ID between", value1, value2, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotBetween(String value1, String value2) {
            addCriterion("TRADE_ID not between", value1, value2, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTraderNameIsNull() {
            addCriterion("TRADER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTraderNameIsNotNull() {
            addCriterion("TRADER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTraderNameEqualTo(String value) {
            addCriterion("TRADER_NAME =", value, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameNotEqualTo(String value) {
            addCriterion("TRADER_NAME <>", value, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameGreaterThan(String value) {
            addCriterion("TRADER_NAME >", value, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameGreaterThanOrEqualTo(String value) {
            addCriterion("TRADER_NAME >=", value, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameLessThan(String value) {
            addCriterion("TRADER_NAME <", value, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameLessThanOrEqualTo(String value) {
            addCriterion("TRADER_NAME <=", value, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameLike(String value) {
            addCriterion("TRADER_NAME like", value, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameNotLike(String value) {
            addCriterion("TRADER_NAME not like", value, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameIn(List<String> values) {
            addCriterion("TRADER_NAME in", values, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameNotIn(List<String> values) {
            addCriterion("TRADER_NAME not in", values, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameBetween(String value1, String value2) {
            addCriterion("TRADER_NAME between", value1, value2, "traderName");
            return (Criteria) this;
        }

        public Criteria andTraderNameNotBetween(String value1, String value2) {
            addCriterion("TRADER_NAME not between", value1, value2, "traderName");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdIsNull() {
            addCriterion("INV_MANAGER_ID is null");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdIsNotNull() {
            addCriterion("INV_MANAGER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdEqualTo(String value) {
            addCriterion("INV_MANAGER_ID =", value, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdNotEqualTo(String value) {
            addCriterion("INV_MANAGER_ID <>", value, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdGreaterThan(String value) {
            addCriterion("INV_MANAGER_ID >", value, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdGreaterThanOrEqualTo(String value) {
            addCriterion("INV_MANAGER_ID >=", value, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdLessThan(String value) {
            addCriterion("INV_MANAGER_ID <", value, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdLessThanOrEqualTo(String value) {
            addCriterion("INV_MANAGER_ID <=", value, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdLike(String value) {
            addCriterion("INV_MANAGER_ID like", value, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdNotLike(String value) {
            addCriterion("INV_MANAGER_ID not like", value, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdIn(List<String> values) {
            addCriterion("INV_MANAGER_ID in", values, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdNotIn(List<String> values) {
            addCriterion("INV_MANAGER_ID not in", values, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdBetween(String value1, String value2) {
            addCriterion("INV_MANAGER_ID between", value1, value2, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andInvManagerIdNotBetween(String value1, String value2) {
            addCriterion("INV_MANAGER_ID not between", value1, value2, "invManagerId");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNull() {
            addCriterion("ASSET_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNotNull() {
            addCriterion("ASSET_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeEqualTo(String value) {
            addCriterion("ASSET_TYPE =", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotEqualTo(String value) {
            addCriterion("ASSET_TYPE <>", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThan(String value) {
            addCriterion("ASSET_TYPE >", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ASSET_TYPE >=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThan(String value) {
            addCriterion("ASSET_TYPE <", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThanOrEqualTo(String value) {
            addCriterion("ASSET_TYPE <=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLike(String value) {
            addCriterion("ASSET_TYPE like", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotLike(String value) {
            addCriterion("ASSET_TYPE not like", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIn(List<String> values) {
            addCriterion("ASSET_TYPE in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotIn(List<String> values) {
            addCriterion("ASSET_TYPE not in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeBetween(String value1, String value2) {
            addCriterion("ASSET_TYPE between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotBetween(String value1, String value2) {
            addCriterion("ASSET_TYPE not between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetType1IsNull() {
            addCriterion("ASSET_TYPE1 is null");
            return (Criteria) this;
        }

        public Criteria andAssetType1IsNotNull() {
            addCriterion("ASSET_TYPE1 is not null");
            return (Criteria) this;
        }

        public Criteria andAssetType1EqualTo(String value) {
            addCriterion("ASSET_TYPE1 =", value, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1NotEqualTo(String value) {
            addCriterion("ASSET_TYPE1 <>", value, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1GreaterThan(String value) {
            addCriterion("ASSET_TYPE1 >", value, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1GreaterThanOrEqualTo(String value) {
            addCriterion("ASSET_TYPE1 >=", value, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1LessThan(String value) {
            addCriterion("ASSET_TYPE1 <", value, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1LessThanOrEqualTo(String value) {
            addCriterion("ASSET_TYPE1 <=", value, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1Like(String value) {
            addCriterion("ASSET_TYPE1 like", value, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1NotLike(String value) {
            addCriterion("ASSET_TYPE1 not like", value, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1In(List<String> values) {
            addCriterion("ASSET_TYPE1 in", values, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1NotIn(List<String> values) {
            addCriterion("ASSET_TYPE1 not in", values, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1Between(String value1, String value2) {
            addCriterion("ASSET_TYPE1 between", value1, value2, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType1NotBetween(String value1, String value2) {
            addCriterion("ASSET_TYPE1 not between", value1, value2, "assetType1");
            return (Criteria) this;
        }

        public Criteria andAssetType10IsNull() {
            addCriterion("ASSET_TYPE10 is null");
            return (Criteria) this;
        }

        public Criteria andAssetType10IsNotNull() {
            addCriterion("ASSET_TYPE10 is not null");
            return (Criteria) this;
        }

        public Criteria andAssetType10EqualTo(String value) {
            addCriterion("ASSET_TYPE10 =", value, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10NotEqualTo(String value) {
            addCriterion("ASSET_TYPE10 <>", value, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10GreaterThan(String value) {
            addCriterion("ASSET_TYPE10 >", value, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10GreaterThanOrEqualTo(String value) {
            addCriterion("ASSET_TYPE10 >=", value, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10LessThan(String value) {
            addCriterion("ASSET_TYPE10 <", value, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10LessThanOrEqualTo(String value) {
            addCriterion("ASSET_TYPE10 <=", value, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10Like(String value) {
            addCriterion("ASSET_TYPE10 like", value, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10NotLike(String value) {
            addCriterion("ASSET_TYPE10 not like", value, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10In(List<String> values) {
            addCriterion("ASSET_TYPE10 in", values, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10NotIn(List<String> values) {
            addCriterion("ASSET_TYPE10 not in", values, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10Between(String value1, String value2) {
            addCriterion("ASSET_TYPE10 between", value1, value2, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType10NotBetween(String value1, String value2) {
            addCriterion("ASSET_TYPE10 not between", value1, value2, "assetType10");
            return (Criteria) this;
        }

        public Criteria andAssetType11IsNull() {
            addCriterion("ASSET_TYPE11 is null");
            return (Criteria) this;
        }

        public Criteria andAssetType11IsNotNull() {
            addCriterion("ASSET_TYPE11 is not null");
            return (Criteria) this;
        }

        public Criteria andAssetType11EqualTo(String value) {
            addCriterion("ASSET_TYPE11 =", value, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11NotEqualTo(String value) {
            addCriterion("ASSET_TYPE11 <>", value, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11GreaterThan(String value) {
            addCriterion("ASSET_TYPE11 >", value, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11GreaterThanOrEqualTo(String value) {
            addCriterion("ASSET_TYPE11 >=", value, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11LessThan(String value) {
            addCriterion("ASSET_TYPE11 <", value, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11LessThanOrEqualTo(String value) {
            addCriterion("ASSET_TYPE11 <=", value, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11Like(String value) {
            addCriterion("ASSET_TYPE11 like", value, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11NotLike(String value) {
            addCriterion("ASSET_TYPE11 not like", value, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11In(List<String> values) {
            addCriterion("ASSET_TYPE11 in", values, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11NotIn(List<String> values) {
            addCriterion("ASSET_TYPE11 not in", values, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11Between(String value1, String value2) {
            addCriterion("ASSET_TYPE11 between", value1, value2, "assetType11");
            return (Criteria) this;
        }

        public Criteria andAssetType11NotBetween(String value1, String value2) {
            addCriterion("ASSET_TYPE11 not between", value1, value2, "assetType11");
            return (Criteria) this;
        }

        public Criteria andInvAimIsNull() {
            addCriterion("INV_AIM is null");
            return (Criteria) this;
        }

        public Criteria andInvAimIsNotNull() {
            addCriterion("INV_AIM is not null");
            return (Criteria) this;
        }

        public Criteria andInvAimEqualTo(String value) {
            addCriterion("INV_AIM =", value, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimNotEqualTo(String value) {
            addCriterion("INV_AIM <>", value, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimGreaterThan(String value) {
            addCriterion("INV_AIM >", value, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimGreaterThanOrEqualTo(String value) {
            addCriterion("INV_AIM >=", value, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimLessThan(String value) {
            addCriterion("INV_AIM <", value, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimLessThanOrEqualTo(String value) {
            addCriterion("INV_AIM <=", value, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimLike(String value) {
            addCriterion("INV_AIM like", value, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimNotLike(String value) {
            addCriterion("INV_AIM not like", value, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimIn(List<String> values) {
            addCriterion("INV_AIM in", values, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimNotIn(List<String> values) {
            addCriterion("INV_AIM not in", values, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimBetween(String value1, String value2) {
            addCriterion("INV_AIM between", value1, value2, "invAim");
            return (Criteria) this;
        }

        public Criteria andInvAimNotBetween(String value1, String value2) {
            addCriterion("INV_AIM not between", value1, value2, "invAim");
            return (Criteria) this;
        }

        public Criteria andApproverFlageIsNull() {
            addCriterion("APPROVER_FLAGE is null");
            return (Criteria) this;
        }

        public Criteria andApproverFlageIsNotNull() {
            addCriterion("APPROVER_FLAGE is not null");
            return (Criteria) this;
        }

        public Criteria andApproverFlageEqualTo(String value) {
            addCriterion("APPROVER_FLAGE =", value, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageNotEqualTo(String value) {
            addCriterion("APPROVER_FLAGE <>", value, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageGreaterThan(String value) {
            addCriterion("APPROVER_FLAGE >", value, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVER_FLAGE >=", value, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageLessThan(String value) {
            addCriterion("APPROVER_FLAGE <", value, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageLessThanOrEqualTo(String value) {
            addCriterion("APPROVER_FLAGE <=", value, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageLike(String value) {
            addCriterion("APPROVER_FLAGE like", value, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageNotLike(String value) {
            addCriterion("APPROVER_FLAGE not like", value, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageIn(List<String> values) {
            addCriterion("APPROVER_FLAGE in", values, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageNotIn(List<String> values) {
            addCriterion("APPROVER_FLAGE not in", values, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageBetween(String value1, String value2) {
            addCriterion("APPROVER_FLAGE between", value1, value2, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andApproverFlageNotBetween(String value1, String value2) {
            addCriterion("APPROVER_FLAGE not between", value1, value2, "approverFlage");
            return (Criteria) this;
        }

        public Criteria andEntrustBussType() {
            addCriterion("ENTRUST_BUSS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeIsNotNull() {
            addCriterion("ENTRUST_BUSS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeEqualTo(String value) {
            addCriterion("ENTRUST_BUSS_TYPE =", value, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeNotEqualTo(String value) {
            addCriterion("ENTRUST_BUSS_TYPE <>", value, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeGreaterThan(String value) {
            addCriterion("ENTRUST_BUSS_TYPE >", value, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ENTRUST_BUSS_TYPE >=", value, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeLessThan(String value) {
            addCriterion("ENTRUST_BUSS_TYPE <", value, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeLessThanOrEqualTo(String value) {
            addCriterion("ENTRUST_BUSS_TYPE <=", value, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeLike(String value) {
            addCriterion("ENTRUST_BUSS_TYPE like", value, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeNotLike(String value) {
            addCriterion("ENTRUST_BUSS_TYPE not like", value, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeIn(List<String> values) {
            addCriterion("ENTRUST_BUSS_TYPE in", values, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeNotIn(List<String> values) {
            addCriterion("ENTRUST_BUSS_TYPE not in", values, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeBetween(String value1, String value2) {
            addCriterion("ENTRUST_BUSS_TYPE between", value1, value2, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andEntrustBussTypeNotBetween(String value1, String value2) {
            addCriterion("ENTRUST_BUSS_TYPE not between", value1, value2, "entrustBussType");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethod() {
            addCriterion("DELIVERY_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodIsNotNull() {
            addCriterion("DELIVERY_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodEqualTo(String value) {
            addCriterion("DELIVERY_METHOD =", value, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodNotEqualTo(String value) {
            addCriterion("DELIVERY_METHOD <>", value, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodGreaterThan(String value) {
            addCriterion("DELIVERY_METHOD >", value, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodGreaterThanOrEqualTo(String value) {
            addCriterion("DELIVERY_METHOD >=", value, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodLessThan(String value) {
            addCriterion("DELIVERY_METHOD <", value, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodLessThanOrEqualTo(String value) {
            addCriterion("DELIVERY_METHOD <=", value, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodLike(String value) {
            addCriterion("DELIVERY_METHOD like", value, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodNotLike(String value) {
            addCriterion("DELIVERY_METHOD not like", value, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodIn(List<String> values) {
            addCriterion("DELIVERY_METHOD in", values, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodNotIn(List<String> values) {
            addCriterion("DELIVERY_METHOD not in", values, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodBetween(String value1, String value2) {
            addCriterion("DELIVERY_METHOD between", value1, value2, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andDeliveryMethodNotBetween(String value1, String value2) {
            addCriterion("DELIVERY_METHOD not between", value1, value2, "deliveryMethod");
            return (Criteria) this;
        }

        public Criteria andOurTraderIsNull() {
            addCriterion("OUR_TRADER is null");
            return (Criteria) this;
        }

        public Criteria andOurTraderIsNotNull() {
            addCriterion("OUR_TRADER is not null");
            return (Criteria) this;
        }

        public Criteria andOurTraderEqualTo(String value) {
            addCriterion("OUR_TRADER =", value, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderNotEqualTo(String value) {
            addCriterion("OUR_TRADER <>", value, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderGreaterThan(String value) {
            addCriterion("OUR_TRADER >", value, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderGreaterThanOrEqualTo(String value) {
            addCriterion("OUR_TRADER >=", value, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderLessThan(String value) {
            addCriterion("OUR_TRADER <", value, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderLessThanOrEqualTo(String value) {
            addCriterion("OUR_TRADER <=", value, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderLike(String value) {
            addCriterion("OUR_TRADER like", value, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderNotLike(String value) {
            addCriterion("OUR_TRADER not like", value, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderIn(List<String> values) {
            addCriterion("OUR_TRADER in", values, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderNotIn(List<String> values) {
            addCriterion("OUR_TRADER not in", values, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderBetween(String value1, String value2) {
            addCriterion("OUR_TRADER between", value1, value2, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andOurTraderNotBetween(String value1, String value2) {
            addCriterion("OUR_TRADER not between", value1, value2, "ourTrader");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveIsNull() {
            addCriterion("IS_AUTO_RECEIVE is null");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveIsNotNull() {
            addCriterion("IS_AUTO_RECEIVE is not null");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveEqualTo(String value) {
            addCriterion("IS_AUTO_RECEIVE =", value, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveNotEqualTo(String value) {
            addCriterion("IS_AUTO_RECEIVE <>", value, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveGreaterThan(String value) {
            addCriterion("IS_AUTO_RECEIVE >", value, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveGreaterThanOrEqualTo(String value) {
            addCriterion("IS_AUTO_RECEIVE >=", value, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveLessThan(String value) {
            addCriterion("IS_AUTO_RECEIVE <", value, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveLessThanOrEqualTo(String value) {
            addCriterion("IS_AUTO_RECEIVE <=", value, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveLike(String value) {
            addCriterion("IS_AUTO_RECEIVE like", value, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveNotLike(String value) {
            addCriterion("IS_AUTO_RECEIVE not like", value, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveIn(List<String> values) {
            addCriterion("IS_AUTO_RECEIVE in", values, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveNotIn(List<String> values) {
            addCriterion("IS_AUTO_RECEIVE not in", values, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveBetween(String value1, String value2) {
            addCriterion("IS_AUTO_RECEIVE between", value1, value2, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andIsAutoReceiveNotBetween(String value1, String value2) {
            addCriterion("IS_AUTO_RECEIVE not between", value1, value2, "isAutoReceive");
            return (Criteria) this;
        }

        public Criteria andVPayDateIsNull() {
            addCriterion("V_PAY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andVPayDateIsNotNull() {
            addCriterion("V_PAY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andVPayDateEqualTo(Date value) {
            addCriterion("V_PAY_DATE =", value, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateNotEqualTo(Date value) {
            addCriterion("V_PAY_DATE <>", value, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateGreaterThan(Date value) {
            addCriterion("V_PAY_DATE >", value, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateGreaterThanOrEqualTo(Date value) {
            addCriterion("V_PAY_DATE >=", value, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateLessThan(Date value) {
            addCriterion("V_PAY_DATE <", value, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateLessThanOrEqualTo(Date value) {
            addCriterion("V_PAY_DATE <=", value, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateIn(List<Date> values) {
            addCriterion("V_PAY_DATE in", values, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateNotIn(List<Date> values) {
            addCriterion("V_PAY_DATE not in", values, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateBetween(Date value1, Date value2) {
            addCriterion("V_PAY_DATE between", value1, value2, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andVPayDateNotBetween(Date value1, Date value2) {
            addCriterion("V_PAY_DATE not between", value1, value2, "vPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateIsNull() {
            addCriterion("M_PAY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andMPayDateIsNotNull() {
            addCriterion("M_PAY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andMPayDateEqualTo(Date value) {
            addCriterion("M_PAY_DATE =", value, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateNotEqualTo(Date value) {
            addCriterion("M_PAY_DATE <>", value, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateGreaterThan(Date value) {
            addCriterion("M_PAY_DATE >", value, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateGreaterThanOrEqualTo(Date value) {
            addCriterion("M_PAY_DATE >=", value, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateLessThan(Date value) {
            addCriterion("M_PAY_DATE <", value, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateLessThanOrEqualTo(Date value) {
            addCriterion("M_PAY_DATE <=", value, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateIn(List<Date> values) {
            addCriterion("M_PAY_DATE in", values, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateNotIn(List<Date> values) {
            addCriterion("M_PAY_DATE not in", values, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateBetween(Date value1, Date value2) {
            addCriterion("M_PAY_DATE between", value1, value2, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andMPayDateNotBetween(Date value1, Date value2) {
            addCriterion("M_PAY_DATE not between", value1, value2, "mPayDate");
            return (Criteria) this;
        }

        public Criteria andCapDaysIsNull() {
            addCriterion("CAP_DAYS is null");
            return (Criteria) this;
        }

        public Criteria andCapDaysIsNotNull() {
            addCriterion("CAP_DAYS is not null");
            return (Criteria) this;
        }

        public Criteria andCapDaysEqualTo(Long value) {
            addCriterion("CAP_DAYS =", value, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysNotEqualTo(Long value) {
            addCriterion("CAP_DAYS <>", value, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysGreaterThan(Long value) {
            addCriterion("CAP_DAYS >", value, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysGreaterThanOrEqualTo(Long value) {
            addCriterion("CAP_DAYS >=", value, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysLessThan(Long value) {
            addCriterion("CAP_DAYS <", value, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysLessThanOrEqualTo(Long value) {
            addCriterion("CAP_DAYS <=", value, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysIn(List<Long> values) {
            addCriterion("CAP_DAYS in", values, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysNotIn(List<Long> values) {
            addCriterion("CAP_DAYS not in", values, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysBetween(Long value1, Long value2) {
            addCriterion("CAP_DAYS between", value1, value2, "capDays");
            return (Criteria) this;
        }

        public Criteria andCapDaysNotBetween(Long value1, Long value2) {
            addCriterion("CAP_DAYS not between", value1, value2, "capDays");
            return (Criteria) this;
        }

        public Criteria andMIntAmtIsNull() {
            addCriterion("M_INT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMIntAmtIsNotNull() {
            addCriterion("M_INT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMIntAmtEqualTo(BigDecimal value) {
            addCriterion("M_INT_AMT =", value, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtNotEqualTo(BigDecimal value) {
            addCriterion("M_INT_AMT <>", value, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtGreaterThan(BigDecimal value) {
            addCriterion("M_INT_AMT >", value, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("M_INT_AMT >=", value, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtLessThan(BigDecimal value) {
            addCriterion("M_INT_AMT <", value, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("M_INT_AMT <=", value, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtIn(List<BigDecimal> values) {
            addCriterion("M_INT_AMT in", values, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtNotIn(List<BigDecimal> values) {
            addCriterion("M_INT_AMT not in", values, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("M_INT_AMT between", value1, value2, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMIntAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("M_INT_AMT not between", value1, value2, "mIntAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtIsNull() {
            addCriterion("M_TRADE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtIsNotNull() {
            addCriterion("M_TRADE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtEqualTo(BigDecimal value) {
            addCriterion("M_TRADE_AMT =", value, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtNotEqualTo(BigDecimal value) {
            addCriterion("M_TRADE_AMT <>", value, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtGreaterThan(BigDecimal value) {
            addCriterion("M_TRADE_AMT >", value, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("M_TRADE_AMT >=", value, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtLessThan(BigDecimal value) {
            addCriterion("M_TRADE_AMT <", value, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("M_TRADE_AMT <=", value, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtIn(List<BigDecimal> values) {
            addCriterion("M_TRADE_AMT in", values, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtNotIn(List<BigDecimal> values) {
            addCriterion("M_TRADE_AMT not in", values, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("M_TRADE_AMT between", value1, value2, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andMTradeAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("M_TRADE_AMT not between", value1, value2, "mTradeAmt");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsIsNull() {
            addCriterion("FIRST_REPO_PS is null");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsIsNotNull() {
            addCriterion("FIRST_REPO_PS is not null");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsEqualTo(String value) {
            addCriterion("FIRST_REPO_PS =", value, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsNotEqualTo(String value) {
            addCriterion("FIRST_REPO_PS <>", value, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsGreaterThan(String value) {
            addCriterion("FIRST_REPO_PS >", value, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsGreaterThanOrEqualTo(String value) {
            addCriterion("FIRST_REPO_PS >=", value, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsLessThan(String value) {
            addCriterion("FIRST_REPO_PS <", value, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsLessThanOrEqualTo(String value) {
            addCriterion("FIRST_REPO_PS <=", value, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsLike(String value) {
            addCriterion("FIRST_REPO_PS like", value, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsNotLike(String value) {
            addCriterion("FIRST_REPO_PS not like", value, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsIn(List<String> values) {
            addCriterion("FIRST_REPO_PS in", values, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsNotIn(List<String> values) {
            addCriterion("FIRST_REPO_PS not in", values, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsBetween(String value1, String value2) {
            addCriterion("FIRST_REPO_PS between", value1, value2, "firstRepoPs");
            return (Criteria) this;
        }

        public Criteria andFirstRepoPsNotBetween(String value1, String value2) {
            addCriterion("FIRST_REPO_PS not between", value1, value2, "firstRepoPs");
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