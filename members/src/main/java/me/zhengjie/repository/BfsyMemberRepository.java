/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.repository;


import me.zhengjie.domain.BfsyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @website https://el-admin.vip
* @author weiyien
* @date 2021-03-13
**/
public interface BfsyMemberRepository extends JpaRepository<BfsyMember, Long>, JpaSpecificationExecutor<BfsyMember> {
    /**
    * 根据 Username 查询
    * @param username /
    * @return /
    */
    BfsyMember findByUsername(String username);
    /**
    * 根据 Email 查询
    * @param email /
    * @return /
    */
    BfsyMember findByEmail(String email);
    /**
     * 根据 phone 查询
     * @param phone /
     * @return /
     */
    BfsyMember findByPhone(String phone);
}