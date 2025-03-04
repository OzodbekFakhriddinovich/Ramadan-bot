package org.example;

public class ProfileData {
    private String chatId;
    private String userRegion;
    private Integer selectedDateMessageId;

    public ProfileData(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public Integer getSelectedDateMessageId() {
        return selectedDateMessageId;
    }

    public void setSelectedDateMessageId(Integer selectedDateMessageId) {
        this.selectedDateMessageId = selectedDateMessageId;
    }
}
