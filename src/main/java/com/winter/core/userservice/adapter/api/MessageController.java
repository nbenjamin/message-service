package com.winter.core.userservice.adapter.api;

import com.winter.core.userservice.adapter.repository.jpa.GroupRepository;
import com.winter.core.userservice.adapter.repository.jpa.MessageRepository;
import com.winter.core.userservice.adapter.repository.jpa.UserRepository;
import com.winter.core.userservice.domain.CoreException;
import com.winter.core.userservice.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/core/message")
public class MessageController {
	
	@Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/user/{userId}/group/{groupId}/sendMessage")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message, @PathVariable("userId")
            Long userId, @PathVariable("groupId") Long groupId) {
    	message.setUser(this.userRepository.findById(userId).get());
    	message.setGroup(this.groupRepository.findById(groupId).get());
        return Optional.ofNullable(messageRepository.save(message)).map(g ->new ResponseEntity<Message>
                (g, HttpStatus.CREATED) ).orElseThrow(() -> new CoreException("Unable to create " +
                "message now, please try again"));
    }

    @GetMapping("/{userId}/{groupId}")
    public List<Message> receiveMessage(@PathVariable("userId")
    Long userId, @PathVariable("groupId") Long groupId) {
    	List<Message> ls=messageRepository.findByUserUserIdAndGroupGroupId(userId, groupId);	
        return  Optional.ofNullable(ls)
               .orElseThrow(() -> new CoreException("Unable to find message"));
    
    }
	
}
