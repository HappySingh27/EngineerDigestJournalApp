package com.dehlan.Journal.Utilities;

import java.util.*;
import com.dehlan.Journal.entity.APIkeys;
import com.dehlan.Journal.repository.APIkeysRepository;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class APIcache {

    public Map<String,String> map = new LinkedHashMap<>();

    @Autowired
    APIkeysRepository apIkeysRepository;
    
    @PostConstruct
    public void getAPIkeys(){
        List<APIkeys> all = apIkeysRepository.findAll();

        map =
        all.stream().collect(Collectors.toMap(APIkeys::getApiName,APIkeys::getApikey));

    }
}
