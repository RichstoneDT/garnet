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
package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role, RoleCriteria, Long> {
    Role selectSingleByCriteria(RoleCriteria criteria);

    int insertBatchSelective(List<Role> records);

    int updateBatchByPrimaryKeySelective(List<Role> records);
}
