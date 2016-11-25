package com.bezPalevaServer.Controllers;

import com.bezPalevaServer.Services.MarkService;
import com.bezPalevaServer.Services.MarksScheduler;
import com.bezPalevaServer.db.Mark;
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

        if(x == null || y == null || type==null) return  null;
        else {
            Calendar deathTime = Calendar.getInstance();
            deathTime.add(Calendar.HOUR, markService.getDeathTimeSize());

            Mark mark = new Mark(Double.parseDouble(x), Double.parseDouble(y), type, description, deathTime.getTimeInMillis());

            if(photoFile != null){
                File file = new File("C:/photos/"+ photoFile.hashCode() + photoFile.getOriginalFilename());
                photoFile.transferTo(file);
                mark.setPhoto_path(file.getAbsolutePath());
            }
            return markService.addMarkInDB(mark);
        }
    }

    @RequestMapping(value = "/changeMark", method = RequestMethod.POST)
    public Mark changeMark(@RequestParam Map<String, String> params, MultipartFile photoFile) throws IOException {

        String id = params.get("id");
        String description = params.get("desc");
        String photoPath = params.get("photo");
        String irrelevance = params.get("irrel");

        if (id != null){
            Mark mark = markService.getMarkFromDB(Long.parseLong(id));

            if (mark != null) {

                if (description != null) mark.setDescription(description);
                if (photoFile != null) {
                    File file;
                    if (photoPath != null) file = new File(photoPath);
                    else file = new File("C:/photos/" + photoFile.hashCode() + photoFile.getOriginalFilename());
                    photoFile.transferTo(file);
                    mark.setPhoto_path(file.getAbsolutePath());
                }
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
