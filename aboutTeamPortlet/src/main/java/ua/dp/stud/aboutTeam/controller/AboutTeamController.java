package ua.dp.stud.aboutTeam.controller;

import jodd.io.FileUtil;
import jodd.io.NetUtil;
import jodd.jerry.Jerry;
import jodd.util.SystemUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.dp.stud.aboutTeam.model.Human;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * About team controller
 * @author Tradunsky V.V.
 */
@Controller
@RequestMapping(value = "view")
public class AboutTeamController {

    /**
     * Render incoming request to aboutTeamList.jsp
     * @return dafault view
     */
    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        List<String> baurls = new ArrayList<String>();
        List<String> devurls = new ArrayList<String>();
        List<String> testersurls = new ArrayList<String>();
        List<String> companyurls = new ArrayList<String>();

        //TODO: think about adding
        //team linkedin list
        //add new team here
        //ba
        baurls.add("http://ua.linkedin.com/pub/aleksey-filyaev/47/457/aaa");
        //developers
        devurls.add("http://ua.linkedin.com/pub/ivan-kamyshan/61/5a0/255/en");
        devurls.add("http://ua.linkedin.com/pub/vyacheslav-tradunsky/59/983/779");
        devurls.add("http://ua.linkedin.com/pub/igor-lapko/6b/646/630");
        devurls.add("http://www.linkedin.com/in/vlablack");
        devurls.add("http://www.linkedin.com/pub/gorbunov-dmitriy/6b/646/a35");
        devurls.add("http://ua.linkedin.com/pub/olga-ryzol/6b/647/a80");
        //testers count==testersCount!
        testersurls.add("http://ua.linkedin.com/pub/elena-onischenko/6b/626/674");
        testersurls.add("http://ua.linkedin.com/pub/olga-goryanaya/54/819/722");
        testersurls.add("http://www.linkedin.com/pub/tatyana-chudopalova/6b/736/b2a");
        testersurls.add("http://ua.linkedin.com/pub/%D0%B0%D0%BD%D0%BD%D0%B0-%D0%B8%D0%B2%D0%B0%D1%85%D0%BD%D0%B8%D0%BA/4a/975/809");
        testersurls.add("http://ua.linkedin.com/pub/marina-khodakovskaya/25/7b9/92b");

        //company count==expertsCount!
        companyurls.add("http://ua.linkedin.com/pub/tatyana-bulanaya/38/13b/424/");
        companyurls.add("http://ua.linkedin.com/in/tamplier");
        companyurls.add("http://ru.linkedin.com/in/arsart");

        //TODO: why did you use all this "toString"?
        List<Human> bateam =getUsersFromLinkedIn(baurls,
                request.getContextPath().toString()+"/images/no_photo.jpg");
        List<Human> devteam =getUsersFromLinkedIn(devurls,
                request.getContextPath().toString()+"/images/no_photo.jpg");
        List<Human> testersteam =getUsersFromLinkedIn(testersurls,
                request.getContextPath().toString()+"/images/no_photo.jpg");
        List<Human> companyteam =getUsersFromLinkedIn(companyurls,
                request.getContextPath().toString()+"/images/no_photo.jpg");
        List<Human> tnxteam = new ArrayList<Human>();
        //static members
        //tnx for watching

        testersteam.add(addHuman("Elena","Onishenko","tester",request.getContextPath().toString()+"/images/elena-onishenko.jpg",
                "http://vk.com/elenaonishhenk0"));
        testersteam.add(addHuman("Tatyana", "Podgornaya", "tester", request.getContextPath().toString() + "/images/tanya-podgornaya.jpg",
                "http://www.facebook.com/tatyana.podgornaya.10"));
        testersteam.add(addHuman("Oksana","Schur","tester",request.getContextPath().toString()+"/images/oksana-shur.jpg",
                "http://vk.com/id21246119"));
        testersteam.add(addHuman("Ilya","Solomyanikov","tester",request.getContextPath().toString()+"/images/ilya-solomyanikov.jpg",
                "https://www.facebook.com/ilya.solomyanikov"));

        companyteam.add(addHuman("Konstantin", "Gavrilchenko", "Software Engineer", request.getContextPath().toString() + "/images/kostya-gavrilchenko.jpg",
                ""));
        companyteam.add(addHuman("Sergey","Alekseenko","Software Engineer",request.getContextPath().toString()+"/images/no_photo.jpg",
                ""));
        companyteam.add(addHuman("Anastasiia","Shatorna","QA",request.getContextPath().toString()+"/images/anastasiia-shatorna.jpg",
                "http://vk.com/id14291951"));
        bateam.add(addHuman("Anton", "Batiuk", "Product Specialist", request.getContextPath().toString() + "/images/anton-batiuk.jpg",
                "https://plus.google.com/u/1/113264243001342224417/posts"));
        tnxteam.add(addHuman("Alexey", "Duzhy", "Software Engineer", request.getContextPath().toString() + "/images/alex-duzhy.jpg",
                "http://vk.com/id26434691"));
        devteam.add(addHuman("Oleg", "Novikov", "Software Engineer", request.getContextPath().toString() + "/images/oleg-novikov.jpg",
                "https://www.facebook.com/lplyr"));
        tnxteam.add(addHuman("Maxim", "Kulishev", "Software Engineer", request.getContextPath().toString() + "/images/no_photo.jpg",
                "http://ua.linkedin.com/pub/max-kulishev/64/2b7/632"));
        tnxteam.add(addHuman("Roman","Lukas","Software Engineer",request.getContextPath().toString()+"/images/Roman-Lukash.jpg",
                "http://vk.com/id153906964"));

        model.setViewName("aboutTeamList");
        model.addObject("baTeam", bateam);
        model.addObject("devTeam", devteam);
        model.addObject("testersTeam", testersteam);
        model.addObject("companyTeam", companyteam);
        model.addObject("tnxTeam", tnxteam);
        return model;
    }
    //TODO: use fullnames for variables
    private Human addHuman(String fname, String lname, String status, String photoUrl, String url){
        Human sm = new Human();
        sm.setFirstName(fname);
        sm.setLastName(lname);
        sm.setStatus(status);
        sm.setPhotoUrl(photoUrl);
        sm.setUrl(url);
        return sm;
    }

    private List<Human> getUsersFromLinkedIn(List<String> urls, String noImage){
        List<Human> userList = new ArrayList<Human>();
        //TODO: use "human" instead of "h"
        Human h=null;
        String str = null;
        //TODO: wtf is ls?
        int ls=0;
        // download the page super-efficiently
        File file;
        for (int i = ls; i < urls.size(); i++) {
                file = new File(SystemUtil.getTempDir(), "team"+i+".html");
                h = new Human();
                try {
                NetUtil.downloadFile(urls.get(i), file);
                // create Jerry, i.e. document context
                Jerry doc = null;
                doc = Jerry.jerry(FileUtil.readString(file));
                h.setFirstName(doc.$(".given-name").text());
                h.setLastName(doc.$(".family-name").text());
                String status = doc.$(".headline-title.title").text().replaceAll("  ","");
                    //TODO: wtf?
                if (status.length()>20) {
                    status = status.substring(0, 17).toString()+"...";
                }
                h.setStatus(status);
                h.setUrl(urls.get(i));
                str = new String();
                str =  doc.$(".photo").attr("src");
                if (str!=null){
                    if (str.equals("")||(str.contains("spacer.gif"))) str=noImage;
                    h.setPhotoUrl(str);
                }
                else h.setPhotoUrl(noImage);
                userList.add(h);
                } catch (IOException e) {
                    ++ls;
                }
            }
            return userList;
    }

}
