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
import org.springframework.web.servlet.ModelAndView;


import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


@RestController
public class AdminController {

    @Autowired
    SysParamRepository sysParamRepository;

    @Autowired
    MarkService markService;

    @RequestMapping("/settings")
    public ModelAndView configureSysParam (){

        SystemParameters systemParameters = sysParamRepository.findOne(0);

        ModelAndView settingsView = new ModelAndView("settings","systemParam",systemParameters);

        return settingsView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String changeSysParam (@RequestParam Map<String, String> params) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        SystemParameters systemParameters = sysParamRepository.findOne(0);

        String secretPass = params.get("secretPass");

        if(!secretPass.equals("")) return "Ошибка";

        String deathTimeSize = params.get("deathTimeSize");
        String irrelevanceLevelMax = params.get("irrelevanceLevelMax");
        String maxNumberMarksPerDay = params.get("maxNumberMarksPerDay");

        if(deathTimeSize != null) systemParameters.setDeathTimeSize(Integer.parseInt(deathTimeSize));
        if(irrelevanceLevelMax != null) systemParameters.setIrrelevanceLevelMax(Integer.parseInt(irrelevanceLevelMax));
        if(maxNumberMarksPerDay != null) systemParameters.setMaxNumberMarksPerDay(Integer.parseInt(maxNumberMarksPerDay));

        sysParamRepository.save(systemParameters);

        return "Системные параметры изменены!";
    }


    @RequestMapping(value = "/deleteMark", method = RequestMethod.POST)
    public void deleteMark (@RequestParam Map<String, String> params) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        String id = params.get("idMark");
        String key = params.get("key");

        if(id != null && key.equals("07@7ko23GWEsp@#"))
            markService.deletemMarkIbBD(Long.parseLong(id));
    }


}
