package ua.dp.stud.studie.controller;

import java.util.Collection;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;



import ua.dp.stud.StudPortalLib.util.ImageService;
import ua.dp.stud.studie.model.Grant;
import ua.dp.stud.studie.service.GrantService;

@Controller(value = "GrantController")
@RequestMapping(value = "VIEW")
public class GrantController {


	@Autowired
	@Qualifier(value = "grantService")
	private GrantService grantService;

	public void setGrantService(GrantService grantService) {
		this.grantService = grantService;
	}

	@ModelAttribute(value = "grant")
	public Grant getGrantCommandObject() {
		return new Grant();
	}


	@Autowired
	@Qualifier(value = "imageService")
	private ImageService imageService;

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@RenderMapping(params = "view=allGrants")
	public ModelAndView showView(RenderRequest request, RenderResponse response) {
		Grant w = new Grant("Название","Город","Страна","Специальность","Квалификация","Название","Название","Название","Названиемфвыфвыфвыммф"
        		+ "паывапыввапваыпывапывапывапвыапыпывапыпыпаыппавыпвыапваыпвыапсчмсчмбьт"
        		+ "апавыпыапвамсчтмбьсичмчмячы"
        		+ "пывапывпывапваыпырвпорфыолдпрфпдфмтбьтып"
        		+ "пыпаыпыпыапывапывапывпыпы","Названиемфвыфвыфвыммф"
                		+ "паывапыввапваыпывапывапывапвыапыпывапыпыпаыппавыпвыапваыпвыапсчмсчмбьт"
                		+ "апавыпыапвамсчтмбьсичмчмячы"
                		+ "пывапывпывапваыпырвпорфыолдпрфпдфмтбьтып"
                		+ "пыпаыпыпыапывапывапывпыпы");
		grantService.addGrant(w);
		return getMainView();
	}

	private ModelAndView getMainView() {
		ModelAndView model = new ModelAndView("viewAllGrants");
		Collection<Grant> grants = grantService.getAllGrants();
		model.addObject("grants", grants);
		return model;
	}
	
	@RenderMapping(params = "view=singleGrant")
    public ModelAndView viewSingleGrant(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewSingleGrant");
        Integer id = Integer.parseInt(request.getParameter("id"));
        Grant grant = grantService.getGrantById(id);
        model.addObject("grant",grant);
        System.out.println(grant);
        return model;
    }



}