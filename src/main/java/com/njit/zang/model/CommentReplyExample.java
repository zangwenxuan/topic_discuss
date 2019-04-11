package com.njit.zang.model;

import java.util.ArrayList;
import java.util.List;

public class CommentReplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public CommentReplyExample() {
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

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
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

        public Criteria andCommentReplyIdIsNull() {
            addCriterion("comment_reply_id is null");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdIsNotNull() {
            addCriterion("comment_reply_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdEqualTo(Integer value) {
            addCriterion("comment_reply_id =", value, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdNotEqualTo(Integer value) {
            addCriterion("comment_reply_id <>", value, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdGreaterThan(Integer value) {
            addCriterion("comment_reply_id >", value, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_reply_id >=", value, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdLessThan(Integer value) {
            addCriterion("comment_reply_id <", value, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("comment_reply_id <=", value, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdIn(List<Integer> values) {
            addCriterion("comment_reply_id in", values, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdNotIn(List<Integer> values) {
            addCriterion("comment_reply_id not in", values, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdBetween(Integer value1, Integer value2) {
            addCriterion("comment_reply_id between", value1, value2, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andCommentReplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_reply_id not between", value1, value2, "commentReplyId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIsNull() {
            addCriterion("from_user_id is null");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIsNotNull() {
            addCriterion("from_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromUserIdEqualTo(String value) {
            addCriterion("from_user_id =", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotEqualTo(String value) {
            addCriterion("from_user_id <>", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdGreaterThan(String value) {
            addCriterion("from_user_id >", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("from_user_id >=", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdLessThan(String value) {
            addCriterion("from_user_id <", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdLessThanOrEqualTo(String value) {
            addCriterion("from_user_id <=", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdLike(String value) {
            addCriterion("from_user_id like", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotLike(String value) {
            addCriterion("from_user_id not like", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIn(List<String> values) {
            addCriterion("from_user_id in", values, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotIn(List<String> values) {
            addCriterion("from_user_id not in", values, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdBetween(String value1, String value2) {
            addCriterion("from_user_id between", value1, value2, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotBetween(String value1, String value2) {
            addCriterion("from_user_id not between", value1, value2, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdIsNull() {
            addCriterion("to_user_id is null");
            return (Criteria) this;
        }

        public Criteria andToUserIdIsNotNull() {
            addCriterion("to_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andToUserIdEqualTo(String value) {
            addCriterion("to_user_id =", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdNotEqualTo(String value) {
            addCriterion("to_user_id <>", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdGreaterThan(String value) {
            addCriterion("to_user_id >", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("to_user_id >=", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdLessThan(String value) {
            addCriterion("to_user_id <", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdLessThanOrEqualTo(String value) {
            addCriterion("to_user_id <=", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdLike(String value) {
            addCriterion("to_user_id like", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdNotLike(String value) {
            addCriterion("to_user_id not like", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdIn(List<String> values) {
            addCriterion("to_user_id in", values, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdNotIn(List<String> values) {
            addCriterion("to_user_id not in", values, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdBetween(String value1, String value2) {
            addCriterion("to_user_id between", value1, value2, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdNotBetween(String value1, String value2) {
            addCriterion("to_user_id not between", value1, value2, "toUserId");
            return (Criteria) this;
        }

        public Criteria andRepConIsNull() {
            addCriterion("rep_con is null");
            return (Criteria) this;
        }

        public Criteria andRepConIsNotNull() {
            addCriterion("rep_con is not null");
            return (Criteria) this;
        }

        public Criteria andRepConEqualTo(String value) {
            addCriterion("rep_con =", value, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConNotEqualTo(String value) {
            addCriterion("rep_con <>", value, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConGreaterThan(String value) {
            addCriterion("rep_con >", value, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConGreaterThanOrEqualTo(String value) {
            addCriterion("rep_con >=", value, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConLessThan(String value) {
            addCriterion("rep_con <", value, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConLessThanOrEqualTo(String value) {
            addCriterion("rep_con <=", value, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConLike(String value) {
            addCriterion("rep_con like", value, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConNotLike(String value) {
            addCriterion("rep_con not like", value, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConIn(List<String> values) {
            addCriterion("rep_con in", values, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConNotIn(List<String> values) {
            addCriterion("rep_con not in", values, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConBetween(String value1, String value2) {
            addCriterion("rep_con between", value1, value2, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepConNotBetween(String value1, String value2) {
            addCriterion("rep_con not between", value1, value2, "repCon");
            return (Criteria) this;
        }

        public Criteria andRepTimeIsNull() {
            addCriterion("rep_time is null");
            return (Criteria) this;
        }

        public Criteria andRepTimeIsNotNull() {
            addCriterion("rep_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepTimeEqualTo(Long value) {
            addCriterion("rep_time =", value, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeNotEqualTo(Long value) {
            addCriterion("rep_time <>", value, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeGreaterThan(Long value) {
            addCriterion("rep_time >", value, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("rep_time >=", value, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeLessThan(Long value) {
            addCriterion("rep_time <", value, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeLessThanOrEqualTo(Long value) {
            addCriterion("rep_time <=", value, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeIn(List<Long> values) {
            addCriterion("rep_time in", values, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeNotIn(List<Long> values) {
            addCriterion("rep_time not in", values, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeBetween(Long value1, Long value2) {
            addCriterion("rep_time between", value1, value2, "repTime");
            return (Criteria) this;
        }

        public Criteria andRepTimeNotBetween(Long value1, Long value2) {
            addCriterion("rep_time not between", value1, value2, "repTime");
            return (Criteria) this;
        }

        public Criteria andCommentIdIsNull() {
            addCriterion("comment_id is null");
            return (Criteria) this;
        }

        public Criteria andCommentIdIsNotNull() {
            addCriterion("comment_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommentIdEqualTo(Integer value) {
            addCriterion("comment_id =", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotEqualTo(Integer value) {
            addCriterion("comment_id <>", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdGreaterThan(Integer value) {
            addCriterion("comment_id >", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_id >=", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdLessThan(Integer value) {
            addCriterion("comment_id <", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdLessThanOrEqualTo(Integer value) {
            addCriterion("comment_id <=", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdIn(List<Integer> values) {
            addCriterion("comment_id in", values, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotIn(List<Integer> values) {
            addCriterion("comment_id not in", values, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdBetween(Integer value1, Integer value2) {
            addCriterion("comment_id between", value1, value2, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_id not between", value1, value2, "commentId");
            return (Criteria) this;
        }

        public Criteria andRepTypeIsNull() {
            addCriterion("rep_type is null");
            return (Criteria) this;
        }

        public Criteria andRepTypeIsNotNull() {
            addCriterion("rep_type is not null");
            return (Criteria) this;
        }

        public Criteria andRepTypeEqualTo(Integer value) {
            addCriterion("rep_type =", value, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeNotEqualTo(Integer value) {
            addCriterion("rep_type <>", value, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeGreaterThan(Integer value) {
            addCriterion("rep_type >", value, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("rep_type >=", value, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeLessThan(Integer value) {
            addCriterion("rep_type <", value, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeLessThanOrEqualTo(Integer value) {
            addCriterion("rep_type <=", value, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeIn(List<Integer> values) {
            addCriterion("rep_type in", values, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeNotIn(List<Integer> values) {
            addCriterion("rep_type not in", values, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeBetween(Integer value1, Integer value2) {
            addCriterion("rep_type between", value1, value2, "repType");
            return (Criteria) this;
        }

        public Criteria andRepTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("rep_type not between", value1, value2, "repType");
            return (Criteria) this;
        }
    }

    /**
     */
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