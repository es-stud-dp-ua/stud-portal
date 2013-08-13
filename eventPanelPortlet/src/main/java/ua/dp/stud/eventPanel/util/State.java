package ua.dp.stud.eventPanel.util;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.springframework.web.portlet.ModelAndView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Pikus Vladislav
 */
public abstract class State {
    private static final Logger LOG = Logger.getLogger(State.class.getName());
    public static final String FIRST_STATE = "first";
    public static final String NEXT_STATE = "next";
    public static final String PREV_STATE = "prev";
    protected static final String PAGE_COUNT = "pageCount";
    protected static final String CURRENT_PAGE = "currentPage";
    protected static final String TYPE = "type";
    protected static final int PER_PAGE = 4;
    protected EventPanelHelper helper;
    protected String cntDesc;
    protected String portletName;

    public String getCntDesc() {
        return cntDesc;
    }

    public State(EventPanelHelper helper, String cntDesc, String portletName) {
        this.helper = helper;
        this.cntDesc = cntDesc;
        this.portletName = portletName;
    }

    /**
     * Set current page
     *
     * @param pageCount page count
     * @return current page
     */
    protected int setPage(Integer pageCount) {
        Integer newCurrentPage = helper.getCurrentPage();
        String direction = helper.getDirection();
        if (direction.equals(NEXT_STATE)) {
            newCurrentPage++;
        } else {
            if (direction.equals(PREV_STATE)) {
                newCurrentPage--;
            } else {
                if (direction.equals(FIRST_STATE)) {
                    newCurrentPage = 1;
                }
            }
        }
        if (pageCount < newCurrentPage) {
            newCurrentPage = 1;
        } else {
            if (newCurrentPage < 1) {
                newCurrentPage = pageCount;
            }
        }
        return newCurrentPage;
    }

    protected void setPlid() {
        ThemeDisplay themeDisplay = (ThemeDisplay) helper.getRequest().getAttribute(WebKeys.THEME_DISPLAY);
        Long groupId = themeDisplay.getScopeGroupId();
        Long plid = 0l;
        try {
            plid = LayoutLocalServiceUtil.getDefaultPlid(groupId, false, portletName);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception: ", ex);
        }
        helper.getModel().addObject("plid", plid);
        helper.getModel().addObject("portlet_name", portletName);
    }

    public abstract Integer getPagesCount();

    public abstract ModelAndView getObjectByPage();

    public void approve() {
    }
}
