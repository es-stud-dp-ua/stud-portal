package ua.dp.stud.aboutTeam.controller;

import jodd.io.FileUtil;
import jodd.io.NetUtil;
import jodd.jerry.Jerry;
import jodd.util.SystemUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.dp.stud.aboutTeam.model.Human;
import ua.dp.stud.aboutTeam.service.HumanService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * About team controller
 *
 * @author Tradunsky V.V.
 */
@Controller
@RequestMapping(value = "view")
public class AboutTeamController {
    private static final String TESTER = "tester";
    private static final String SOFT_ENG = "Software Engineer";
    private static final String NO_PHOTO = "/images/no_photo.jpg";
    private static final List<String> BA_URLS;
    private static final List<String> DEV_URLS;
    private static final List<String> TESTER_URLS;
    private static final List<String> COMPANY_URLS;
    private static final Integer REFRESH_RATE = 7;
    private static final Integer TOTAL_MS_PER_DAY = 24 * 60 * 60 * 1000;
    private static Logger log = Logger.getLogger(AboutTeamController.class.getName());

    @Autowired
    @Qualifier(value = "humanService")
    private HumanService humanService;

    public void setHumanService(HumanService humanService) {
        this.humanService = humanService;
    }

    static {
        //BA linkedIn urls
        BA_URLS = Arrays.asList("http://ua.linkedin.com/pub/aleksey-filyaev/47/457/aaa");
        //Developers linkedIn urls
        DEV_URLS = Arrays.asList("http://ua.linkedin.com/pub/ivan-kamyshan/61/5a0/255/en",
                "http://ua.linkedin.com/pub/vyacheslav-tradunsky/59/983/779",
                "http://ua.linkedin.com/pub/igor-lapko/6b/646/630", "http://www.linkedin.com/in/vlablack",
                "http://www.linkedin.com/pub/gorbunov-dmitriy/6b/646/a35",
                "http://ua.linkedin.com/pub/olga-ryzol/6b/647/a80");
        //Testers linkedIn urls
        TESTER_URLS = Arrays.asList("http://ua.linkedin.com/pub/elena-onischenko/6b/626/674",
                "http://ua.linkedin.com/pub/olga-goryanaya/54/819/722",
                "http://www.linkedin.com/pub/tatyana-chudopalova/6b/736/b2a",
                "http://ua.linkedin.com/pub/%D0%B0%D0%BD%D0%BD%D0%B0-%D0%B8%D0%B2%D0%B0%D1%85%D0%BD%D0%B8%D0%BA/4a/975/809",
                "http://ua.linkedin.com/pub/marina-khodakovskaya/25/7b9/92b");
        //Companies linkedIn urls
        COMPANY_URLS = Arrays.asList("http://ua.linkedin.com/pub/tatyana-bulanaya/38/13b/424/",
                "http://ua.linkedin.com/in/tamplier", "http://ru.linkedin.com/in/arsart");
    }

