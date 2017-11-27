package com.winter.core.userservice.adapter.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.winter.core.userservice.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{
	
		public List<Message> findByUserUserIdAndGroupGroupId(Long userId, Long groupId);

}