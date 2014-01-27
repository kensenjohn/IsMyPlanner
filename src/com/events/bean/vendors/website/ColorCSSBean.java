package com.events.bean.vendors.website;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/25/14
 * Time: 7:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class ColorCSSBean {

    private String background = Constants.EMPTY;
    private String highlightedTextOrLink = Constants.EMPTY;
    private String text = Constants.EMPTY;
    private String navBreadTabBackground = Constants.EMPTY;
    private String border = Constants.EMPTY;
    private String filledButton = Constants.EMPTY;
    private String filledButtonText = Constants.EMPTY;

    private String plainButton = Constants.EMPTY;
    private String plainButtonText = Constants.EMPTY;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getHighlightedTextOrLink() {
        return highlightedTextOrLink;
    }

    public void setHighlightedTextOrLink(String highlightedTextOrLink) {
        this.highlightedTextOrLink = highlightedTextOrLink;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNavBreadTabBackground() {
        return navBreadTabBackground;
    }

    public void setNavBreadTabBackground(String navBreadTabBackground) {
        this.navBreadTabBackground = navBreadTabBackground;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getFilledButton() {
        return filledButton;
    }

    public void setFilledButton(String filledButton) {
        this.filledButton = filledButton;
    }

    public String getFilledButtonText() {
        return filledButtonText;
    }

    public void setFilledButtonText(String filledButtonText) {
        this.filledButtonText = filledButtonText;
    }

    public String getPlainButton() {
        return plainButton;
    }

    public void setPlainButton(String plainButton) {
        this.plainButton = plainButton;
    }

    public String getPlainButtonText() {
        return plainButtonText;
    }

    public void setPlainButtonText(String plainButtonText) {
        this.plainButtonText = plainButtonText;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ColorCSSBean{");
        sb.append("background='").append(background).append('\'');
        sb.append(", highlightedTextOrLink='").append(highlightedTextOrLink).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", navBreadTabBackground='").append(navBreadTabBackground).append('\'');
        sb.append(", border='").append(border).append('\'');
        sb.append(", filledButton='").append(filledButton).append('\'');
        sb.append(", filledButtonText='").append(filledButtonText).append('\'');
        sb.append(", plainButton='").append(plainButton).append('\'');
        sb.append(", plainButtonText='").append(plainButtonText).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
