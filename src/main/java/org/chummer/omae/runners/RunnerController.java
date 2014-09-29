package org.chummer.omae.runners;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.chummer.omae.users.Chummer;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RunnerController {
	
	private Logger log = Logger.getLogger(RunnerController.class);

    @RequestMapping(value="/runner", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a Chummer5 xml file by posting to this same URL. It must be in Campaign Mode.";
    }

	@Secured("ROLE_PLAYERS")
    @RequestMapping(value="/runner/upload", method=RequestMethod.POST)
    public @ResponseBody Chummer handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("/Users/jflo/Documents/omae/"+name + "-uploaded.chum5")));
                stream.write(bytes);
                stream.close();
                return new Chummer();
            } catch (Exception e) {
            	log.error("couldn't write file", e);
            	return new Chummer();
            }
        } else {
        	return new Chummer();
        }
    }

}