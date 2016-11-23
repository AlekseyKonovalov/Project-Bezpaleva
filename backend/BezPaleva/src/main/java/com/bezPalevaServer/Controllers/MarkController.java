package com.bezPalevaServer.Controllers;

import com.bezPalevaServer.Services.MarkService;
import com.bezPalevaServer.db.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


@RestController
public class MarkController {

    @Autowired
    MarkService markService;

    @RequestMapping(value = "/mark", method = RequestMethod.GET)
    public ArrayList<Mark> getMarks (@RequestParam Map<String, String> params)  {

        String x = params.get("x");
        String y = params.get("y");
        String radius = params.get("rad");

        if (x == null || y== null|| radius == null) return null;
        else
        return markService.getMarksByRadius(Double.parseDouble(x), Double.parseDouble(y), Integer.parseInt(radius));
    }

    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    public  Mark addMark (@RequestParam Map<String, String> params, MultipartFile photoFile) throws IOException {

        String x = params.get("x");
        String y = params.get("y");
        String type = params.get("type");
        String description = params.get("desc");
        String photo = null;


        if(x == null || y == null || type==null) return  null;
        else {
            Mark mark = new Mark(Double.parseDouble(x), Double.parseDouble(y), type, description, photo, 654l, 0);
            return markService.addMarkInDB(mark);
        }
    }

}
