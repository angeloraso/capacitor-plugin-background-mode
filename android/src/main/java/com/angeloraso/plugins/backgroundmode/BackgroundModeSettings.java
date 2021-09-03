package com.angeloraso.plugins.backgroundmode;

public class BackgroundModeSettings {
    private String title = "App is on background mode";
    private String text = "App will start faster";
    private String subText = "";
    private Boolean bigText = false;
    private Boolean resume = true;
    private Boolean silent = false;
    private Boolean hidden = true;
    private String color = "55335A";
    private String icon = "icon";
    private String channelName = "capacitor-plugin-background-mode";
    private String channelDescription = "capacitor plugin background mode notification";
    private Boolean allowClose = false;
    private String closeIcon = "close-icon";
    private String closeTitle = "Close";
    private Boolean showWhen = true;
    private Visibility visibility = Visibility.PUBLIC;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public Boolean getBigText() {
        return bigText;
    }

    public void setBigText(Boolean bigText) {
        this.bigText = bigText;
    }

    public Boolean getResume() {
        return resume;
    }

    public void setResume(Boolean resume) {
        this.resume = resume;
    }

    public Boolean getSilent() {
        return silent;
    }

    public void setSilent(Boolean silent) {
        this.silent = silent;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public Boolean getAllowClose() {
        return allowClose;
    }

    public void setAllowClose(Boolean allowClose) {
        this.allowClose = allowClose;
    }

    public String getCloseIcon() {
        return closeIcon;
    }

    public void setCloseIcon(String closeIcon) {
        this.closeIcon = closeIcon;
    }

    public String getCloseTitle() {
        return closeTitle;
    }

    public void setCloseTitle(String closeTitle) {
        this.closeTitle = closeTitle;
    }

    public Boolean getShowWhen() {
        return showWhen;
    }

    public void setShowWhen(Boolean showWhen) {
        this.showWhen = showWhen;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        if (visibility != null) {
            this.visibility = Visibility.valueOf(visibility);
        }
    }
}
