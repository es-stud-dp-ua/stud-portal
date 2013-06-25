package ua.dp.stud.askQuestionPortlet.controller;

import com.liferay.util.portlet.PortletProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.askQuestionPortlet.model.MailChanger;
import ua.dp.stud.askQuestionPortlet.util.EmailValidator;
import ua.dp.stud.askQuestionPortlet.util.Mailer;

import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Edit mail controller
 * @author Alex
 */
@Controller(value = "editMailController")
@RequestMapping(value = "EDIT")
@SessionAttributes(value = "newmail")
//TODO: rename class
public class EditMailContoller
{
    @Autowired
    private Mailer mailer;

    /**
     * Render request to edit.jsp
     * @param response
     * @return
     */
    @RenderMapping
    public ModelAndView showEditPage(RenderResponse response)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("curmail", PortletProps.get("admin_mail"));
        return new ModelAndView("edit", map);
    }

    /**
     * trying to save new mail to properties. if done render request to editSuccess.jsp
     * @param response
     * @param mailChanger
     */
    @ActionMapping
    public void saveNewMail(ActionResponse response,
                            @Valid @ModelAttribute("mailChanger") MailChanger mailChanger,
                            BindingResult bindingResult)
    {
        Validator validator = new EmailValidator();
        validator.validate(mailChanger,bindingResult);
        if (bindingResult.hasErrors())
        {
            return;
        }
        if (mailChanger.getNewMail().length() > 0)
        {
            mailer.setNewMail(mailChanger.getNewMail());
            response.setRenderParameter("result","editsuccess");
        }
    }

    /**
     * Render request to editSuccess.jsp
     * @param response
     * @return
     */
    @RenderMapping(params = "result=editsuccess")
    public String editionSuccessPage(RenderResponse response)
    {
        return "editSuccess";
    }

    /**
     * Returns model attribute - mail changer bean
     * @return
     */
    @ModelAttribute(value = "mailChanger")
    public MailChanger getCommandObject() {
        return new MailChanger();
    }
}
