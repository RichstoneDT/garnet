/*
 * 广州丰石科技有限公司拥有本软件版权 2019-06-03 并保留所有权利。
 * Copyright 2019, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.richstonedt.garnet.model.criteria;

import java.util.ArrayList;
import java.util.List;

public class GroupUserCriteria {
    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected String orderByClause;

    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected boolean distinct;

    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected List<Criteria> oredCriteria;

    /**
     * TABLE： gar_group_users
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected Integer start;

    /**
     * TABLE： gar_group_users
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected Integer end;

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public GroupUserCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public Integer getStart() {
        return start;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * <br>
     *
     * TABLE： gar_group_users<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public Integer getEnd() {
        return end;
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_group_users
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Long value) {
            addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Long value) {
            addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Long value) {
            addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Long value) {
            addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Long value) {
            addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Long value) {
            addCriterion("group_id <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Long> values) {
            addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Long> values) {
            addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Long value1, Long value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Long value1, Long value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_group_users
     *
     * @mbg.generated 该代码为自动生成，请不要修改
     *
     * DATE: 2018-03-30 13:23
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_group_users
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
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