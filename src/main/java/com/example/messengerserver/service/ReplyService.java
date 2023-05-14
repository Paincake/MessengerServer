package com.example.messengerserver.service;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Form;
import com.example.messengerserver.entity.Reply;
import com.example.messengerserver.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public void replyToForm(Reply reply){
        replyRepository.save(reply);
    }
    public void deleteReply(Long replyId){
        if(replyRepository.findById(replyId).isPresent()) replyRepository.deleteById(replyId);
    }
    public List<Reply> findAllRepliesByUser(AuthUser user){
        return replyRepository.findRepliesByRepliedUser(user);
    }

    public Reply findReplyById(Long replyId) {
        return replyRepository.findById(replyId).orElse(null);
    }
}
