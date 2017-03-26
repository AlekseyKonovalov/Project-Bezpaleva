package com.bezPalevaServer.Controllers;


import com.bezPalevaServer.Services.Decryptor;
import com.bezPalevaServer.Services.MarkService;
import com.bezPalevaServer.db.MarkRepository;
import com.bezPalevaServer.db.SysParamRepository;
import com.bezPalevaServer.db.SystemParameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


@RestController
public class AdminController {

    @Autowired
    SysParamRepository sysParamRepository;

    @Autowired
    MarkService markService;

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public SystemParameters changeSysParam (@RequestParam Map<String, String> params) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {

        Decryptor decryptor = new Decryptor();

        String secretKey = params.get("secretKey");

        if(!decryptor.checkSecretKey(secretKey))  return null;

        SystemParameters systemParameters = sysParamRepository.findOne(0);

        String deathTimeSize = params.get("deathTimeSize");
        String irrelevanceLevelMax = params.get("irrelevanceLevelMax");
        String maxNumberMarksPerDay = params.get("maxNumberMarksPerDay");

        if(deathTimeSize != null) systemParameters.setDeathTimeSize(Integer.parseInt(deathTimeSize));
        if(irrelevanceLevelMax != null) systemParameters.setIrrelevanceLevelMax(Integer.parseInt(irrelevanceLevelMax));
        if(maxNumberMarksPerDay != null) systemParameters.setMaxNumberMarksPerDay(Integer.parseInt(maxNumberMarksPerDay));

        return sysParamRepository.save(systemParameters);
    }


    @RequestMapping(value = "/deleteMark", method = RequestMethod.POST)
    public void deleteMark (@RequestParam Map<String, String> params) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IOException, IllegalBlockSizeException {

        Decryptor decryptor = new Decryptor();

        String secretKey = params.get("secretKey");
        String id = params.get("idMark");

        if(decryptor.checkSecretKey(secretKey) && id != null)
            markService.deletemMarkIbBD(Long.parseLong(id));
    }


}
