package site.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import site.facade.UserService;
import site.model.Speaker;

import java.util.List;

@Controller
public class SpeakerController {
    private static final Logger logger = LogManager.getLogger(SpeakerController.class);

   @Autowired
   private UserService userService;

   @RequestMapping("/speakers")
   public String speakers(Pageable pageable, Model model) {
      List<Speaker> acceptedSpeakers = userService.findAcceptedSpeakers();
      Page<Speaker> speakers = new PageImpl<Speaker>(acceptedSpeakers, pageable, acceptedSpeakers.size());
      model.addAttribute("speakers", speakers);

      model.addAttribute("tags", userService.findAllTags());

      return "/speakers.jsp";
   }

   //read a single blog
   @RequestMapping("/speaker/{id}")
   public String getById(@PathVariable("id") final long id, Model model) {
      Speaker speaker = userService.findSpeaker(id);
        if (speaker == null) {
            logger.error(String.format("Invalid speaker id (%1$d)", id));
            return "/404.jsp";
        }
      if(speaker.getAccepted()) {
         model.addAttribute("speaker", speaker);
      }

      model.addAttribute("tags", userService.findAllTags());
      return "/speaker.jsp";
   }
}