    /**
     * Render incoming request to aboutTeamList.jsp
     *
     * @return dafault view
     */
    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {

        String contextPath = request.getContextPath().toString();
        String photoUrl = contextPath + NO_PHOTO;
        List<Human> baTeam = getUsersFromLinkedIn(BA_URLS, photoUrl);
        List<Human> devTeam = getUsersFromLinkedIn(DEV_URLS, photoUrl);
        List<Human> testersTeam = getUsersFromLinkedIn(TESTER_URLS, photoUrl);
        List<Human> companyTeam = getUsersFromLinkedIn(COMPANY_URLS, photoUrl);
        //static members
        //todo: also should be static
        List<Human> tnxTeam = Arrays.asList(new Human("Elena", "Onishenko", TESTER, contextPath + "/images/elena-onishenko.jpg", "http://vk.com/elenaonishhenk0"),
                new Human("Tatyana", "Podgornaya", TESTER, contextPath + "/images/tanya-podgornaya.jpg", "http://www.facebook.com/tatyana.podgornaya.10"),
                new Human("Oksana", "Schur", TESTER, contextPath + "/images/oksana-shur.jpg", "http://vk.com/id21246119"),
                new Human("Ilya", "Solomyanikov", TESTER, contextPath + "/images/ilya-solomyanikov.jpg", "https://www.facebook.com/ilya.solomyanikov"),
                new Human("Konstantin", "Gavrilchenko", SOFT_ENG, contextPath + "/images/kostya-gavrilchenko.jpg", ""),
                new Human("Sergey", "Alekseenko", SOFT_ENG, contextPath + "/images/no_photo.jpg", ""),
                new Human("Anastasiia", "Shatorna", "QA", contextPath + "/images/anastasiia-shatorna.jpg", "http://vk.com/id14291951"),
                new Human("Anton", "Batiuk", "Product Specialist", contextPath + "/images/anton-batiuk.jpg", "https://plus.google.com/u/1/113264243001342224417/posts"),
                new Human("Alexey", "Duzhy", SOFT_ENG, contextPath + "/images/alex-duzhy.jpg", "http://vk.com/id26434691"),
                new Human("Oleg", "Novikov", SOFT_ENG, contextPath + "/images/oleg-novikov.jpg", "https://www.facebook.com/lplyr"),
                new Human("Maxim", "Kulishev", SOFT_ENG, contextPath + "/images/no_photo.jpg", "http://ua.linkedin.com/pub/max-kulishev/64/2b7/632"),
                new Human("Roman", "Lukas", SOFT_ENG, contextPath + "/images/Roman-Lukash.jpg", "http://vk.com/id153906964"));

        ModelAndView model = new ModelAndView("aboutTeamList");
        model.addObject("baTeam", baTeam);
        model.addObject("devTeam", devTeam);
        model.addObject("testersTeam", testersTeam);
        model.addObject("companyTeam", companyTeam);
        model.addObject("tnxTeam", tnxTeam);
        return model;
    }

    private List<Human> getUsersFromLinkedIn(List<String> urls, String noImage) {
        List<Human> userList = new ArrayList<Human>();
        // download the page super-efficiently
        for (int i = 0; i < urls.size(); i++) {
            Human human = humanService.getByUrl(urls.get(i));
            Date now = DateTime.now().toDate();
            if (human == null) {
                human = new Human();
                if (loadUser(human, urls.get(i), i, noImage)) {
                    human.setDate(now);
                    humanService.addHuman(human);
                } else {
                    continue;
                }
            } else {
                Long difference = now.getTime() - human.getDate().getTime();
                //todo: use long
                Integer days = (int) (difference / TOTAL_MS_PER_DAY);
                if (days >= REFRESH_RATE) {
                    if (loadUser(human, urls.get(i), i, noImage)) {
                        human.setDate(now);
                        humanService.updateHuman(human);
                    } else {
                        continue;
                    }
                }
            }
            userList.add(human);
        }
        return userList;
    }

    private Boolean loadUser(Human human, String url, Integer id, String noImage) {
        String str;
        File file = new File(SystemUtil.getTempDir(), "team" + id + ".html");
        try {
            NetUtil.downloadFile(url, file);
            // create Jerry, i.e. document context
            //todo: remove assignment to null
            Jerry doc = null;
            doc = Jerry.jerry(FileUtil.readString(file));
            human.setFirstName(doc.$(".given-name").text());
            human.setLastName(doc.$(".family-name").text());
            String status = doc.$(".headline-title.title").text().replaceAll("  ", "");
            human.setStatus(status.replaceAll("\n|\r\n", ""));
            human.setUrl(url);
            str = doc.$(".photo").attr("src");
            if (str != null) {
                if (str.equals("") || (str.contains("spacer.gif"))) {
                    str = noImage;
                }
                human.setPhotoUrl(str);
            } else {
                human.setPhotoUrl(noImage);
            }
        } catch (IOException e) {
            //if page is not loaded, just take a new page
            log.log(Level.SEVERE, "Exception: ", e);
            return false;
        }
        return true;
    }
}
