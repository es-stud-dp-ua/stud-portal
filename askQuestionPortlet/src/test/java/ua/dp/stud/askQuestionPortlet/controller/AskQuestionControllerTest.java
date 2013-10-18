package ua.dp.stud.askQuestionPortlet.controller;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang.exception.NestableException;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.support.SimpleSessionStatus;
import ua.dp.stud.askQuestionPortlet.model.Question;
import ua.dp.stud.askQuestionPortlet.util.Mailer;

import javax.portlet.RenderResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class AskQuestionControllerTest {
    private AskQuestionController askQuestionController;
    private Question question;
    private BindException bindException;

    @Before
    public void setUp() {
        Mailer mailer = mock(Mailer.class);
        askQuestionController = new AskQuestionController();
        askQuestionController.setMailer(mailer);
        question = new Question();
        bindException = new BindException(question, "question");
    }

    @Test
    public void doGet() {
        RenderResponse response = new MockRenderResponse();
        assertEquals("askQuestionForm", askQuestionController.doGet(response));
    }

    @Test
    public void doPostValidateCorrectValues() throws Exception{
        question.setSentFrom("gainwardoleg@gmail.com");
        question.setSentFromName("Test Test");
        question.setSentFromMobile("380000000000");
        question.setText("test");
        question.setSubject("subject");
        MockActionResponse actionResponse = new MockActionResponse();
        askQuestionController.doPost(question, bindException, actionResponse, new SimpleSessionStatus());
        assertEquals("success", actionResponse.getRenderParameter("result"));
        assertEquals(0, bindException.getErrorCount());
    }

    @Test
    public void doPostValidateIncorrectValues() throws Exception{
        question.setSentFrom("dude");
        question.setSentFromName("");
        question.setSentFromMobile("");
        question.setText("");
        question.setSubject("");
        MockActionResponse actionResponse = new MockActionResponse();
        askQuestionController.doPost(question, bindException, actionResponse, new SimpleSessionStatus());
        // 2 required fields in contact admin form, that's why 2
        assertEquals(2, bindException.getErrorCount());
    }
}
