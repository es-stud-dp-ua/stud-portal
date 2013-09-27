package ua.dp.stud.createAccount.controller;

import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.createAccount.model.UserInfo;
import ua.dp.stud.createAccount.util.Hash;
import ua.dp.stud.createAccount.util.Mailer;
import ua.dp.stud.createAccount.util.UserInfoValidator;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY;

/**
 * Create account controller
 *
 * @author Tradunsky Vyacheslav
 * @author Vladislav Pikus
 */
@Controller
@SessionAttributes(value = "userInfo")
@RequestMapping(value = "VIEW")
public class AccountController {

    private static String strExcept = "exception";
    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    //index of rules
    private static final int RULESACEPT = 6;
    private static final int DEFAULT_ROLE_ID = 10142;
    private static final String EXEPTION = "Exception: ";

    @Autowired
    private Mailer mailer;

    public void setMailer(Mailer mailer) {
        this.mailer = mailer;
    }

    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        return new ModelAndView("create_account");
    }

    @RenderMapping(params = "result=success")
    public String successPage(Model model) {
        return "success_page";
    }

    @RenderMapping(params = "mode=activate")
    public ModelAndView showAddUser(RenderRequest request, RenderResponse response)
            throws PortalException, IOException, SystemException {
        ModelAndView model = new ModelAndView();
        //choose activation result
        if (confirmNewUser(request)) {
            model.setViewName("activation");
        } else {
            model.setViewName("activationFail");
        }
        //set view for user activation result
        return model;
    }

    /**
     * Exception rendering messege
     *
     * @param request  rendering request
     * @param response rendering response
     * @return return current view with exception message
     */
    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = showView(request, response);
        SessionErrors.add(request, request.getParameter(strExcept));
        return model;
    }

    @ModelAttribute(value = "userInfo")
    public UserInfo getCommandObject() {
        return new UserInfo();
    }

    /**
     * Adding new user in database using liferay user service.
     *
     * @param userInfo       new user's info
     * @param bindingResult  validation for new user info
     * @param actionRequest  request from registration form
     * @param actionResponse request response for registration form
     * @param sessionStatus  session work for registration form
     * @param image          new user portret
     * @throws IOException     will be happen if some information about new user is
     *                         wrong
     * @throws SystemException will be happen if server is not available
     * @throws PortalException will be happen if liferay portal is not available
     */
    @ActionMapping(value = "addNewUser")
    public void addNewUser(@Valid @ModelAttribute("userInfo") UserInfo userInfo,
                           BindingResult bindingResult,
                           ActionRequest actionRequest,
                           ActionResponse actionResponse,
                           SessionStatus sessionStatus,
                           @RequestParam("portret") CommonsMultipartFile image)
            throws SystemException, PortalException {
        UserInfoValidator validator = new UserInfoValidator();
        //validate necessary information

        validator.necessaryFields(userInfo, bindingResult);
        if (bindingResult.hasErrors()) {
            //wrong data
            actionResponse.setRenderParameter(strExcept, "error.data");
            return;
        }
        //activation link setup
        HttpServletRequest request = PortalUtil.getHttpServletRequest(actionRequest);
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(THEME_DISPLAY);
        String portletId = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
        request = PortalUtil.getOriginalServletRequest(request);
        //Retrieve layout id of another portlet
        long plid = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), portletId);
        PortletURL activationURL = PortletURLFactoryUtil.create(request, portletId, plid, PortletRequest.RENDER_PHASE);
        //sets other fields
        boolean autoPassword = false;
        boolean autoScreenName = true;
        boolean male = true;
        long[] groupIds = null;
        long[] organizationIds = null;
        long[] roleIds = {DEFAULT_ROLE_ID};
        long[] userGroupIds = null;
        boolean sendEmail = true;
        //create variable for user expando fields
        Map<String, String> hashOfUserExpando = new HashMap<String, String>();

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        //get date for calendar view from views
        Date birthday = null;
        Calendar calendar = GregorianCalendar.getInstance();
        //todo: if birthday param is invalid then just skip it or render same view with error message
        try {
            birthday = df.parse(ParamUtil.getString(actionRequest, "birthday"));
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
            birthday = new Date();
        }
        calendar.setTime(birthday);
        //get company id
        long companyId = themeDisplay.getCompany().getCompanyId();
        long creatorId = themeDisplay.getUser().getUserId();
        Locale locale = themeDisplay.getLocale();
        //get current context
        ServiceContext serviceContext = ServiceContextFactory.getInstance(User.class.getName(), actionRequest);

        //save expando
        hashOfUserExpando.put("placeOfStudy", userInfo.getPlaceOfStudy());
        hashOfUserExpando.put("faculty", userInfo.getFaculty());
        hashOfUserExpando.put("group", userInfo.getGroup());
        hashOfUserExpando.put("vkUid", userInfo.getVkontakteId());
        hashOfUserExpando.put("faceBook", userInfo.getFacebookId());
        hashOfUserExpando.put("about", userInfo.getAboutMe());
        hashOfUserExpando.put("rules", (userInfo.isRules()) ? "true" : "false");
        User user = null;

        try {
            //new user creating
            user = UserLocalServiceUtil.addUser(creatorId, companyId, autoPassword,
                    userInfo.getPassword1(), userInfo.getPassword2(),
                    autoScreenName, "", userInfo.getEmailAddress(), 0, "",
                    locale, userInfo.getFirstName(), "",
                    userInfo.getLastName(), 0, 0, male,
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                    calendar.get(Calendar.YEAR), "", groupIds, organizationIds,
                    roleIds, userGroupIds, sendEmail, serviceContext);
            //user unactivated
            UserLocalServiceUtil.updateActive(user.getUserId(), false);
            //set backup for current PermissionChecker
            PermissionChecker permissionCheckerBackup = PermissionThreadLocal.getPermissionChecker();
            //set new PermissionChecker
            PermissionThreadLocal.setPermissionChecker(getAdministratorPermissionChecker(companyId));
            //create expando fields
            if (createExpandoFields(user.getExpandoBridge())) {
                //sets expando fields
                setExpandoFields(user.getExpandoBridge(), hashOfUserExpando);
            }
            //set old PermissionChecker
            PermissionThreadLocal.setPermissionChecker(permissionCheckerBackup);
        } catch (DuplicateUserEmailAddressException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
            actionResponse.setRenderParameter(strExcept, "error.email");
            return;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
            actionResponse.setRenderParameter(strExcept, "error.global");
            return;
        }
        //todo: use on try-block
        try {
            //update new user Portrait
            if (image.getSize() > 0) {
                UserLocalServiceUtil.updatePortrait(user.getUserId(), image.getBytes());
            }
        } catch (Exception ex) {
            /* when error happen while upload image then dont do nothing! because user is all ready created*/
        }
        //activation url for encryption
        StringBuilder hashStr = new StringBuilder();
        hashStr.append("id=").append(String.valueOf(user.getUserId()))
                .append("&emailAddress=").append(user.getEmailAddress());
        Hash hash = new Hash();
        String hashedStr = hash.getCrypt(hashStr.toString());
        if (hashedStr.isEmpty()) {
            actionResponse.setRenderParameter(strExcept, "error.global");
            return;
        }
        //rendering setting
        activationURL.setParameter("hash", hashedStr);
        //rendering params
        activationURL.setParameter("mode", "activate");
        //send activation message on new user mail
        synchronized (mailer) {
            mailer.sendActivationMail(userInfo.getEmailAddress(),
                    activationURL.toString());
        }
        //complete session
        sessionStatus.setComplete();
        //send success message
        actionResponse.setRenderParameter("result", "success");
    }

    //helper methods
    private boolean confirmNewUser(RenderRequest actionRequest)
            throws IOException, SystemException, PortalException {
        //hash functions object
        Hash hash = new Hash();
        //get activation hash
        String hashStr = hash.getDecrypt(ParamUtil.getString(actionRequest, "hash"));
        //split activation hash
        String[] hashSplit = hashStr.split("&");
        //create variable for attributes
        Map<String, String> hashMap = new HashMap<String, String>();
        //get all attributes
        for (String hashObj : hashSplit) {
            String[] buff = hashObj.split("=");
            hashMap.put(buff[0], buff[1]);
        }
        //get new user id
        int userId = Integer.parseInt(hashMap.get("id"));
        //try to active new user
        try {
            UserLocalServiceUtil.updateActive(userId, true);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
            return false;
        }
        return true;
    }

    private boolean createExpandoFields(ExpandoBridge userExpandoBridge) {
        //columns name
        String[] expandoColumn = new String[]{"placeOfStudy",
                "faculty",
                "group",
                "vkUid",
                "faceBook",
                "about",
                "rules"};
        //columns type
        int type = ExpandoColumnConstants.STRING;
        //for not empty bridge
        if (userExpandoBridge != null) {
            try {
                //adding new expando in database
                for (int i = 0; i < expandoColumn.length - 1; i++) {
                    if (!userExpandoBridge.hasAttribute(expandoColumn[i])) {
                        userExpandoBridge.addAttribute(expandoColumn[i], type);
                    }
                }
                //add rules column
                type = ExpandoColumnConstants.BOOLEAN;
                if (!userExpandoBridge.hasAttribute(expandoColumn[RULESACEPT])) {
                    userExpandoBridge.addAttribute(expandoColumn[RULESACEPT], type);
                }
            } catch (PortalException ex) {
                LOG.log(Level.SEVERE, EXEPTION, ex);
                return false;
            }
        }
        return true;
    }

    private void setExpandoFields(ExpandoBridge userExpandoBridge, Map mapOfExpFields) {
        //columns name
        String[] expandoColumn = new String[]{"placeOfStudy",
                "faculty",
                "group",
                "vkUid",
                "faceBook",
                "about",
                "rules"};
        //column of rules
        boolean rules;
        //for not empty bridge
        if (userExpandoBridge != null) {
            //adding expando information in database
            for (int i = 0; i < expandoColumn.length - 1; i++) {
                //for not empty columns
                if (!mapOfExpFields.get(expandoColumn[i]).equals("")) {
                    //set expando column
                    userExpandoBridge.setAttribute(expandoColumn[i],
                            (Serializable) mapOfExpFields.get(expandoColumn[i]));
                }
            }
            //get rules column
            rules = (mapOfExpFields.get(expandoColumn[RULESACEPT]).equals("true")) ? true : false;
            //set rules column
            userExpandoBridge.setAttribute(expandoColumn[RULESACEPT], rules);
        }
    }

    private static PermissionChecker getAdministratorPermissionChecker(long companyId) throws PortalException, SystemException {
        PermissionChecker administratorPermissionChecker = null;
        Role administratorRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
        List<User> administratorUsers = UserLocalServiceUtil.getRoleUsers(administratorRole.getRoleId());

        if ((administratorUsers != null) && (administratorUsers.size() > 0)) {

            User administratorUser = administratorUsers.get(0);

            try {
                administratorPermissionChecker = PermissionCheckerFactoryUtil.getPermissionCheckerFactory().create(administratorUser, true);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, EXEPTION, ex);
                throw new SystemException(ex.getMessage(), ex);
            }
        } else {
            throw new SystemException("Unable to find a user with the Administrator role! Impossible!");
        }

        return administratorPermissionChecker;
    }
}
