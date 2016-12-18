package com.bezPalevaServer.Controllers;

import com.bezPalevaServer.Services.MarkService;
import com.bezPalevaServer.Services.MarksScheduler;
import com.bezPalevaServer.Services.SystemParameters;
import com.bezPalevaServer.Services.UserService;
import com.bezPalevaServer.db.Mark;
import com.bezPalevaServer.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


@RestController
public class MarkController {

    @Autowired
    MarkService markService;
    @Autowired
    UserService userService;
    @Autowired
    SystemParameters systemParameters;

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
        String userId = params.get("userId");

        if(x == null || y == null || type == null || userId == null) return  null;
        else {
            Calendar deathTime = Calendar.getInstance();
            deathTime.add(Calendar.HOUR, systemParameters.getDeathTimeSize());

            User user = userService.getUserFromDB(Long.parseLong(userId));

            Mark mark = new Mark(Double.parseDouble(x), Double.parseDouble(y), type, description, deathTime.getTimeInMillis(), user);

            if(photoFile != null) markService.createPhotoFile(photoFile, mark, null);

            return markService.addMarkInDB(mark);
        }
    }


    @RequestMapping(value = "/changeMark", method = RequestMethod.POST)
    public Mark changeMark(@RequestParam Map<String, String> params, MultipartFile photoFile) throws IOException {

        String id = params.get("id");
        String description = params.get("desc");
        String type = params.get("type");
        String photoPath = params.get("photo");
        String irrelevance = params.get("irrel");
        String userId = params.get("userId");

        if (id != null){
            Mark mark = markService.getMarkFromDB(Long.parseLong(id));

            if (mark != null) {

                if (description != null) mark.setDescription(description);
                if (type != null) mark.setType(type);
                if (photoFile != null) markService.createPhotoFile(photoFile, mark, photoPath);

                if (irrelevance != null) mark.incIrrelevanceLevel();
                return markService.addMarkInDB(mark);
            }
        }
        return null;
    }

    @RequestMapping(value = "/photo", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(@RequestParam("path") String path) throws IOException {

        InputStream is = new FileInputStream(path);
        BufferedImage img = ImageIO.read(is);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(img,"jpg", bos);
        return bos.toByteArray();
    }

}
