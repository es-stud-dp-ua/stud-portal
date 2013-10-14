package ua.dp.stud.askQuestionPortlet.controller;

import org.apache.commons.lang.exception.NestableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.askQuestionPortlet.model.Question;
import ua.dp.stud.askQuestionPortlet.util.Mailer;
import ua.dp.stud.askQuestionPortlet.util.QuestionValidator;

import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;
import javax.validation.Valid;

/**
 * Ask question controller
 *
 * @author Oleg
 */
@Controller
@SessionAttributes(value = "question")
@RequestMapping(value = "VIEW")
public class AskQuestionController {
    @Autowired
    private Mailer mailer;

    /**
     * Sets the mailer
     *
     * @param mailer
     */
    public void setMailer(Mailer mailer) {
        this.mailer = mailer;
    }

    /**
     * Calls after form's submit button pressed
     *
     * @param question
     * @param bindingResult
     * @param response
     * @param sessionStatus
     */
    @ActionMapping
    public void doPost(@Valid @ModelAttribute("question") Question question, BindingResult bindingResult,
                       ActionResponse response, SessionStatus sessionStatus) throws Exception {
        Validator validator = new QuestionValidator();
        validator.validate(question, bindingResult);
        if (bindingResult.hasErrors()) {
            return;
        } else {
            synchronized (mailer) {
                mailer.sendMail(
                        question.getSentFrom(),
                        question.getSubject(),
                        question.getText(),
                        question.getSentFromName(),
                        question.getSentFromMobile());
            }
            sessionStatus.setComplete();
            response.setRenderParameter("result", "success");
        }
    }

    /**
     * Render incoming request to askQuestionForm.jsp
     *
     * @param response
     * @return
     */
    @RenderMapping
    public String doGet(RenderResponse response) {
        return "askQuestionForm";
    }

    /**
     * Renders to success_page.jsp
     *
     * @param model
     * @return
     */
    @RenderMapping(params = "result=success")
    public String successPage(Model model) {
        return "success_page";
    }

    /**
     * Returns empty question object
     *
     * @return
     */
    @ModelAttribute(value = "question")
    public Question getCommandObject() {
        return new Question();
    }
}
