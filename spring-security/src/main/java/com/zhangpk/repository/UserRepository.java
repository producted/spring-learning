package com.zhangpk.repository;

import com.zhangpk.model.UserDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By zhangpk On 2019/6/17
 **/
@Repository
public interface UserRepository extends CrudRepository<UserDO, Long> {

    List<UserDO> findByUsername(String username);
}
