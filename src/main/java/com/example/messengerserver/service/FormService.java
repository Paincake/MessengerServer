package com.example.messengerserver.service;

import com.example.messengerserver.entity.Form;
import com.example.messengerserver.entity.Reply;
import com.example.messengerserver.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;

    public List<Form> findAllForms(){
        return formRepository.findAll();
    }

    public void submitForm(Form form){
        formRepository.save(form);
    }

    public List<Reply> findAllRepliesToForm(Long formId){
        Form form = formRepository.findById(formId).orElse(null);
        if(form != null){
            return form.getReplyList();
        }
        else return null;
    }
    public void deleteForm(Long formId){
        if(formRepository.existsById(formId)) formRepository.deleteById(formId);
    }
    public Form findFormById(Long formId){
        return formRepository.findById(formId).orElse(null);
    }
}
