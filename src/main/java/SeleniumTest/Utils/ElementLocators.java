package SeleniumTest.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ElementLocators {
    GOOGLE_ACCEPT_COOKIES_MODAL_WINDOW("//*[@data-type = 'cookie-notification']//button", "google"),
    GOOGLE_CHAT_WITH_MANAGET_MODAL("//div[contains(@class, 'message-container')]//span[@class='close']", "google"),
    POLICY_POPUP_AGREE_BUTTON_XPATH("//*[text()='AGREE']/parent::button", "pastebin"),
    HIDE_SLIDE_BANNER_BUTTON_XPATH("//*[contains(@id,'hideSlideBanner')]", "pastebin");
    private final String path;
    private final String domen;
}
